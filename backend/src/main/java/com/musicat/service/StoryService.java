package com.musicat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.dto.YoutubeSearchResultDto;
import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.Story;
import com.musicat.data.repository.StoryRepository;
import com.musicat.util.ConstantUtil;
import com.musicat.util.StoryBuilderUtil;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// Todo : 사연 Valid 검사 false일 경우 readed 처리 해줘야 한다. -> 이미 신청한 사연이 있는지 검증하기 위해서
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    // logger 정의
    private static final Logger logger = LoggerFactory.getLogger(StoryService.class);

    // Utility 정의
    private final StoryBuilderUtil storyBuilderUtil;
    private final ConstantUtil constantUtil;

    // Service 정의
    private final SpotifyApiService spotifyApiService;
    private final YoutubeApiService youtubeApiService;
    private final KafkaProducerService kafkaProducerService;

    // Repository 정의
    private final StoryRepository storyRepository;


    /**
     * 사연 신청
     *
     * @param storyRequestDto
     */
    @Transactional
    public void insertStory(StoryRequestDto storyRequestDto) {

        Story story = null;

        // 1. Spotify, Youtube API를 사용해서 DB에 반영하기
        String spotifyQuery =
                storyRequestDto.getStoryMusicTitle() + " " + storyRequestDto.getStoryMusicArtist();
        try {
            List<SpotifySearchResultDto> spotifySearchResultDtos = spotifyApiService.searchSpotifyMusicList(
                    spotifyQuery);
            // Todo : spotify 검색 결과가 유효한지 체크하는 로직 재정의 필요
            logger.debug("검색 결과 사이즈 : {}", spotifySearchResultDtos.size());
            if (spotifySearchResultDtos.size() > 0) {
                // a. 스포티파이 검색 결과 -> 커버 이미지 -> DB
                for (SpotifySearchResultDto data : spotifySearchResultDtos) {
                    logger.debug("스포티파이 검색 결과 : {}, {}", data.getMusicTitle(),
                            data.getMusicImage());
                }

                SpotifySearchResultDto spotifyResult = spotifySearchResultDtos.get(0);

                // b. 유튜브 검색 -> 동영상 ID -> DB
                YoutubeSearchResultDto youtubeSearchResultDto = youtubeApiService.findVideo(
                        storyRequestDto.getStoryMusicTitle(),
                        storyRequestDto.getStoryMusicArtist());
                logger.debug("유튜브 검색 결과 : {}", youtubeSearchResultDto);

                if (youtubeSearchResultDto == null) {
                    logger.debug("유튜브 검색 결과가 없습니다.");
                    throw new RuntimeException("유튜브 검색 결과가 없습니다.");
                }

                // c. 스포티파이 + 유튜브 검색 성공시 -> DB
                story = storyRepository.save(
                        storyBuilderUtil.buildStoryEntity(storyRequestDto));
                story.setStoryMusicCover(spotifyResult.getMusicImage());
                story.setStoryMusicYoutubeId(youtubeSearchResultDto.getVideoId());
                story.setStoryMusicLength(youtubeSearchResultDto.getMusicLength());

            } else {
                logger.debug("스포티파이 검색 결과가 없습니다.");
                throw new RuntimeException("스포티파이 검색 결과가 없습니다.");
            }
        } catch (Exception e) {
            logger.debug("서버 예외 발생");
            throw new RuntimeException("서버 예외 발생");
        }



        // 2. 사연 데이터, 신청곡 를 카프카로 전송 -> 파이썬 서버에서 valid 체크 후 DB 반영, 인트로 음성 파일 생성, Reaction 음성 파일 생성, Outro 음성 파일 생성
        try {
            // Todo : Topic과 보낼 데이터 재정의 필요
            // storySeq, storyContent
            // storyMusicTitle, storyMusicContent
            kafkaProducerService.send("verifyStory", story.getStoryContent());
            kafkaProducerService.send("musicRequest", story.getStoryMusicTitle());
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("카프카 에러");
        }

    }

//    /**
//     * 사연 1개 조회 (읽어야 하는 사연)
//     */
//    public Object getTopStoryInfo() throws Exception {
//
//        Optional<Story> optionalStory = storyRepository.findTop1ByStoryReadedFalseAndStoryValidTrueOrderByStoryCreatedAt();
//
//        if (optionalStory.isPresent()) { // 사연이 존재함
//            Story story = optionalStory.get();
//
//            return storyBuilderUtil.buildStoryInfoDto(story);
//        } else { // 사연이 존재하지 않음
//            return null;
//        }
//    }

    /**
     * 사연 중복 검사
     *
     * @param userSeq
     * @return
     */
    public void isUniqueStory(long userSeq) {
        Optional<Story> optionalStory = storyRepository.findByUserSeqAndStoryReadedFalseOrStoryReadedNull(
                userSeq);

        if (optionalStory.isPresent()) throw new EntityExistsException("중복 사연이 존재합니다.");

    }


    /**
     * 사연 상세 조회
     *
     * @param storySeq
     * @return
     */
    public StoryInfoDto getStory(long storySeq) {
        Story story = storyRepository.findById(storySeq)
                .orElseThrow(EntityNotFoundException::new);

        return storyBuilderUtil.buildStoryInfoDto(story);
    }

    /**
     * 사연 삭제
     * @param storySeq
     */
    @Transactional
    public void deleteStory(long storySeq) {

        Story story = storyRepository.findById(storySeq)
                .orElseThrow(() -> new EntityNotFoundException("사연이 존재하지 않습니다.")); // 사연 조회

        storyRepository.delete(story); // 사연 삭제
    }


}

package com.musicat.data.repository;

import com.musicat.data.entity.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 작성 기준으로 최신순 3개를 가져오는 메소드
    Optional<List<Notice>> findTop3ByOrderByNoticeCreatedAtDesc();

    Page<Notice> findAll(Pageable pageable);

    Page<Notice> findAllByNoticeTitleContainingOrNoticeContentContaining(Pageable pageable, String noticeTitle, String noticeContent);
}
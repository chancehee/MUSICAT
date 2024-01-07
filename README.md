![프로젝트 로고](./image/Logo.png/)

Musicat은 인공지능 DJ가 진행하는 고품격 라디오 서비스입니다. DJ 및 청취자와 채팅으로 소통할 수 있고, 사연과 음악 신청도 가능합니다. 

<!-- 프로젝트 UCC : [Musicat - UCC](https://ramen-buang.notion.site/Musicat-UCC-c561acbeb18743fb94ad90b9eeb6be23)

프로젝트 기간: 2023.02.27 ~ 2023.04.07 -->

<!-- ## 프로젝트 소개 (배경, 개요)
 많은 기업에서 ChatGPT를 활용한 다양한 서비스를 출시하고 있습니다. 이런 흐름에 발맞춰 인공지능 음성도메인과 결합하여 인공지능 DJ 라디오 방송인 뮤직캣을 기획하게 되었습니다.

 지치지 않는 AI DJ 뮤직캣은 청취자에게 연중 무휴 24시간 중단 없는 서비스를 경험을 제공합니다. 또한 라디오 진행에 필요한 수많은 자원을 대신하여 프로그램 관리만을 통해 방송컨텐츠를 제공할 수 있습니다. -->

<!-- ## 프로젝트 주요 기능
- 채팅 : 다른 청취자들과 소통이 가능하고, 라디오 DJ가 댓글을 읽고 반응하여 청취자와 신선하고 재미있는 상호작용을 합니다.
- 사연 : 하나의 목소리가 아닌, 다양한 목소리를 통해 사연을 읽어줘서 더욱 다채로운 사연 낭독을 합니다. 
- 신청곡 : 원하는 노래를 신청하고, 스트리밍하여 다른 청취자들에게 자신의 최애 노래를 뽑낼 수 있습니다. 
- 마이페이지 : 사이트 내에서 사용되는 포인트의 거래내역, 알림, 공지사항이 확인 가능하고 뱃지, 배경, 테마를 커스터마이징하여 자신만의 라디오를 만들 수 있습니다.   

## 프로젝트 주요 기술  
1. 인증/인가 
    - 카카오 소셜 로그인 : OAuth 2.0 사용
    - JWT를 사용한 인가 기능  
    - Spring Security를 활용한 사용자/관리자 권한에 따른 인증/인가 
2. 음악 스트리밍 (YouTube API)
    - 재생 중인 노래 제목 하이라이팅 
    - 신청곡 재생 목록 상위 10개 표시
    - 신청곡이 없을 경우 : 서버 DB에 저장된 노래 재생
    - 신청곡이 있을 경우 : 신청곡 목록 DB에 저장된 노래 재생
3. 신청곡 (Spotify API, YouTube API)
    - 검색창에서 KeyUp Event 시에 Spotify 검색 결과 상의 20개 리턴
    - 검색 결과 클릭시 Spotify 음악 상세정보(노래제목, 아티스트, 앨범커버이미지 등) 조회, YouTube 음악 검색 결과 리턴
    - 검색 결과를 Spring Server 에 전송 (노래 신청) -> DB 반영 
3. 사연 (Chat GPT API, Naver Clova API, TTS)
    - (화자, 내용) 형식의 key-value 형태로 사연 데이터 작성 
    - 사연 데이터가 Spring Server로 넘어온 후 적절한 사연인지 검증 로직 수행 (아파치 카프카, 파이썬 서버, Chat GPT API 사용)
    - 적절한 사연일 경우 TTS 작업 (Naver Clova API, Google TTS 사용)  -> MP3 파일 생성 
4. 채팅창 상호 작용 (Chat GPT API, STOMP, Perspective API, TTS)
    - 채팅 공격성 검사 (Bad Word API 사용) -> 공격적인 채팅일 경우: 채팅 필터링("클린 채팅을 사용해 주세요"), 회원에게 경고 메시지 전송 (누적 3회시 Ban)
    - 청취자들끼리 채팅 상호작용 
    - AI Radio DJ와 상호작용 : 적절한 채팅일 경우 TTS 작업 (아파치 카프카, Chat GPT API, Naver Clova API 사용) -> MP3 파일 생성
5. 마이페이지
    - 포인트를 사용하여 배경 테마, 채팅시 사용하는 뱃지 구매 -> 사용자 기호에 맞는 커스텀 테마 가능
    - 다크모드/라이트모드 지원
    - 닉네임 중복검사 및 수정 기능 제공  

## 사용 기술
* 이슈 관리 : Jira
* 형상 관리 : Git, Gitlab
* 의사소통, 협업: Notion, Mattermost, Discord
* 개발환경
    * OS : Window10
    * IDE : Intellij, VSCode
    * EC2 : Ubuntu 20.04 LTS (GNU/Linux 5.4.0-1018-aws x86_64)
    * Database : Mariadb 10.6
    * SSH : Windows Terminal, MobaXterm
    * CI/CD : Jenkins
    * Reverse Proxy : Nginx
    * SSL : CertBot, Let's Encrypt
* 프론트엔드 (React)
    * Typescript
    * React
    * Recoil
    * React-Query
    * Vite
    * sockjs-Client
    * stompjs
    * threejs
* 백엔드 (SpringBoot)
    * Springboot Starter Data JPA
    * Springboot Starter Websocket
    * Springboot Starter Security
    * JWT
    * Apache Kafka
    * google api services youtube v3 (Youtube Data API V3)
    * google http client gson
    * Spotify API (음원 검색)
    * perspective API (비속어 필터링)
    * jsoup
    * lombok
    * spring boot devtools
    * mariadb java client
* 백엔드 (RadioServer)
    * FastAPI
    * asyncio
    * pydub
    * MariaDB
    * Apache Kafka
    * gunicorn
    * uvicorn
    * Naver Clova API
    * Chat GPT API
-->

## 🛠️ 기술 스택


<div align=center>
<img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=NGINX&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>

<br>

<img src="https://img.shields.io/badge/Apache Kafka-231F20?style=for-the-badge&logo=Apache Kafka&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>

<br>

<img src="https://img.shields.io/badge/Java-FF7800?style=for-the-badge&logo=Java&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/FastAPI-009688?style=for-the-badge&logo=FastAPI&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>

<br>

<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=TypeScript&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>

<br>

<img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=Jira&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/GitLab-FCA121?style=for-the-badge&logo=GitLab&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> 
<img src="https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=Mattermost&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>

</div>


## 📋 상세 페이지

### 메인페이지
![메인](https://github.com/chancehee/MUSICAT/assets/59447235/3100403c-db4c-414f-96db-2b6165f36318)  
<!-- ![메인페이지](./image/메인페이지.gif) -->  

### 신청곡
<img width="1415" alt="신청곡1" src="https://github.com/chancehee/MUSICAT/assets/59447235/19d1e785-fe25-4060-9ea6-68040495215e"> 
<br>
<img width="1413" alt="신청곡2" src="https://github.com/chancehee/MUSICAT/assets/59447235/2385b436-6a29-4db1-83ee-97b98d0d4fd4">
<br>
원하는 노래를 신청하고 라디오 DJ의 리액션을 들을 수 있습니다.

### 사연신청
![사연신청](./image/사연신청.gif)  
다양한 목소리의 화자를 선택할 수 있습니다. 

<!-- ![노래신청](./image/노래신청.gif)   -->

### 채팅
![채팅](./image/채팅.gif)  
라디오 DJ 및 청취자와 소통할 수 있습니다.

<!--### 마이페이지
![마이페이지](./image/마이페이지.gif)  
내 전체적인 정보를 알 수 있는 페이지. 다크모드, 닉네임 변경, 나의 츄르 변동내역을 확인 가능하다. -->
<!-- ### 알림 / 공지사항
![공지사항](./image/공지사항.gif)  
공지사항과 개인 알림을 받을 수 있는 페이지 -->
### 마이페이지
![인벤토리](./image/인벤토리.gif)  
배지, 배경, CD타입을 커스터마이징 할 수 있습니다. 
<!-- ### 관리자페이지
![관리자페이지](./image/관리자페이지.gif)  
다른 사용자들의 상태를 관리하는 페이지. 관리자만 들어갈 수 있다. 사용자들의 채팅, 정지 여부 상태를 변경 가능하다. -->


### 📆 프로젝트 기간 (23.02.27 ~ 23.04.07)

- 기획 및 설계 : 23.02.27 ~ 23.03.05
- 프로젝트 구현 : 23.03.06 ~ 23.04.02
- 버그 수정 및 산출물 정리 : 23.04.03 ~ 23.04.07



<!-- ### 기능 기획서
  ![기능 기획서](./image/work1.png)

  --- -->

### 시스템 아키택쳐
  ![아키텍쳐](./image/system.png)

  ---

<!-- ### API 명세서
  ![API 명세서](./image/api1.png)
  
  --- -->

### ERD 다이어그램
  ![ERD](./image/ERD.png)

  ---


## 👪 개발 멤버 소개

<table>
    <tr>
        <td height="140px" align="center"> 
            <img src="./image/human5.png" width="140px" /> <br><br> 😶 최다은 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center"> 
            <img src="./image/human3.png" width="140px" /> <br><br> 🙂 박동환 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center">
            <img src="./image/human6.png" width="140px" /> <br><br> 😆 이연학 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center">
            <img src="./image/human1.png" width="140px" /> <br><br> 👑 김동언 <br>(Back-End) </a> <br></td>
        <td height="140px" align="center"> 
            <img src="./image/human2.png" width="140px" /> <br><br> 😁 이찬희 <br>(Back-End) </a> <br></td>
		<td height="140px" align="center"> 
            <img src="./image/human4.png" width="140px" /> <br><br> 😶 최웅렬 <br>(Back-End) </a> <br></td>
    </tr>
</table>


## 프로젝트 참고 링크

- [프로젝트 Notion](https://ramen-buang.notion.site/SSAFY-2-MusiCat-6ce1496529df4689bdae266db3d50466) Musicat




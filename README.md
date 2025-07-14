# 👚 WEARUP

지속 가능한 패션 소비를 위한 **의류 구독 기반 렌탈 서비스**  
패스트패션으로 인한 환경오염과 자원 낭비 문제를 해결하고자  
브랜드의 의류 등록/재고 관리와 사용자의 구독 기반 대여·반납 기능을 제공합니다.

> **📅 프로젝트 기간**: 2025.05.15 ~ 2025.06.05  
> **👥 팀명**: NCT200  

<br>

## 📌 프로젝트 개요

**WEARUP**은 옷장을 공유하는 의류 렌탈 서비스로, 다음과 같은 문제의식에서 출발했습니다:

- ✔️ **문제 인식**: 패스트패션이 유발하는 환경오염, 자원 낭비
- 💡 **해결 방안**: 브랜드와 소비자를 연결하는 지속 가능한 의류 순환 시스템
- 🧾 **주요 기능**:
  - 브랜드의 상품 등록 및 재고 관리
  - 사용자의 구독 서비스 기반 렌탈/반납
  - 관리자 매출 관리, 사용자 포인트 환급, 결제 조회 등 

<br>

## 🛠 사용 기술 스택

| 분야 | 기술 |
|------|------|
| **Frontend** | Thymeleaf, Bootstrap, JQuery, AJAX |
| **Backend** | Java 17, Spring Boot 3, Spring Security, MyBatis, RestTemplate |
| **Database** | MySQL, RDS (AWS) |
| **CI/CD & Infra** | EC2, 자동화 스크립트, GitHub |
| **기타** | OAuth2, org.json, Scheduler, JSoup, Validator, Lombok | 

<br>

## 🧩 주요 기능 요약

- 🔐 **OAuth2 기반 로그인/회원가입**
- 📦 **브랜드 상품 등록 및 수정**
- 🔁 **구독 기반 의류 대여/반납 시스템**
- 💳 **포인트 환급 및 결제 내역 조회**
- 📝 **피드(게시글/댓글/첨부파일) 기능**
- 📊 **관리자용 매출 집계 및 회원 관리**
- 🏠 **주소 검색 (다음 API 활용)**
- 🖥 **반응형 UI 전반 스타일링**
- 🛠 **EC2 서버 배포 및 RDS 연동** 

<br>

## 👨‍👩‍👧‍👦 팀원 및 담당 업무

| 이름 | 역할 | 담당 업무 요약 |
|------|------|----------------|
| **안주경 (팀장)**  | OAuth2 로그인/회원가입, 마이페이지(내정보/구독/대여/게시물), 결제 조회, 포인트 환급 로직, 전체 CSS 및 반응형 UI |
| **이현정** | 피드(CRUD), 관리자 기능, 권한 기반 페이지 제어, 다음 주소 API, EC2+RDS 배포, GitHub 관리 |
| **최해훈** |  브랜드 회원가입/상품 CRUD, 대여 서비스, 상태 변경/배송 관리, Scheduler 동기화 로직 |

<br>

## 📝 실행 방법

### 0. 사전 준비

- Java 17 이상 설치
- MySQL 8 이상 설치 및 DB 생성
- AWS S3, RDS, OAuth2 연동 설정 필요


### 1. 레포지토리 클론

```bash
git clone https://github.com/hyoju0104/WEARUP.git
cd WEARUP
```

### 2. application.yml 작성
src/main/resources/application.yml 파일을 생성하고 아래 내용을 참고하여 환경을 설정해 주세요:

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/wearup?serverTimezone=Asia/Seoul
    username: root
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: none

oauth:
  kakao:
    client-id: your-client-id
    redirect-uri: http://localhost:8080/login/oauth2/code/kakao
```

### 3. 프로젝트 실행
```bash
# Gradle 빌드
./gradlew build

# Spring Boot 실행
./gradlew bootRun
```

### 4. 웹 접속
```http://localhost:8080```

# 게시판 과제

## 들어가기에 앞서
 주어진 2주라는 시간이 정말 짧게 느껴졌고 굉장히 도전적인 과제였습니다.
 이력서를 통해서 이미 알고 계시다시피, 거의 대부분의 업무를 Ruby, Ruby on rails로 진행했었고
 자바와 스프링부트가 아주 생소한 상태에서 시작한 과제였습니다.
 
  자바/스프링과 친해지면서 과제를 진행하기 위해서 선택적으로 포기한 부분들도 있습니다.
 우선 UI/UX 부분은 본 과제에서 거의 고려하지 않았습니다. 저도 이 부분에 대해서는 아쉬움이 많이 남지만,
 기본 기능에 집중하기 위해 부득이하게 선택한 전략이라는 점 고려해주시면 감사하겠습니다.
 
  코드 곳곳에서 자바/스프링에 익숙하지 않은 모습들이 보일 수도 있습니다.
 저도 결과물의 퀄리티에 대해서는 아쉬움이 남지만, 과정은 아쉬움없이 알차게 보낸것 같습니다.
 합/불의 여부와 관계없이 이번 과제를 통해서 단기간에 정말 많은 공부를 할 수 있어서 즐거웠습니다.
 간만에 느껴보는 "어려움을 헤쳐나가는 즐거움"이 넘치는 과정이었습니다.

## 개발환경
- Java 11.0.8
- Gradle 6.6.1
- Spring boot 2.3.4
- MySQL 5.7
- 프로젝트는 다중 모듈 구조로 개발되었습니다.
```
root: 루트 프로젝트
└─ core: Entity, Repository 및 Utility 등
└─ board-batch: 배치(batch)
└─ web-app: 게시판 애플리케이션
└─ admin-app: 관리자 애플리케이션
```


## 사전 준비사항
### MySQL
- 테스트의 용이함을 위해서 Docker로 세팅을 추천합니다.
- 로컬에 Docker를 설치해주세요. https://www.docker.com/products/docker-desktop
- 프로젝트 루트 디렉토리에서 `docker-compose up`을 통해 MySQL 5.7을 실행합니다.
  - 포트는 3307로 노출시켜두었으며, 프로젝트에서도 3307로 설정되어있습니다.
- `docker ps` 따위의 명령어로 해당 CONTAINER ID를 획득합니다.
- `docker exec -it <CONTAINER ID> /bin/bash`를 통해 컨테이너 내부로 진입.
- `mysql --user root`로 MySQL로 접속.
- `CREATE DATABASE multi_board;` 실행으로 DB 생성

### DB schema migration
- 해당 프로젝트는 Flyway를 사용하여 migration 버젼 관리를 합니다.
- 프로젝트 루트 디렉토리에서 아래 명령어를 통해 DB 마이그레이션을 진행합니다.
  - Windows: `gradlew.bat :core:flywayMigrate`
  - LINUX/Mac: `./gradlew :core:flywayMigrate`
  
### 메일 서버
- 메일 서버 역시 테스트의 용이함을 위해 Gmail로 세팅되어있습니다.
- `./web-app/src/main/resources/mail-setting-credentials.properties` 파일을 생성해주세요.
- 아래 내용을 추가해주세요.
```
spring.mail.username=foo@gmail.com // 실제 본인 이메일주소
spring.mail.password=barbaz // 메일 계정 비밀번호
```
- https://myaccount.google.com/lesssecureapps?pli=1 설정으로 들어가서 `보안 수준이 낮은 앱 허용: 사용`으로 설정되어있는지 확인

## 애플리케이션 구동
- 프로젝트는 다중 모듈 구조로 개발되었습니다.
```
root: 루트 프로젝트
└─ core: Entity, Repository 및 Utility 등
└─ board-batch: 배치(batch)
└─ web-app: 게시판 애플리케이션
└─ admin-app: 관리자 애플리케이션
```
- 각각의 앱은 아래의 포트로 구동되도록 설정되어 있습니다. 구동 전에 확인 부탁드립니다.
  - web-app: 8080
  - admin-app: 8081
  - board-batch: 8082 (Spring Batch도 HTTP 요청을 받을 수 있도록 설계)

- 아래의 2개의 계정이 사전에 생성되어있습니다.
  - test1@test.com / test   (어드민/일반 권한)
  - test2@test.com / test   (일반 권한)
  
### 게시판 앱(web-app)
- 아래의 명령어를 통해서 Spring boot 구동.
  - Windows: `gradlew.bat :web-app:bootRun`
  - LINUX/Mac: `./gradlew :web-app:bootRun`
- 접속은 `localhost:8080`으로 진행.

### 관리자 앱(admin-app)
- 아래의 명령어를 통해서 Spring boot 구동.
  - Windows: `gradlew.bat :admin-app:bootRun`
  - LINUX/Mac: `./gradlew :admin-app:bootRun`
- 접속은 `localhost:8081`로 진행.
  
### 배치(board-batch)
- 아래의 명령어를 통해서 Spring boot 구동
  - Windows: `gradlew.bat :board-batch:bootRun`
  - LINUX/Mac: `./gradlew :board-batch:bootRun`
- 랭킹 계산 잡은 admin-app에 설정된 스케쥴러를 통해 매일 01:00에 board-batch 앱으로 HTTP 요청을 날려 트리거합니다.
- 테스트를 위해 아래의 url로 GET 리퀘스트를 보냄으로써 임의 실행 가능합니다.
  - `http://localhost:8082/calculate-ranking\?t\=awefawefawefawef` <-- 임의의 스트링으로 변경하면서 테스트!
- 내부망을 통해서 server to server만 허용할거라는 가정하에 인증은 생략하였습니다.
- 사실 HTTP 리퀘스트를 받지않고 자체적으로 스케쥴러를 가지는 구현도 했었으나, 그냥 웹서버 버젼으로 구현해보았습니다.

## 보완할 점, 아쉬운 점
- Access Control List(ACL)도 구현하고 싶었으나, 시간 관계상 생략.
- DB와 통신하는 부분(JPA, Hibernate 등)에서 좀 더 성능 개선을 할 여지가 남아있음.
- Soft delete를 구현하였으나, 삭제 레코드와의 Unique constraint 충돌 문제.
- 일부 기능에는 테스트 코드를 작성하였으나, 테스트의 러닝커브가 스프링부트 자체의 러닝커브보다 가파르게 느껴져 일부만 작성.
- UI가 엉망인 부분과 그나마 구현된 Template/script 등도 DRY 하지않은 점.
- 기타 무수히 많음...

# EveryAuction_RENEW
누구나 쉽게 경매할수 있는 중고 거래 플랫폼입니다.

판매할 물건을 업로드 하고 1대1 채팅으로 물건을 거래하세요!

http://everyauction.bu.to/

※본 프로젝트는 https://github.com/joung45387/everyAuction 의 개선버전이며, 처음부터 재설계한 프로젝트입니다.

# 사용기술
## 백엔드
* SpringBoot
* Spring Security
* Spring Data JPA
* WebSocket

## 프론트엔드
* Thymeleaf
* HTML/CSS/Javascript
* Bootstrap5

## 데이터베이스
* MySQL
* Redis

## 인프라
* AWS EC2
* AWS S3
* Azure VM
* Nginx
* Docker
* RabbitMQ
* GitHub Actions

# 시스템 아키텍처
<img src="https://user-images.githubusercontent.com/45916379/214310735-af91fc71-671a-403d-91c0-b260c639eea0.png">

## ERD
<img src="https://user-images.githubusercontent.com/45916379/214311717-eb381af7-cd75-42a4-82a5-833ceaef8558.png">

## 화면 구성


| ![111](https://user-images.githubusercontent.com/45916379/214575255-a6e6d5ce-85a3-427d-8491-a312447c7eef.png) | ![22](https://user-images.githubusercontent.com/45916379/214575360-c72ad374-c387-40f8-a80b-40bda9a58886.png) | ![333](https://user-images.githubusercontent.com/45916379/214575500-00a3c786-ad22-4179-8285-a840ac33fb2f.png) | 
|:-------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------:|
|                                                    메인 페이지                                                     |                                                     로그인                                                      |                                                     상품등록                                                      |

|    ![444](https://user-images.githubusercontent.com/45916379/214576780-83e89285-b802-42a6-908e-baba1d5b1300.png)    |  ![999](https://user-images.githubusercontent.com/45916379/214576895-e98e628a-68eb-4706-8647-68bd74065f73.png)   |   ![555](https://user-images.githubusercontent.com/45916379/214576979-1b041e4b-f727-4fff-b9b7-4d486b7bdcc4.png)    |
|:--------:|:-----:|:-------:|
|  상품 페이지  |  댓글   |  구매 목록  |

|   ![666](https://user-images.githubusercontent.com/45916379/214577191-6d5a188b-7e25-4376-8627-780f56a47f9e.png)    |   ![777](https://user-images.githubusercontent.com/45916379/214577264-4b85093d-f369-4fc6-aca4-bbb7f4694e07.png)    |  ![888](https://user-images.githubusercontent.com/45916379/214577329-92053424-d7b8-4d7f-b919-6ac2f096b608.png)   |
|:-------:|:-------:|:-----:|
|  판매 목록  |  경매 기록  |  채팅   |

## 핵심 기능
* Nginx를 이용한 리버스 프록시 서버<br>
  Nginx를 이용하여 리버스 프록시 서버를 구축하고, 로드밸런싱을 구축하였습니다. 이는 서버의 부하와 트래픽을 분산시키기 위해 Scale out 한것이며 가용할수 있는 서버의 부족으로 한 서버 내에서 2개의 스프링 서버를 가동시키고 있지만 추후 분산 시스템의 확장을 위해서 이런 방식을 사용했습니다.
* CI/CD 및 무중단 배포<br>
  GitHub Actions를 이용하여 지속적 통합과 배포 파이프라인을 구축하였으며 Rolling방식의 무중단 배포 전략을 사용했습니다. <br>
  과정은 다음과 같습니다.
1. master 브랜치로 push 되면 jar파일로 빌드 후 도커 이미지화 합니다. 
2. Docker Hub에 이미지를 업로드하고 ssh접속을 통해 EC2에 접속한 후, 기존 1번서버를 종료한 후 컨테이너에서 이미지를 실행합니다.
3. 2번서버를 위한 jar 파일을 빌드하고 도커 이미지화 합니다. 이를 Docker Hub에 이미지를 업로드합니다.
4. Spring actuator를 이용하여 1번 서버가 정상적으로 가동중인지 확인하고 아니라면 가동될때가지 대기합니다.
5. 1번서버가 가동중이라면 새로 올린 이미지를 ssh를 통해 기존 2번서버를 종료한 후 컨테이너에서 이미지를 실행합니다.
<br>
   
* 로그인<br>
  스프링 시큐리티를 이용하여 Form Login 방식을 이용하여 세션을 유지 시켰습니다. 또한 로드밸런싱된 다중 서버이기 때문에 Redis 서버에 세션을 저장하여 여러 서버에서 로그인을 유지 할수 있도록 하였습니다. 
  그리고 OAuth 소셜로그인을 구현하여 받은 토큰정보를 바탕으로 회원가입을 진행합니다. 그리고 AOP를 이용하여 가입된 OAuth 유저가 추가정보를 입력할수 있도록 유도하는것을 구현하였습니다.
* 이미지 저장<br>
  메인페이지에서 썸네일을 원본으로 불러온다면 많은 트래픽이 발생하며, 속도 저하 이슈가 있을것이라 판단하여 썸네일이 될 첫번째 이미지를  ImgScalr 라이브러리를 이용해여 Crop 및 리사이징 하여 용량을 줄인 다음 AWS S3에 저장하였습니다.
  그리고 상품 페이지에서의 이미지들은 많은 이미지를 불러오는것이 아니기 때문에 원본 이미지를 저장하였습니다.
* 채팅<br>
  웹소켓을 이용하여 채팅을 구현하였습니다. 서버가 분산되어 있기 때문에 RabbitMQ를 이용하여 서버간의 통신을 하여 서버가 달라도 채팅을 할수 있도록 구현하였습니다.
  또한 웹 지식을 가지고 있는 사용자가 악의적인 목적을 가지고 다른사람의 채팅을 볼 가능성이 있기 때문에 Handshaking 후 웹소켓에 구독할 때 ChannelInterceptor를 이용하여 권한을 가지고 있는 유저인지 체크 후 채팅을 할수 있도록 구현하였습니다.
  


# SemiProject
##  카페 홍보 및 원두 제품 판매 사이트

[7조 세미프로젝트 기획안 220616.pdf](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/623ff9d2-54e6-4b74-a9dd-e5b85aebcbb2/7조_세미프로젝트_기획안_220616.pdf)

--- 

### 개발 환경
- **백엔드** : 자바, Springboot
- **DB** : MySQL using Mybatis
- **UI/UX** : Javascript, jQuery, Bootstrap5, Html5, CSS 
- **구조설계**: erdCloud, - - Notion

- **수행방향**: 각자 맡은 부분 개발 후 서버 <-> 웹 연동 구축할때 회의

### 협업 tool
- notion 
- [GitHub - kimyunzae/SemiProject](https://github.com/kimyunzae/SemiProject.git)
---

### 프로젝트 추진 일정

- 6/14~ 6/16: 주제 선정 및 일정 수립
- 6/16~ 6/19 : UI/UX 설계 / 데이터베이스 설계(erd) 및 DDL, DML 생성
- 6/20: Spring & 데이터베이스 연동환경 구축 (VO, DAO, Controller, Mapper) 
- 6/21~6/24: 쇼핑몰 웹 서비스 / 관리자 제품관리 기능 구현

---
### Database 설계

- ERD
  
    ![erdCloud (2).png](/src/main/resources/static/assets/img/Screen%20Shot%202022-06-24%20at%2023.44.26.png)
    
- SQL (DDL)
  
    ```sql
    DROP SCHEMA IF EXISTS teacoffeedb;
    CREATE SCHEMA teacoffeedb;
    USE teacoffeedb;
    
    DROP TABLE IF EXISTS `cust`;
    
    CREATE TABLE `cust` (
    	`uid`	VARCHAR(30)	NOT NULL,
    	`name`	VARCHAR(30)	NOT NULL,
    	`addr`	VARCHAR(30)	NULL,
    	`pwd`	VARCHAR(30) NOT NULL,
    	`email`	VARCHAR(30)	NULL,
    	`phone`	VARCHAR(20)	NULL,
    	`def_ship_addr` INT NULL
    );
    
    DROP TABLE IF EXISTS `cart`;
    
    CREATE TABLE `cart` (
    	`crid`	INT	NOT NULL,
    	`uid`	VARCHAR(30)	NOT NULL,
    	`pid`	INT	NOT NULL,
    	`cnt`	INT NOT	NULL
    );
    
    DROP TABLE IF EXISTS `cate`;
    
    CREATE TABLE `cate` (
    	`cid`	INT	NOT NULL,
    	`cname`	VARCHAR(30)	NOT NULL
    );
    
    DROP TABLE IF EXISTS `orderd`;
    
    CREATE TABLE `orderd` (
    	`orid`	INT	NOT NULL,
    	`uid`	VARCHAR(30)	NOT NULL,
    	`ordate`	DATE NOT NULL,
    	`totalprice`	INT	NULL,
    	`addr`	VARCHAR(100)	NOT NULL,
    	`receivename`	VARCHAR(30)	NOT NULL,
    	`status`	VARCHAR(30)	NULL,
    	`transaction`	VARCHAR(30)	NOT NULL
    );
    
    DROP TABLE IF EXISTS `board`;
    
    CREATE TABLE `board` (
    	`bid`	INT	NOT NULL,
    	`pid`	INT	NULL,
    	`uid`	VARCHAR(30)	NOT NULL,
    	`title`	VARCHAR(100) NOT NULL,
    	`contents`	VARCHAR(1000)	NULL
    );
    
    DROP TABLE IF EXISTS `orderdetail`;
    
    CREATE TABLE `orderdetail` (
    	`odid`	INT	NOT NULL,
    	`orid`	INT	NOT NULL,
    	`pid`	INT	NOT NULL,
    	`price`	INT	NOT NULL,
    	`pname` VARCHAR(100) NOT NULL,
    	`cnt`	INT	NOT NULL
    );
    
    DROP TABLE IF EXISTS `product`;
    
    CREATE TABLE `product` (
    	`pid`	INT	NOT NULL,
    	`pname`	VARCHAR(100)	NOT NULL,
    	`pprice`	INT	NOT NULL,
    	`regdate`	DATE	NOT NULL,
    	`cid`	INT	NOT NULL,
    	`pimgname`	VARCHAR(260)	NULL,
    	`pcontents`	VARCHAR(1000)	NULL
    );
    
    DROP TABLE IF EXISTS `addrlist`;
    
    CREATE TABLE `addrlist` (
    	`aid`	INT	NOT NULL,
    	`uid`	VARCHAR(30)	NOT NULL,
    	`addr`	VARCHAR(100) NOT NULL,
    	`receivename`	varchar(30)	NULL
    );
    
    ALTER TABLE `cust` ADD CONSTRAINT `PK_CUST` PRIMARY KEY (
    	`uid`
    );
    
    ALTER TABLE `cart` ADD CONSTRAINT `PK_CART` PRIMARY KEY (
    	`crid`
    );
    
    ALTER TABLE `cate` ADD CONSTRAINT `PK_CATE` PRIMARY KEY (
    	`cid`
    );
    
    ALTER TABLE `orderd` ADD CONSTRAINT `PK_ORDERD` PRIMARY KEY (
    	`orid`
    );
    
    ALTER TABLE `board` ADD CONSTRAINT `PK_BOARD` PRIMARY KEY (
    	`bid`
    );
    
    ALTER TABLE `orderdetail` ADD CONSTRAINT `PK_ORDERDETAIL` PRIMARY KEY (
    	`odid`
    );
    
    ALTER TABLE `product` ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (
    	`pid`
    );
    
    ALTER TABLE `addrlist` ADD CONSTRAINT `PK_ADDRLIST` PRIMARY KEY (
    	`aid`
    );
    
    ALTER TABLE cart MODIFY crid INT AUTO_INCREMENT;
    ALTER TABLE orderd MODIFY orid INT AUTO_INCREMENT;
    ALTER TABLE board MODIFY bid INT AUTO_INCREMENT;
    ALTER TABLE orderdetail MODIFY odid INT AUTO_INCREMENT;
    ALTER TABLE product MODIFY pid INT AUTO_INCREMENT;
    ALTER TABLE product AUTO_INCREMENT = 1000;
    ALTER TABLE addrlist MODIFY aid INT AUTO_INCREMENT;
    ```
    
---
### 웹사이트 주요기능

User의 CRUD 기능 구현, Admin page를 통한 Product CRUD 기능 구현

- User의 CRUD

  - Register페이지를 통해 새로운 User Insert 

    - Signup Now 버튼을 통해 데이터에 값 전송
    - ID 입력칸에 Ajax를 이용하여 중복 아이디 체크 기능 구현
    - 정상적으로 user등록이 완료되면 바로 login이 된 메인화면 출력

  - Sign in 페이지를 통해 login 실행

    - 존재하지 않는 id값을 넣거나 cust data table의 user id와 pwd가 일치하지 않으면 Ajax를 이용하여 ''아이디와 비밀번호를 확인하여 주십시오'' 메세지를 출력

  - 로그인이 정상적으로 되면 Cart와 MyPage화면이 홈 화면에 추가.

    - Profile 화면에서 로그인 한 user데이터들을 thymeleaf를 통해 출력

    - jQuery를 이용하여 EditProfile 버튼을 눌러 user 정보를 수정하면 수정된 데이트를 전송, Save 버튼으로 변경된 데이터 저장

    - jQuery를 이용해 address 페이지에서도 저장된 배송 정보를 수정, 저장 가능하게 구현

    - orders페이지에서 thymeleaf를 이용해 로그인 한 user의 주문했던 내역 출력

    - activities페이지에서 thymeleaf를 이용해 user가 product별 남긴 후기 출력

      

- Product 구매 기능 (User)

  - 상품을 thymeleaf를 이용하여 카테고리 별로  product페이지에 출력
    - login 전, product페이지 각 상품에 마우스를 올리면 Add cart 버튼이 실행되지 않고 signin 만 실행되도록 구현
    - login 후, 각 상품에 마우스를 올리면 사용한 bootstrap 기능으로 상품이 확대되며 view detail 생성
    - view detail 버튼 클릭 시 제품의 이름, 상세 정보, 후기, 가격, 수량을 modal 창으로 구현, 수량은 bootstrap-select 버튼을 이용
    - modal창 하단에 but, cart, close 버튼을 생성, thymeleaf를 이용해 다른 페이지로 이동할 수 있도록 링크 연결
  - 상품을 cart에 넣으면 Session에 데이터 전송, cart 페이지에 출력
    - 각 상품들의 수량을  JavaScript를 이용해 +,- 버튼으로 수정할 수 있게 구현
    - remove 버튼을 누르면 thymeleaf를 이용해 해당 데이터 삭제, 삭제된 값 Session에 저장
    - cart list 하단에 JavaScript를 이용해 상품 totalprice와 수량 출력
    - 제품 수량이 1미만이면 jQuery를 통해 ''최소 구매 수량'' 확인창이 alert으로 뜸.
    - order버튼을 누르면 상품 주문 페이지로 이동
  - order 페이지에 구매한 상품과 주문한 user 정보 출력
    - back to cart 버튼을 누르면 cart 버튼으로 이동
    - transaction을 bootstrap-select 버튼으로 선택하게 함
    - buy 버튼을 누르면 구매자 정보, 아이디, totalprice, 주소 등 상세 정보 페이지로 이동

---

### 개인 분담 파트

- 김현재: 목표지정 및 역할분배, 쇼핑몰 디자인 및 구조설계, 상품관련 기능 개발
- 이석희: 회원관련 기능 개발, 리뷰, 상품상세, 장바구니, 주문결제 기능 개발
- 백상은: DB구축, 카페정보 페이지, 로그인/회원가입, 장바구니, 주문결제 기능 개발
- 이상찬: DB구축, 배송관련 기능 개발, 데이터 수집 및 주입, 팀원 개발 지원 및 오류 해결

---


### 참고 사이트

- 실제 커피 판매 사이트
  
    [https://www.tea-and-coffee.com/buy-coffee/type/espresso-coffee](https://www.tea-and-coffee.com/buy-coffee/type/espresso-coffee)
    
- DB 설계 관련
  
    [https://m.blog.naver.com/ndb796/220638179768](https://m.blog.naver.com/ndb796/220638179768)
    
    [https://jamesyleather.tistory.com/390](https://jamesyleather.tistory.com/390)
    
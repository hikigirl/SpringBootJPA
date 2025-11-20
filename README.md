# JPA, Java Persistence API
- 자바 진영 > ORM 기술 표준으로 사용되는 인터페이스
- ORM(Object-Relational Mapping)

### 프로젝트 생성
- Name: "jpa"
- Language: Java
- Type: Gradle - Groovy
- Group: "com.test"
- Artifact: "jpa"
- Package name: "com.test.jpa"
- Java: 17
- Packaging: jar
- Dependencies:
  - Spring Web
  - Lombok
  - Oracle Driver
  - Thymeleaf
  - Spring Boot DevTools
  - Spring Data JPA

### JDBC 기반 기술
1. MyBatis
  1. Mapping Framework
  2. Java <-> Oracle(관계형 DB)
  3. 클래스 객체 -> (추가) -> 오라클 레코드
  4. 오라클 레코드 -> (전달) -> 클래스 객체
2. JPA
  1. ORM
  2. Java(객체) <-> 오라클(엔티티)
  3. 모든 DB 데이터를 자바처럼 접근 + 조작
  4. 구현체 -> Hibernate

### JPA 세팅하기
1. build.gradle: 일단 보류
2. application.yaml
   1. JDBC 설정(DataSource)
   2. JPA 설정

### 파일 생성
#### 기본 키워드 예제
- `com.test.jpa.controller`
  - `TestController.java`
- `com.test.jpa.entity`
  - `Item.java`
- `com.test.jpa.model`
  - `ItemDTO.java`
- `com.test.jpa.repository`
  - `ItemRepository.java`(I)
- `templates`: `result.html`

#### 관계 차수가 다양한 경우의 예제
- `com.test.jpa.controller`
  - `TestController.java`
- `com.test.jpa.entity`
  - `User.java`
  - `UserInfo.java`
  - `Board.java`
  - `Comment.java`
  - `Tag.java`
  - `Tagging.java`
- `com.test.jpa.repository`(Interface)
  - `UserRepository.java`
  - `UserInfoRepository.java`
  - `BoardRepository.java`
  - `CommentRepository.java`
  - `TagRepository.java`
  - `TaggingRepository.java`
- `com.test.jpa.model`
  - `UserDTO.java`
  - `UserInfoDTO.java`
  - `BoardDTO.java`
  - `CommentDTO.java`
  - `TagDTO.java`
  - `TaggingDTO.java`
- `templates`: `result.html`

#### 엔티티 클래스
- 역할: tblItem 테이블을 자바와 중계해주는 역할
- Java에서 item 클래스를 조작 -> (개발자는 개입X) -> DB의 tblItem에 반영
- DTO 역할과 일부 유사
- DTO는 단순한 상자, Entity는 기능이 많은 상자 정도..

#### JPA 쿼리 실행 방식
1. Query Method: 3~40%, 단순한 업무
2. JPQL, Java Persistence Query Language: 1~20%, 1+3번으로 불가능한 경우 사용...
3. Query DSL: 3~40%, 복잡한 업무

### CRUD 작업
1. INSERT
2. SELECT
3. UPDATE
4. DELETE

### Query Method
- 정해진 키워드 사용 > 메서드명 생성 > 메서드 호출 > 메서드명에 따라 정해진 SQL 생성
- 정해진 행동 키워드 + 컬럼명

### JPA에서 테이블 간의 관계를 표현하기
- 엔티티 - 엔티티간에 인식시키는 법 
- 부모 객체or자식 객체를 멤버변수로 둔다
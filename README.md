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
- `com.test.jpa.controller`
  - `TestController.java`
- `com.test.jpa.entity`
  - - `Item.java`
- `com.test.jpa.model`
  - `ItemDTO.java`
- `com.test.jpa.repository`
  - `ItemRepository.java`(I)
- `templates`: `result.html`
- 
#### 엔티티 클래스
- 역할: tblItem 테이블을 자바와 중계해주는 역할
- Java에서 item 클래스를 조작 -> (개발자는 개입X) -> DB의 tblItem에 반영
- DTO 역할과 일부 유사
- DTO는 단순한 상자, Entity는 기능이 많은 상자 정도..
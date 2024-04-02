# 프로젝트명

프로젝트에 대한 간단한 소개 및 목적을 기술합니다.

## 관련 링크
- [프로젝트 설계도(Figma)](https://www.figma.com/file/b8tROULbGSVe7N26f31zlD/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?type=design&node-id=0%3A1&mode=design&t=iMtZARC0JqIXbqtl-1)
- [데이터베이스 설계도(ERD Cloud)](https://www.erdcloud.com/d/Ma9ziJYfQaZ9bquvS)
- [API 문서(구글 시트)](https://docs.google.com/spreadsheets/d/1IWWC5rmi8mBa3hvy6ZgmAF24ZSfPu1dLKsrIT8RFtwU/edit?usp=sharing)

## 목차

- [기술 스택](#기술-스택)
- [개발 컨벤션](#개발-컨벤션)
  - [코드 컨벤션](#코드-컨벤션)
  - [커밋 컨벤션](#커밋-컨벤션)
  - [이슈 관리](#이슈-관리)
  - [브랜치 전략](#브랜치-전략)

## 기술 스택

프로젝트를 구현하는 데 사용된 기술들을 나열합니다.

- Java
- Spring Boot

## 개발 컨벤션

### 코드 컨벤션
[캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/)

### 커밋 컨벤션
#### [커밋 양식]  
```
- #[이슈번호] - 동작(feat, fix, ...) - [메인 내용]  
- ex) #3-feat-게시판 생성 로직 구현  
- ex) #3-fix-게시판 수정 로직 버그 수정  
```

#### [커밋 컨벤션]  
```
- feat : 새로운 기능 추가  
- fix : 버그 수정  
- refactor : 코드 리펙토링
- docs : 문서 수정
- test : 테스트 코드 작성 / 수정
- chore : 자잘한 수정 (오타 등)
- build : 빌드 관련 파일에 대한 커밋
- move : 파일 이동
- rename : 파일 이름 변경
- delete : 파일 삭제
- config : application.yml 파일에 대한 수정
- style : 코드 컨벤션 및 별도 코드 스타일 수정
```

### 이슈 관리

project 기반으로 이슈 관리

### 브랜치 전략
#### [브랜치 양식]
```
- [목적]/#[이슈번호]-[세부설명]
- ex) feature/#4-service-impl
- 이슈기반 브랜치
```

#### [목적 목록]
```
- feature
- fix
- docs
```
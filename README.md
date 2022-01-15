# 삼성 SDS 기업연계 프로젝트 ; SSURBAR
> 서울 기업연계 F101팀✨  
강현구 문성욱 조현민 최혜원  

![SSURBAR](https://user-images.githubusercontent.com/62040737/148211134-29d2b0ab-f229-4487-b35d-97cdd95b53cf.png)


<br>

### 🗂 Content
[🔈 프로젝트 소개](#-프로젝트-소개)
   <br>
   - [📑 타겟 및 목표](#-타겟-및-목표)
   - [📑 개발 환경](#-개발-환경)
   - [📑 핵심 기능](#-핵심-기능)
   - [📑 ERD Diagram](#-erd-diagram)
   - [📑 와이어프레임](#-와이어프레임)
   - [📑 목업](#-목업)
   - [📑 기능명세서](#-기능명세서)
   - [📑 스토리보드](#-스토리보드)
   - [📑 멘토링](#-멘토링)
   - [📑 팀 소개](#-팀-소개)
     <br>
<br><br>

## 💡 프로젝트 소개
### 프로젝트 명 : SSURBAR 📝
데이터 기반의 진단/분석 기능에 충실하여 
사내 다양한 설문을 손 쉽게 진행하고 분석할 수 있는 SaaS형 설문/분석 서비스

<br>

### 🔔 타겟 및 목표
#### 서비스 타겟 : 
- 사내 설문조사를 실시하고 이를 분석하는 리서처
<br>

#### 서비스 목표 :
- [X] 임직원을 위한 간편 설문조사 서비스  
- [X] 데이터 기반 진단/분석에 충실한 Tool  
- [X] 유저 흐름을 고려한 사용자 중심의 UI/UX  
<br>


<br>
<br>

### 📌 핵심 기능

![ssurbar1](https://user-images.githubusercontent.com/62040737/149624532-f2a27675-f55b-4a26-a58d-2363e9824eaf.png)

<br><br>

#### 1. 설문 작성  

![ssurbar2](https://user-images.githubusercontent.com/62040737/149624543-2c5afd9a-fef7-4cfd-9b80-a0a6ab3c7768.png)

- 익명성이 보장되고 응답에 제한이 없는 설문 생성  
- 기본 템플릿 제공  
- 지난 설문조사 템플릿 복사 기능 제공  


#### 2. 설문 결과 분석  

![ssurbar3](https://user-images.githubusercontent.com/62040737/149624549-368e8fdb-23b1-4bef-839c-bdb9e044ced7.png)
![ssurbar4](https://user-images.githubusercontent.com/62040737/149624548-b48459e1-49ec-4e4f-9b58-ccdc7dddc03f.png)

- 한눈에 들어오는 그래프  
- 항목별 설문 결과 수치화  

#### 3. 동일 설문 비교 
![ssurbar5](https://user-images.githubusercontent.com/62040737/149624560-9352fa64-35e8-4342-8fc3-a9e6ce679d15.png)
![ssurbar6](https://user-images.githubusercontent.com/62040737/149624564-9065cf44-70da-45bf-bcfd-a8e6b1e7947a.png)

- 그래프를 통한 비교  

<br>
<br>

### 📌 구현 기능

```
- 회원가입 및 로그인
  - 사용자가 로그인을 할 경우 관리자 계정에서 회원가입 요청을 승인해야 로그인이 가능하도록 구현  
- 설문 생성
  - 신규 설문 생성시, 유효성 검사를 거쳐 필터 및 카테고리가 생성되고 미리보기를 통해 생성되는 설문을 보여준다.
  - 생성 완료시 설문 응답이 가능한 링크와 설문 결과를 볼 수 있는 링크 제공
  - 기본 템플릿을 사용한 설문 생성 또한 가능하다.
- 결과 분석
  - 설문 응답 결과를 그래프로 수치화하여 나타낸다.
  - 설문 응답에 관한 피드백을 남길 수 있다.
  - 응답 결과에 필터를 적용하여 원하는 값의 결과보기를 제공한다.
  - 응답데이터를 엑셀로 추출할 수 있다.
- 설문 비교 
  - 같은 템플릿을 사용한 설문끼리 비교가 가능한 그래프를 제공한다.
- 설문 응답
  - 카테고리별로 질문을 볼 수 있다.
  - 반응형 웹 구현을 통해 모바일 환경에서도 응답이 가능하다.
```



### 🛠 개발 환경

- Server : AWS EC2
  - Ubuntu 20.04.1

<br>

- Backend
  - Java : Java 1.11.0
  - Framework : SpringBoot 2.4.5
  - ORM : JPA(Hibernate)
  - Nginx : 1.18.0 (Ubuntu)
  - IDE : Intellij 2021.1.3 / Visual Studio Code 1.59
  - Dependency tool : gradle-6.7
  - Database : MariaDB-10.3.31

<br>

- Frontend
  - HTML5, CSS3, Javascript(Es6)
  - Vue : 2.6.11

<br><br>


### 🔨 시스템 아키텍쳐
![ssurbar7](https://user-images.githubusercontent.com/62040737/149624570-a92c5f83-d05d-4a41-826e-76bd4a9fab87.png)

<br><br>

### 💎 기능명세서


![image (1)](https://user-images.githubusercontent.com/62040737/149624593-77967f57-46b7-4c92-9ffd-79d004fca07a.png)

🔗 자세히 보기 : https://docs.google.com/spreadsheets/d/1EWhW19g2spP30Q2yUSbq1GYBjWtOHhR2NHKgYrmg_Yk/edit#gid=0

<br><br>

### 📖 와이어프레임

![image (2)](https://user-images.githubusercontent.com/62040737/149624594-d6b8a3e3-0d63-48c5-8692-93e24d70435a.png)


🔗 자세히 보기 : https://www.figma.com/file/K6bO4JZOLAtoZkXMrfGnvI/%EA%B8%B0%EC%97%85%EC%97%B0%EA%B3%84?node-id=0%3A1

<br><br>

### 📌 ERD Diagram

![ssurbar8](https://user-images.githubusercontent.com/62040737/149624573-73d8858b-a600-4eaf-bc85-2504e6390ee1.png)

🔗 자세히 보기 : https://www.erdcloud.com/d/pWYeAFyPAHfEf5vTu

<br><br>


### 🎨 목업

![image (3)](https://user-images.githubusercontent.com/62040737/149624597-0ceac336-1738-4e1b-99d9-957cd009b95d.png)


🔗 자세히 보기 : https://www.figma.com/file/K6bO4JZOLAtoZkXMrfGnvI/%EA%B8%B0%EC%97%85%EC%97%B0%EA%B3%84?node-id=3%3A2

<br><br>

### 🎬 스토리보드

🔗 자세히 보기 : https://lab.ssafy.com/s05-final/S05P31F005/-/blob/master/docs/Storyboard/%EC%8A%A4%ED%86%A0%EB%A6%AC%EB%B3%B4%EB%93%9C.pdf

<br><br>

### 🙌 멘토링

🔗 자세히 보기 : https://lab.ssafy.com/s05-final/S05P31F005/-/tree/master/docs/%EB%A9%98%ED%86%A0%EB%A7%81

<br><br>

### 📢 팀 소개
![image (4)](https://user-images.githubusercontent.com/62040737/149624602-c9347999-f17e-4951-b783-75035b7422c9.png)

[🔗 노션 링크](https://www.notion.so/7ca9c079a57c44acb4a2926fd1ea218b)

<br><br>


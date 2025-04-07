# HeuronProject - 안드로이드 과제


## 🌟 프로젝트 개요

**HeuronProject**는 **Heuron 안드로이드 채용 과제 수행**을 위해 제작된 예제 앱입니다.  
제공된 요구사항에 따라 **Jetpack Compose**, **MVVM 아키텍처**, **클린 아키텍처**,  
**Retrofit** 기반으로 이미지 목록 및 상세 화면 기능을 구현하였습니다.

## 🌳 프로젝트 구조

- 📁 **app**
    - 📦 **com.heuron.heuronproject**
      - 📁 core — 공통유틸
      - 📁 data — 데이터 소스, API, Repository 구현
      - 📁 domain — UseCase 등 비즈니스 로직
      - 📁 presentation — View, ViewModel
      - 📁 ui.theme — Jetpack Compose 테마
      - 🧩 App.kt — 앱 진입 지점
      - 🧩 MainActivity.kt — 메인 컴포즈 Activity

## ✨ 주요 기능

### ✅ 이미지 목록 화면
- [Lorem Picsum API](https://picsum.photos/v2/list)에서 이미지 데이터를 가져옴
- id, author, width, height 정보를 리스트에 표시
- **LazyColumn**을 사용한 스크롤 리스트
- id, author, width, height 각각에 대해 텍스트 필터링
- **AND 조건** 필터 적용
- 일치하는 항목은 **초록색 배경**으로 하이라이팅

### 🖼️ 이미지 상세 화면
- 이미지 **컬러 / 흑백 토글**
- **핀치 줌 및 회전 제스처** 구현
- 리스트화면으로 돌아오는 경우 시 **스크롤 위치 유지**

## 📚 사용 라이브러리 및 사용 이유

| 라이브러리 | 설명 및 사용 이유 |
|------------|------------------|
| **Jetpack Compose** | 선언적 UI 개발을 위해 사용하였습니다. 코드량 감소와 직관적인 UI 상태 관리가 가능합니다.|
| **Retrofit** | REST API 요청을 하기위한 라이브러리 입니다.Coroutine과의 연동도 쉽다는 장점도 있어 선택하였습니다.|
| **Gson** | JSON 직렬화/역직렬화를 가능하게 하고 Retrofit과 함께 사용 합니다.|
| **OkHttp** | Retrofit의 내부 HTTP 클라이언트 라이브러리 입니다.|
| **Coil** | 비동기이미지 로딩 및 캐싱에 용의한 이미지라이브러리 입니다.|
| **Hilt** | DI(의존성 주입)을 하기 위해 사용하였고 ViewModel, Repository 등 구성요소에서 사용됩니다.|
| **Kotlin Coroutines** | 비동기 처리 및 상태관리를 위해 사용하였습니다.|

### 🚀 프로젝트 실행 순서
1. **GitHub에서 프로젝트 클론**
   ```bash
   git clone https://github.com/your-username/HeuronProject.git
   cd HeuronProject

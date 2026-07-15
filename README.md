# NotWaste
냉장고 속 식재료의 스마트한 관리와 유통기한 임박 알림을 통해 식재료 낭비를 줄이고 친환경적인 소비 습관을 돕는 Android 개인 프로젝트

---

## 📌 1. 프로젝트 개요
* **개발 기간:** 2025.11.16 ~ 2025.12.01 (약 2주)
* **참여 인원:** 개인 프로젝트
* **주요 역할:** 기획, UI/UX 디자인, 안드로이드 앱 개발 및 DB 설계

#### 💡 프로젝트 목표
  - 냉장고 속 식재료를 직관적으로 관리하고 유통기한 임박 알림을 제공하여 식재료를 적절한 시점에 소비할 수 있도록 유도한다.
  - 레시피 검색과 낭비 기록 기능을 결합하여, 단순한 관리를 넘어 사용자의 지속 가능한 음식 소비 습관 개선을 목표로 한다.
---

## <p>📚 2. STACKS

<div> 

### Environment
  <img src="https://img.shields.io/badge/androidstudio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white"> 
  <img src="https://img.shields.io/badge/git-F03C2E?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

### Development
  <img src="https://img.shields.io/badge/Java-FF6600?style=for-the-badge&logo=OpenJDK&logoColor=white">
  <img src="https://img.shields.io/badge/XML-005FAD?style=for-the-badge&logo=xml&logoColor=white">

### Database & Storage
  <img src="https://img.shields.io/badge/Room%20DB-SQLite?style=for-the-badge&logo=sqlite&logoColor=white&color=003B57">

  <br>
</div>

---

## 🎯 3. 주요 기능

#### **🧊 냉장고 관리 기능 (식재료 CRUD)**
  - 식재료 이름, 유통기한, 보관 위치(냉장/냉동/실온), 수량을 설정하여 냉장고에 등록
  - 등록된 식재료는 `RecyclerView`를 통해 목록으로 구현하였으며, 탭 레이아웃을 통해 보관 위치별로 필터링하여 파악 가능
  - 등록된 아이템 클릭 시 상세 화면으로 이동하여 정보를 수정하거나 삭제 가능
#### **🔔 유통기한 임박 알림 시스템**
  - 등록된 유통기한과 현재 날짜를 기반으로 D-Day를 실시간 계산하여 표시
  - `AlarmManager`와 `BroadcastReceiver`를 활용하여 지정된 특정 시간에 자동으로 유통기한 임박 알림을 발생시키고 `Notification`으로 전달
  - 사용자가 푸시 알림을 놓치더라도 앱 내부 알림 목록 DB에 누적 저장하여 언제든 다시 확인 가능
  - 사용자는 설정 화면에서 알림 사용 여부, 알림 수신 사전 기간(예: 3일 전, 7일 전 등), 선호하는 알림 발송 주기를 커스텀 가능
#### **🍳 맞춤형 레시피 검색**
  - 유통기한이 임박한 식재료를 어떻게 소비해야 할지 고민하는 사용자를 위해 바로 레시피를 검색해 볼 수 있는 `WebView`를 탑재
  - 외부 브라우저로 이동할 필요 없이 앱 내부에서 해당 식재료를 주재료로 한 레시피 검색 가능
#### **📊 마이페이지 (소비 습관 트래킹)**
  - 최근 일주일 내에 유통기한이 지나 폐기(낭비) 처리된 식재료 목록 확인 가능
  - 사용자는 자신의 음식 낭비 패턴을 인지하고, 향후 식재료 관리 습관 개선 가능

---

## 📂 4. 폴더 구조

```text
NotWaste/
│
├─ app/src/main/
│  ├─ java/com/example/notwaste/
│  │  ├─ data/
│  │  │  ├─ dao/
│  │  │  │  ├─ IngredientDao.java            # 식재료 테이블 접근 DAO
│  │  │  │  ├─ NotificationDao.java          # 알림 기록 테이블 접근 DAO
│  │  │  │  └─ NotificationSettingDao.java   # 알림 설정 테이블 접근 DAO
│  │  │  │
│  │  │  ├─ database/
│  │  │  │  └─ AppDatabase.java              # Room 데이터베이스 초기화 및 인스턴스 관리
│  │  │  │
│  │  │  └─ model/
│  │  │     ├─ Ingredient.java              # 식재료 데이터 모델
│  │  │     ├─ NotificationItem.java        # 알림 내역 데이터 모델
│  │  │     └─ NotificationSetting.java     # 알림 설정 정보 데이터 모델
│  │  │
│  │  ├─ receiver/
│  │  │  └─ ExpireAlarmReceiver.java        # 알람 이벤트를 수신하여 푸시 알림을 발생시키는 리시버
│  │  │
│  │  ├─ ui/                                # 화면 설계
│  │  │
│  │  └─ util/
│  │     ├─ AlarmScheduler.java             # AlarmManager 등록 및 스케줄링 유틸 클래스
│  │     └─ DateUtil.java                   # 디데이(D-Day) 및 날짜 계산 도우미 클래스
│  │
│  ├─ res/                                  # 앱 리소스 파일                        
│  │
│  └─ AndroidManifest.xml                   # 앱 구성요소 등록 및 알림 권한 정의
│
└─ README.md                                # 프로젝트 가이드 및 기술 기술서

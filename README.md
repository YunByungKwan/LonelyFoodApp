# 고독한 미식가

## Introduce.

> 내가 찾은 맛집의 위치와 그 곳에서 먹은 음식을 다이어리로 작성할 수 있습니다.
> <br>[구글 플레이 스토어 바로 가기](https://play.google.com/store/apps/details?id=org.ybk.fooddiaryapp)

### :point_right: Why.
> 인스타그램에 음식 사진만 올리는 사람들을 보고 영감을 받아 개발했습니다.

### :point_right: Trouble Shooting

- [[2021.05.26] BaseActivity, BaseFragment, BaseViewModel 추가](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.05.26%5D-BaseActivity,-BaseFragment,-BaseViewModel-%EC%B6%94%EA%B0%80)
- [[2021.05.20] 코틀린 GSON 관련 이슈](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.05.20%5D-%EC%BD%94%ED%8B%80%EB%A6%B0-GSON-%EA%B4%80%EB%A0%A8-%EC%9D%B4%EC%8A%88)
- [[2021.04.16] RxJava 도입](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.04.16%5D-RxJava-%EB%8F%84%EC%9E%85)
- [[2021.04.06] BindingAdapter 도입](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.04.06%5D-BindingAdapter-%EB%8F%84%EC%9E%85)
- [[2021.03.21] Unit Test 도입](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.03.21%5D-Unit-Test-%EB%8F%84%EC%9E%85)
- [[2021.03.16] DI 도입(Koin 라이브러리)](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.03.16%5D-DI-%EB%8F%84%EC%9E%85(Koin-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC))

### :point_right: Preview

<img src="https://user-images.githubusercontent.com/51109517/113472872-ddb16200-94a0-11eb-8731-9abdeda63754.gif" width=200 height=400/> <img src="https://user-images.githubusercontent.com/51109517/113472937-3ed93580-94a1-11eb-8ac1-91a2ae00452c.gif" width=200 height=400/> <img src="https://user-images.githubusercontent.com/51109517/113472941-43055300-94a1-11eb-901c-bb2d3a87e622.gif" width=200 height=400/> <img src="https://user-images.githubusercontent.com/51109517/113472940-413b8f80-94a1-11eb-8dd1-148084c23445.gif" width=200 height=400/>

### :point_right: About App

- <B>Language:</B> Kotlin
- <B>Libraries:</B> Android Jetpack(ViewModel, LiveData, DataBinding, Navigation), Firebase(Authentication, Realtime Database, Storage), Retrofit, RxJava, Koin, Mockito, hamcrest, Espresso, JUnit
- <B>How to</B>
  - Android Jetpack을 사용해서 MVVM 패턴으로 설계
  - Firebase Authentication을 연동하여 Google & Facebook 로그인
  - Firebase Realtime Database 사용
  - Firebase Storage를 이미지 저장소로 사용
  - Retrofit, RxJava를 통한 비동기 API 통신
  - Koin을 통한 의존성 주입
  - Mockito JUnit 등을 통한 단위 테스트 작성

### :point_right: Main Feature

- 구글, 페이스북 간편 로그인 기능
- 일기 작성,수정,삭제 기능
- 네이버 검색 API를 통한 맛집 검색 기능
- 음식(1 - 4개), 프로필 이미지 첨부 기능
- 일기 작성 중 swipe를 통한 이미지 삭제 기능
- 다른 회원 및 공유하기를 통해 다른 사람들의 공유 기능

### :point_right: Architecture

<img src="https://user-images.githubusercontent.com/51109517/115983229-f19d3f00-a5da-11eb-9720-4f27e267a319.png" width=700 height=400/>

### :point_right: Main Class Diagram

<img src="https://user-images.githubusercontent.com/51109517/118401924-91ae2b80-b6a2-11eb-957a-d49f5a03fc2d.png" width=700 height=400/>

<br>

## Main Process.

### :point_right: Login Process

<img src="https://user-images.githubusercontent.com/51109517/115982852-01b41f00-a5d9-11eb-8f55-08a7f3a1d735.jpg" width=700 height=400/>

### :point_right: Writing Process

<img src="https://user-images.githubusercontent.com/51109517/118347314-46e6c380-b57d-11eb-81b3-c82f79828974.png" width=700 height=400/>

## Test.

### :point_right: instrumentation tests
<details>
<summary>DiaryFragmentTest</summary>
<div markdown="1">

```kotlin
@RunWith(AndroidJUnit4::class)
class DiaryFragmentTest {

    @get:Rule
    val intentRule = IntentsTestRule(FragmentScenario.EmptyFragmentActivity::class.java)

    @Before
    fun set_up() {
        launchFragmentInContainer<DiaryFragment>()
    }

    @Test
    fun should_send_intent_to_AddDiaryActivity_if_FloatingActionButton_is_clicked() {
        val targetAct = "org.ybk.fooddiaryapp.ui.adddiary.AddDiaryActivity"
        Thread.sleep(3000)
        onView(withId(R.id.lottieView)).perform(click())
        intended(IntentMatchers.hasComponent(targetAct))
        Thread.sleep(1000)
    }
}
```

</div>
</details>

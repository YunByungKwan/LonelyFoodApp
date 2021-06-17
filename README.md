# 고독한 미식가 
고독한 미식가는 위치, 텍스트, 이미지 기반의 다이어리 앱입니다. ([Google Play Store](https://play.google.com/store/apps/details?id=org.ybk.fooddiaryapp))<br>
인스타그램에 음식 사진만 올리는 사람들을 보고 개발하게 되었습니다.<br>
<br>

## Skills
`Android Jetpack`(`ViewModel`, `LiveData`, `Room`, `DataBinding`, `Navigation`), `Firebase`(`Authentication`, `Realtime Database`, `Storage`), `Retrofit`, `RxJava`, `Dagger`, `Glide`, `Espresso`, `Mockito`, `JUnit4`, `hamcrest`<br>
<br>

### Process
- [Google & Facebook 로그인](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5BProcess%5D-Google-&-Facebook-%EB%A1%9C%EA%B7%B8%EC%9D%B8)
- [일기 작성](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5BProcess%5D-%EC%9D%BC%EA%B8%B0-%EC%9E%91%EC%84%B1)
- [검색 기능](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5BProcess%5D-%EA%B2%80%EC%83%89-%EA%B8%B0%EB%8A%A5)
<br>

### :fire: Trouble Shooting
- [[21.06.14] Firebase Storage의 업로드 속도가 느린 이유](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B21.06.14%5D-Firebase-Storage%EC%9D%98-%EC%97%85%EB%A1%9C%EB%93%9C-%EC%86%8D%EB%8F%84%EA%B0%80-%EB%8A%90%EB%A6%B0-%EC%9D%B4%EC%9C%A0)
- [[21.06.09] Unit Test 추가 및 수정(Test Double 중 Stub)](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B21.06.09%5D-Unit-Test-%EC%B6%94%EA%B0%80-%EB%B0%8F-%EC%88%98%EC%A0%95(Test-Double-%EC%A4%91-Stub))
- [[21.06.06] Room 네트워크 캐시 추가](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B21.06.06%5D-Room-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EC%BA%90%EC%8B%9C-%EC%B6%94%EA%B0%80)
- [[21.06.03] 의존성 주입 라이브러리 변경(Koin에서 Dagger로)](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B21.06.03%5D-%EC%9D%98%EC%A1%B4%EC%84%B1-%EC%A3%BC%EC%9E%85-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EB%B3%80%EA%B2%BD(Koin%EC%97%90%EC%84%9C-Dagger%EB%A1%9C))
- [[21.05.26] 일기 추가 코드 리팩토링](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.05.26%5D-%EC%9D%BC%EA%B8%B0-%EC%B6%94%EA%B0%80-%EC%BD%94%EB%93%9C-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81)
- [[21.05.26] BaseActivity, BaseFragment, BaseViewModel 추가](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.05.26%5D-BaseActivity,-BaseFragment,-BaseViewModel-%EC%B6%94%EA%B0%80)
- [[21.05.20] 코틀린 GSON 관련 이슈](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.05.20%5D-%EC%BD%94%ED%8B%80%EB%A6%B0-GSON-%EA%B4%80%EB%A0%A8-%EC%9D%B4%EC%8A%88)
- [[21.04.16] RxJava 도입](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.04.16%5D-RxJava-%EB%8F%84%EC%9E%85)
- [[21.04.06] BindingAdapter 도입](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.04.06%5D-BindingAdapter-%EB%8F%84%EC%9E%85)
- [[21.03.21] Unit Test 추가(Test Double 중 Fake)](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B21.03.21%5D-Unit-Test-%EC%B6%94%EA%B0%80(Test-Double-%EC%A4%91-Fake))
- [[21.03.16] 의존성 주입 라이브러리 도입(Koin)](https://github.com/YunByungKwan/LonelyFoodApp/wiki/%5B2021.03.16%5D-DI-%EB%8F%84%EC%9E%85(Koin-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC))
<br>

### Preview
<img src="https://user-images.githubusercontent.com/51109517/113472872-ddb16200-94a0-11eb-8731-9abdeda63754.gif" width=200 height=400/> <img src="https://user-images.githubusercontent.com/51109517/113472937-3ed93580-94a1-11eb-8ac1-91a2ae00452c.gif" width=200 height=400/> <img src="https://user-images.githubusercontent.com/51109517/113472941-43055300-94a1-11eb-901c-bb2d3a87e622.gif" width=200 height=400/> <img src="https://user-images.githubusercontent.com/51109517/113472940-413b8f80-94a1-11eb-8dd1-148084c23445.gif" width=200 height=400/>
<br>

### Architecture
<img src="https://user-images.githubusercontent.com/51109517/121285168-de3efc80-c918-11eb-8517-4a9856e86ff9.png" width=700 height=400/>
<br>

### Dagger Graph
<img src="https://user-images.githubusercontent.com/51109517/121352793-2c2f2100-c968-11eb-80dd-d82ba2bb8aab.png" width=700 height=400/>
<br>


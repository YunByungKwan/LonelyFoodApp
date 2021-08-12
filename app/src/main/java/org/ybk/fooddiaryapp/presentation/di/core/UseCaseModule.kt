package org.ybk.fooddiaryapp.presentation.di.core

import com.google.firebase.auth.AuthCredential
import dagger.Module
import dagger.Provides
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository
import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import org.ybk.fooddiaryapp.domain.repository.UserRepository
import org.ybk.fooddiaryapp.domain.usecase.*

@Module
class UseCaseModule {

    @Provides
    fun provideAddDiaryUseCase(diaryRepository: DiaryRepository): AddDiaryUseCase {
        return AddDiaryUseCase(diaryRepository)
    }

    @Provides
    fun provideUploadFoodImageUseCase(diaryRepository: DiaryRepository): UploadFoodImageUseCase {
        return UploadFoodImageUseCase(diaryRepository)
    }

    @Provides
    fun provideSignInFirebaseUseCase(userRepository: UserRepository): SignInFirebaseUseCase {
        return SignInFirebaseUseCase(userRepository)
    }

    @Provides
    fun provideDeleteDiaryUseCase(diaryRepository: DiaryRepository): DeleteDiaryUseCase {
        return DeleteDiaryUseCase(diaryRepository)
    }

    @Provides
    fun provideGetDiaryListUseCase(diaryRepository: DiaryRepository): GetDiaryListUseCase {
        return GetDiaryListUseCase(diaryRepository)
    }

    @Provides
    fun provideGetDiaryUseCase(diaryRepository: DiaryRepository): GetDiaryUseCase {
        return GetDiaryUseCase(diaryRepository)
    }

    @Provides
    fun provideGetOpenDiaryListUseCase(diaryRepository: DiaryRepository): GetOpenDiaryListUseCase {
        return GetOpenDiaryListUseCase(diaryRepository)
    }

    @Provides
    fun provideGetPlaceListUseCase(placeRepository: PlaceRepository): GetPlaceListUseCase {
        return GetPlaceListUseCase(placeRepository)
    }

    @Provides
    fun provideGetProfileImageUseCase(profileRepository: ProfileRepository): GetProfileImageUseCase {
        return GetProfileImageUseCase(profileRepository)
    }

    @Provides
    fun provideUploadProfileImageUseCase(profileRepository: ProfileRepository): UploadProfileImageUseCase {
        return UploadProfileImageUseCase(profileRepository)
    }

    @Provides
    fun provideAddProfileImagePathUseCase(profileRepository: ProfileRepository): AddProfileImagePathUseCase {
        return AddProfileImagePathUseCase(profileRepository)
    }

    @Provides
    fun provideWithDrawFirebaseUseCase(userRepository: UserRepository): WithDrawFirebaseUseCase {
        return WithDrawFirebaseUseCase(userRepository)
    }

    @Provides
    fun provideRecommendRestaurantUseCase(diaryRepository: DiaryRepository): RecommendRestaurantUseCase {
        return RecommendRestaurantUseCase(diaryRepository)
    }

    @Provides
    fun provideUpdateDiaryToOpenUseCase(diaryRepository: DiaryRepository): UpdateDiaryToOpenUseCase {
        return UpdateDiaryToOpenUseCase(diaryRepository)
    }
}
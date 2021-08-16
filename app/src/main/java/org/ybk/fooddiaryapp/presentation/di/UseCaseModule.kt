package org.ybk.fooddiaryapp.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository
import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import org.ybk.fooddiaryapp.domain.repository.UserRepository
import org.ybk.fooddiaryapp.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideRecommendRestaurantUseCase(
        diaryRepository: DiaryRepository
    ): RecommendRestaurantUseCase {
        return RecommendRestaurantUseCase(diaryRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateDiaryToOpenUseCase(
        diaryRepository: DiaryRepository
    ): UpdateDiaryToOpenUseCase {
        return UpdateDiaryToOpenUseCase(diaryRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteDiaryUseCase(
        diaryRepository: DiaryRepository
    ): DeleteDiaryUseCase {
        return DeleteDiaryUseCase(diaryRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddDiaryUseCase(diaryRepository: DiaryRepository): AddDiaryUseCase {
        return AddDiaryUseCase(diaryRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUploadFoodImageUseCase(diaryRepository: DiaryRepository): UploadFoodImageUseCase {
        return UploadFoodImageUseCase(diaryRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetDiaryListUseCase(
        diaryRepository: DiaryRepository
    ): GetDiaryListUseCase {
        return GetDiaryListUseCase(diaryRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetDiaryUseCase(
        diaryRepository: DiaryRepository
    ): GetDiaryUseCase {
        return GetDiaryUseCase(diaryRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignInFirebaseUseCase(
        userRepository: UserRepository
    ): SignInFirebaseUseCase {
        return SignInFirebaseUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUploadProfileImageUseCase(
        profileRepository: ProfileRepository
    ): UploadProfileImageUseCase {
        return UploadProfileImageUseCase(profileRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetProfileImageUseCase(
        profileRepository: ProfileRepository
    ): GetProfileImageUseCase {
        return GetProfileImageUseCase(profileRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideAddProfileImagePathUseCase(
        profileRepository: ProfileRepository
    ): AddProfileImagePathUseCase {
        return AddProfileImagePathUseCase(profileRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetPlaceListUseCase(
        placeRepository: PlaceRepository
    ): GetPlaceListUseCase {
        return GetPlaceListUseCase(placeRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideWithDrawFirebaseUseCase(
        userRepository: UserRepository
    ): WithDrawFirebaseUseCase {
        return WithDrawFirebaseUseCase(userRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetOpenDiaryListUseCase(
        diaryRepository: DiaryRepository
    ): GetOpenDiaryListUseCase {
        return GetOpenDiaryListUseCase(diaryRepository)
    }
}
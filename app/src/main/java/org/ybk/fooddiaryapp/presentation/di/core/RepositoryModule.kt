package org.ybk.fooddiaryapp.presentation.di.core

import dagger.Module
import dagger.Provides
import org.ybk.fooddiaryapp.data.repository.diary.DiaryRepositoryImpl
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryLocalDataSource
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryRemoteDataSource
import org.ybk.fooddiaryapp.data.repository.place.PlaceRepositoryImpl
import org.ybk.fooddiaryapp.data.repository.place.datasource.PlaceRemoteDataSource
import org.ybk.fooddiaryapp.data.repository.profile.ProfileRepositoryImpl
import org.ybk.fooddiaryapp.data.repository.profile.datasource.ProfileRemoteDataSource
import org.ybk.fooddiaryapp.data.repository.user.UserRepositoryImpl
import org.ybk.fooddiaryapp.data.repository.user.datasource.UserRemoteDataSource
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository
import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import org.ybk.fooddiaryapp.domain.repository.UserRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDiaryRepository(
        diaryLocalDataSource: DiaryLocalDataSource,
        diaryRemoteDataSource: DiaryRemoteDataSource
    ): DiaryRepository {
        return DiaryRepositoryImpl(diaryLocalDataSource, diaryRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providePlaceRepository(
        placeRemoteDataSource: PlaceRemoteDataSource
    ): PlaceRepository {
        return PlaceRepositoryImpl(placeRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileRemoteDataSource: ProfileRemoteDataSource
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }
}
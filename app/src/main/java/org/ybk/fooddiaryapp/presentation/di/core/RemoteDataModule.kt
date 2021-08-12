package org.ybk.fooddiaryapp.presentation.di.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import org.ybk.fooddiaryapp.data.api.NaverApiService
import org.ybk.fooddiaryapp.data.db.DiaryDao
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryLocalDataSource
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryRemoteDataSource
import org.ybk.fooddiaryapp.data.repository.diary.datasourceimpl.DiaryDiaryLocalDataSourceImpl
import org.ybk.fooddiaryapp.data.repository.diary.datasourceimpl.DiaryRemoteDataSourceImpl
import org.ybk.fooddiaryapp.data.repository.place.datasource.PlaceRemoteDataSource
import org.ybk.fooddiaryapp.data.repository.place.datasourceimpl.PlaceRemoteDataSourceImpl
import org.ybk.fooddiaryapp.data.repository.profile.datasource.ProfileRemoteDataSource
import org.ybk.fooddiaryapp.data.repository.profile.datasourceimpl.ProfileRemoteDataSourceImpl
import org.ybk.fooddiaryapp.data.repository.user.datasource.UserRemoteDataSource
import org.ybk.fooddiaryapp.data.repository.user.datasourceimpl.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(
        diaryDao: DiaryDao
    ): DiaryLocalDataSource {
        return DiaryDiaryLocalDataSourceImpl(diaryDao)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        mDatabase: FirebaseDatabase,
        mStorage: FirebaseStorage,
        mFirestore: FirebaseFirestore
    ): DiaryRemoteDataSource {
        return DiaryRemoteDataSourceImpl(mDatabase, mStorage, mFirestore)
    }

    @Singleton
    @Provides
    fun providePlaceRemoteDataSource(naverApiService: NaverApiService): PlaceRemoteDataSource {
        return PlaceRemoteDataSourceImpl(naverApiService)
    }

    @Singleton
    @Provides
    fun provideProfileRemoteDataSource(
        mDatabase: FirebaseDatabase,
        mStorage: FirebaseStorage
    ): ProfileRemoteDataSource {
        return ProfileRemoteDataSourceImpl(mDatabase, mStorage)
    }

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(mAuth: FirebaseAuth): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(mAuth)
    }
}
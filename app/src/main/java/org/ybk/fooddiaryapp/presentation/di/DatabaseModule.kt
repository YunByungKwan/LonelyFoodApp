package org.ybk.fooddiaryapp.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ybk.fooddiaryapp.data.db.DiaryDao
import org.ybk.fooddiaryapp.data.db.DiaryDatabase
import org.ybk.fooddiaryapp.util.MyApplication

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDiaryDao(): DiaryDao {
        return DiaryDatabase.getInstance(MyApplication.instance)!!.diaryDao()
    }
}
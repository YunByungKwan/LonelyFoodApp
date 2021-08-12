package org.ybk.fooddiaryapp.presentation.di.core

import dagger.Module
import dagger.Provides
import org.ybk.fooddiaryapp.data.db.DiaryDao
import org.ybk.fooddiaryapp.data.db.DiaryDatabase
import org.ybk.fooddiaryapp.util.MyApplication

@Module
class DatabaseModule {
    @Provides
    fun provideDiaryDao(): DiaryDao {
        return DiaryDatabase.getInstance(MyApplication.instance)!!.diaryDao()
    }
}
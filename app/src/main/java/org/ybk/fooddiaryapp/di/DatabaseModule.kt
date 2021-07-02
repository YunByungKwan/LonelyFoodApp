package org.ybk.fooddiaryapp.di

import dagger.Module
import dagger.Provides
import org.ybk.fooddiaryapp.data.local.dao.DiaryDao
import org.ybk.fooddiaryapp.data.local.db.DiaryDatabase
import org.ybk.fooddiaryapp.util.MyApplication

@Module
class DatabaseModule {
    @Provides
    fun provideDiaryDao(): DiaryDao {
        return DiaryDatabase.getInstance(MyApplication.instance)!!.diaryDao()
    }
}
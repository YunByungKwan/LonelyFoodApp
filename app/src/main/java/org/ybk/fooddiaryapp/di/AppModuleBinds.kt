package org.ybk.fooddiaryapp.di

import dagger.Binds
import dagger.Module
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.data.DiaryRepositoryImpl
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(
        repository: DiaryRepositoryImpl
    ): DiaryRepository
}
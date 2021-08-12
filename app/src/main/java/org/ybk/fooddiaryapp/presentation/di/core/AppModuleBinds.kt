package org.ybk.fooddiaryapp.presentation.di.core

import dagger.Binds
import dagger.Module
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.repository.diary.DiaryRepositoryImpl
import javax.inject.Singleton

//@Module
//abstract class AppModuleBinds {
//
//    @Singleton
//    @Binds
//    abstract fun bindRepository(
//        repository: DiaryRepositoryImpl
//    ): DiaryRepository
//}
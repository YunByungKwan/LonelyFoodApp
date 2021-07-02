package org.ybk.fooddiaryapp.di

import dagger.Binds
import dagger.Module
import org.ybk.fooddiaryapp.data.local.LocalDataSource
import org.ybk.fooddiaryapp.data.local.LocalDataSourceImpl
import org.ybk.fooddiaryapp.data.remote.RemoteDataSource
import org.ybk.fooddiaryapp.data.remote.RemoteDataSourceImpl
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Singleton
    @Local
    @Binds
    abstract fun bindLocalDataSource(
        localDataSource: LocalDataSourceImpl
    ): LocalDataSource

    @Singleton
    @Remote
    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ): RemoteDataSource
}


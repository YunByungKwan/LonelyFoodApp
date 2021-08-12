package org.ybk.fooddiaryapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.ybk.fooddiaryapp.di.*
import org.ybk.fooddiaryapp.presentation.di.adddiary.AddDiaryComponent
import org.ybk.fooddiaryapp.presentation.di.core.*
import org.ybk.fooddiaryapp.presentation.di.dialog.DotDialogComponent
import org.ybk.fooddiaryapp.presentation.di.diary.DiaryComponent
import org.ybk.fooddiaryapp.presentation.di.editdiary.EditDiaryComponent
import org.ybk.fooddiaryapp.presentation.di.login.LoginComponent
import org.ybk.fooddiaryapp.presentation.di.map.MapComponent
import org.ybk.fooddiaryapp.presentation.di.profile.ProfileComponent
import org.ybk.fooddiaryapp.presentation.di.search.SearchComponent
import org.ybk.fooddiaryapp.presentation.di.settings.SettingsComponent
import org.ybk.fooddiaryapp.presentation.di.share.ShareComponent
import org.ybk.fooddiaryapp.util.MyApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    // AppModule::class,
    // AppModuleBinds::class,
    // ViewModelBuilderModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    AndroidSupportInjectionModule::class,
    SubcomponentsModule::class,
    UseCaseModule::class,
    RepositoryModule::class,
    RemoteDataModule::class
])
interface AppComponent: AndroidInjector<MyApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun addDiaryComponent(): AddDiaryComponent.Factory
    fun dotDialogComponent(): DotDialogComponent.Factory
    fun diaryComponent(): DiaryComponent.Factory
    fun editDiaryComponent(): EditDiaryComponent.Factory
    fun loginComponent(): LoginComponent.Factory
    fun mapComponent(): MapComponent.Factory
    fun profileComponent(): ProfileComponent.Factory
    fun searchComponent(): SearchComponent.Factory
    fun settingsComponent(): SettingsComponent.Factory
    fun shareComponent(): ShareComponent.Factory
}

@Module(
    subcomponents = [
        AddDiaryComponent::class,
        DotDialogComponent::class,
        DiaryComponent::class,
        EditDiaryComponent::class,
        LoginComponent::class,
        MapComponent::class,
        ProfileComponent::class,
        SearchComponent::class,
        SettingsComponent::class,
        ShareComponent::class
    ]
)
object SubcomponentsModule
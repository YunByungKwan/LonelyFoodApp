package org.ybk.fooddiaryapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.ybk.fooddiaryapp.di.*
import org.ybk.fooddiaryapp.ui.adddiary.di.AddDiaryComponent
import org.ybk.fooddiaryapp.ui.dialog.di.DotDialogComponent
import org.ybk.fooddiaryapp.ui.diary.di.DiaryComponent
import org.ybk.fooddiaryapp.ui.editdiary.di.EditDiaryComponent
import org.ybk.fooddiaryapp.ui.login.di.LoginComponent
import org.ybk.fooddiaryapp.ui.map.di.MapComponent
import org.ybk.fooddiaryapp.ui.profile.di.ProfileComponent
import org.ybk.fooddiaryapp.ui.search.di.SearchComponent
import org.ybk.fooddiaryapp.ui.settings.di.SettingsComponent
import org.ybk.fooddiaryapp.ui.share.di.ShareComponent
import org.ybk.fooddiaryapp.util.MyApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AppModuleBinds::class,
    ViewModelBuilderModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    AndroidSupportInjectionModule::class,
    SubcomponentsModule::class
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
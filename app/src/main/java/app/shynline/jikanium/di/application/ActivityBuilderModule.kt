package app.shynline.jikanium.di.application

import app.shynline.jikanium.MainActivity
import app.shynline.jikanium.di.application.mainactivity.FragmentBuilderModule
import app.shynline.jikanium.di.application.mainactivity.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            FragmentBuilderModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}
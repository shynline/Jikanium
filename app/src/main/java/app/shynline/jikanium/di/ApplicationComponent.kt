package app.shynline.jikanium.di

import android.content.Context
import app.shynline.jikanium.BaseApplication
import app.shynline.jikanium.di.application.ActivityBuilderModule
import app.shynline.jikanium.di.application.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityBuilderModule::class
    ]
)
abstract class ApplicationComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }
}
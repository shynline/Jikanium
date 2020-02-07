package app.shynline.jikanium.di.application

import app.shynline.jikanium.JikanActivity
import app.shynline.jikanium.di.application.mainactivity.FragmentBuilderModule
import app.shynline.jikanium.di.application.mainactivity.JikanActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/***
 * Activity Builder module
 */
@Module
abstract class ActivityBuilderModule {
    /***
     * Injecting Jikan Activity
     */
    @ContributesAndroidInjector(
        modules = [
            JikanActivityModule::class,
            FragmentBuilderModule::class
        ]
    )
    abstract fun jikanActivity(): JikanActivity
}
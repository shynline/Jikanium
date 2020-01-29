package app.shynline.jikanium.di.application.mainactivity

import androidx.lifecycle.ViewModel
import app.shynline.jikanium.di.ViewModelBuilder
import app.shynline.jikanium.di.ViewModelKey
import app.shynline.jikanium.di.application.mainactivity.fragments.AnimeFragmentModule
import app.shynline.jikanium.ui.anime.AnimeFragment
import app.shynline.jikanium.ui.anime.AnimeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class,
            AnimeFragmentModule::class
        ]
    )
    abstract fun animeFragment(): AnimeFragment

    @Binds
    @IntoMap
    @ViewModelKey(AnimeViewModel::class)
    abstract fun bindViewModel(viewModel: AnimeViewModel): ViewModel

}
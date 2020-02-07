package app.shynline.jikanium.di.application.mainactivity

import androidx.lifecycle.ViewModel
import app.shynline.jikanium.di.ViewModelBuilder
import app.shynline.jikanium.di.ViewModelKey
import app.shynline.jikanium.di.application.mainactivity.fragments.AnimeFragmentModule
import app.shynline.jikanium.ui.anime.AnimeFragment
import app.shynline.jikanium.ui.anime.AnimeViewModel
import app.shynline.jikanium.ui.animelistbygenre.AnimeListByGenreFragment
import app.shynline.jikanium.ui.animelistbygenre.AnimeListByGenreViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/***
 * Fragment Builder Module
 */
@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class,
            AnimeFragmentModule::class
        ]
    )
    abstract fun animeFragment(): AnimeFragment

    /***
     * Injecting anime fragment view model
     */
    @Binds
    @IntoMap
    @ViewModelKey(AnimeViewModel::class)
    abstract fun bindAnimeViewModel(viewModel: AnimeViewModel): ViewModel


    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    abstract fun animeListByGenreFragment(): AnimeListByGenreFragment

    /***
     * Injecting Anime list by genre view model
     */
    @Binds
    @IntoMap
    @ViewModelKey(AnimeListByGenreViewModel::class)
    abstract fun bindAnimeListByGenreViewModel(viewModel: AnimeListByGenreViewModel): ViewModel


}
package app.shynline.jikanium.ui.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/***
 * Anime View model
 */
class AnimeViewModel(
    private val animeRepository: AnimeRepository
) : ViewModel() {


    private val _anime = MutableLiveData<Anime>()
    val anime: LiveData<Anime> = _anime

    fun init(id: Long) {
        viewModelScope.launch {
            animeRepository.getAnime(id).collect {
                when (it) {
                    is Result.Success -> {
                        _anime.value = it.data
                    }
                    Result.NotModified -> {
                    }
                    Result.Loading -> {
                    }
                    is Result.Error -> {
                    }
                }
            }
        }

    }

}

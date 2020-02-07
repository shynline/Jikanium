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
import javax.inject.Inject

/***
 * Anime View model
 */
class AnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {


    private val _anime = MutableLiveData<Anime>()
    val anime: LiveData<Anime> = _anime

    init {
        viewModelScope.launch {
            animeRepository.getAnime(1).collect {
                when (it) {
                    is Result.Success -> {
                        _anime.value = it.data
                    }
                    Result.NotModified -> TODO()
                    Result.Loading -> TODO()
                    is Result.Error -> TODO()
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
    }
}

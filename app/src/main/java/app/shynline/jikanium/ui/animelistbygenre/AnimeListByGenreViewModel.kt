package app.shynline.jikanium.ui.animelistbygenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.GenreRepository
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeListByGenreViewModel(
    private val genreRepository: GenreRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<AnimePart>>().apply { value = emptyList() }
    val items: LiveData<List<AnimePart>> = _items
    private var page = 1

    private val _loaded = MutableLiveData<Event<Unit>>()
    val loaded: LiveData<Event<Unit>> = _loaded

    private val _animeDetail = MutableLiveData<Event<Long>>()
    val animeDetail: LiveData<Event<Long>> = _animeDetail


    private fun resetState() {
        _items.value = emptyList()
        page = 1
    }

    fun openAnimeDetail(id: Long) {
        _animeDetail.value = Event(id)
    }


    var genre = 0
        set(value) {
            field = value
            resetState()
            load()
        }

    /***
     * Load next page
     */
    fun load() {
        viewModelScope.launch {
            genreRepository.getAnimeListByGenre(genre, page).collect {
                if (it is Result.Loading)
                    return@collect
                if (it is Result.Success) {
                    _items.value = (_items.value ?: emptyList()).toMutableList()
                        .apply { addAll(it.data.sortedBy { item -> item.id }) }
                    page += 1
                }
                _loaded.value = Event(Unit)
            }
        }
    }

}
package app.shynline.jikanium.ui.animelistbygenre

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import coil.api.load


@BindingAdapter(value = ["imageUrl"])
fun loadImageUrl(view: ImageView, url: String) {
    view.load(url)
}

/**
 * [BindingAdapter]s for the [AnimePart]s list.
 */
@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<AnimePart>) {
    (listView.adapter as? AnimeListAdapter)?.submitList(items)
}
package app.shynline.jikanium

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.ui.animelistbygenre.AnimeListAdapter
import coil.api.load
import coil.transform.CircleCropTransformation


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

@BindingAdapter(value = ["loadImageUrlCircleCrop"])
fun loadImageUrlCircleCrop(view: ImageView, url: String?) {
    view.load(url) {
        transformations(CircleCropTransformation())
    }
}
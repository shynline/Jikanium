package app.shynline.jikanium.ui.animelistbygenre

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter(value = ["imageUrl"])
fun loadImageUrl(view: ImageView, url: String) {
    Glide.with(view).load(url)
        .apply(RequestOptions().centerCrop()).into(view)
}

/**
 * [BindingAdapter]s for the [AnimePart]s list.
 */
@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<AnimePart>) {
    (listView.adapter as? AnimeListAdapter)?.submitList(items)
}
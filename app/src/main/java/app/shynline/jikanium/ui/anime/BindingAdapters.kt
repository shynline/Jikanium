package app.shynline.jikanium.ui.anime

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation


@BindingAdapter(value = ["imageUrl"])
fun loadImageUrlCircleCrop(view: ImageView, url: String) {
    view.load(url) {
        transformations(CircleCropTransformation())
    }
}
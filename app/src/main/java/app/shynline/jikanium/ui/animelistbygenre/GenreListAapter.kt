package app.shynline.jikanium.ui.animelistbygenre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.shynline.jikanium.constants.AnimeGenre
import app.shynline.jikanium.databinding.CategoryItemBinding

/***
 * Genre list adapter
 */
class GenreListAdapter(private val listener: GenreClickListener) :
    ListAdapter<AnimeGenre, GenreListAdapter.ViewHolder>(
        GenreListDiffCallback()
    ) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }


    class ViewHolder private constructor(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: GenreClickListener, item: AnimeGenre) {
            binding.listener = listener
            binding.genre = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }

    /***
     * An interface to notify click event
     */
    interface GenreClickListener {
        fun onClick(animeGenre: AnimeGenre)
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class GenreListDiffCallback : DiffUtil.ItemCallback<AnimeGenre>() {
    override fun areItemsTheSame(oldItem: AnimeGenre, newItem: AnimeGenre): Boolean {
        return oldItem.value == newItem.value
    }

    override fun areContentsTheSame(oldItem: AnimeGenre, newItem: AnimeGenre): Boolean {
        return oldItem == newItem
    }
}
package app.shynline.jikanium.ui.animelistbygenre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.databinding.AnimeItemBinding

/***
 * Anime list adapter
 */
class AnimeListAdapter(private val viewModel: AnimeListByGenreViewModel) :
    ListAdapter<AnimePart, AnimeListAdapter.ViewHolder>(AnimePartDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun submitList(list: List<AnimePart>?) {
        super.submitList(list?.let { ArrayList(it) })
    }


    class ViewHolder private constructor(val binding: AnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /***
         * bind method should be called in onBindViewHolder
         */
        fun bind(viewModel: AnimeListByGenreViewModel, item: AnimePart) {
            binding.viewmodel = viewModel
            binding.anime = item
            binding.executePendingBindings()
        }

        companion object {
            /***
             * Create ViewHolder
             */
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AnimeItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class AnimePartDiffCallback : DiffUtil.ItemCallback<AnimePart>() {
    override fun areItemsTheSame(oldItem: AnimePart, newItem: AnimePart): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimePart, newItem: AnimePart): Boolean {
        return oldItem.id == newItem.id
    }
}
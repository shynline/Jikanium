package app.shynline.jikanium.ui.genrelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.shynline.jikanium.R
import app.shynline.jikanium.constants.AnimeGenre
import app.shynline.jikanium.constants.animeGenres
import app.shynline.jikanium.databinding.FragmentGenreListBinding
import app.shynline.jikanium.ui.animelistbygenre.GenreListAdapter

class GenreListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentGenreListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentGenreListBinding.inflate(inflater, container, false)

        viewDataBinding.list.layoutManager =
            GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        viewDataBinding.list.adapter =
            GenreListAdapter(object :
                GenreListAdapter.GenreClickListener {
                override fun onClick(animeGenre: AnimeGenre) {
                    findNavController().navigate(R.id.navigation_animeListByGenreFragment)
                }
            }).apply {
                submitList(animeGenres)
            }


        return viewDataBinding.root
    }
}
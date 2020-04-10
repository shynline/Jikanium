package app.shynline.jikanium.ui.animelistbygenre

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.shynline.jikanium.JikanActivity
import app.shynline.jikanium.R
import app.shynline.jikanium.constants.AnimeGenre
import app.shynline.jikanium.constants.animeGenres
import app.shynline.jikanium.databinding.AnimeListByGenreFragmentBinding
import com.google.android.flexbox.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/***
 * Anime List By Genre Fragment
 */
class AnimeListByGenreFragment : Fragment() {

    companion object {
        fun newInstance() = AnimeListByGenreFragment()
        const val TYPE_ANIME = 1
    }


    private lateinit var flm: FlexboxLayoutManager
    private var type = TYPE_ANIME
    private var genre = 0
    private var loading = false
    private var filterOpened = false
    private var isTransitioning = false


    private lateinit var viewDataBinding: AnimeListByGenreFragmentBinding

    private val viewModel: AnimeListByGenreViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AnimeListByGenreFragmentBinding.inflate(
            inflater,
            container, false
        ).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val glm = GridLayoutManager(
            context, 3,
            RecyclerView.VERTICAL, false
        )
        viewDataBinding.list.layoutManager = glm
        viewDataBinding.list.adapter = AnimeListAdapter(viewModel)
        viewDataBinding.list.itemAnimator
        viewDataBinding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var pastVisibleItems = 0
            var visibleItemCount: Int = 0
            var totalItemCount: Int = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = glm.childCount
                    totalItemCount = glm.itemCount
                    pastVisibleItems = glm.findFirstVisibleItemPosition()

                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = true
                            viewModel.load()
                        }
                    }
                }
            }
        })


        viewDataBinding.motionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                // Not needed
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                if (filterOpened) {
                    viewDataBinding.image.setImageDrawable(context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.avd_anim
                        )
                    })
                } else {
                    viewDataBinding.image.setImageDrawable(context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.avd_anim_reverse
                        )
                    })
                }
                filterOpened = filterOpened.not()
                (viewDataBinding.image.drawable as AnimatedVectorDrawable).start()
                p0?.isInteractionEnabled = false
                isTransitioning = true
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                // Not needed
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (filterOpened.not()) {
                    p0?.isInteractionEnabled = true
                }
                isTransitioning = false
            }

        })


        viewDataBinding.image.setOnClickListener {
            if (isTransitioning) return@setOnClickListener
            if (filterOpened) {
                viewDataBinding.motionLayout.transitionToStart()
            } else {
                viewDataBinding.motionLayout.transitionToEnd()
            }
        }

        flm = FlexboxLayoutManager(context)
        flm.flexDirection = FlexDirection.ROW
        flm.flexWrap = FlexWrap.WRAP
        flm.alignItems = AlignItems.FLEX_START
        flm.justifyContent = JustifyContent.FLEX_START
        viewDataBinding.genreList.layoutManager = flm

        viewDataBinding.genreList.adapter =
            GenreListAdapter(object :
                GenreListAdapter.GenreClickListener {
                override fun onClick(animeGenre: AnimeGenre) {
                    if (isTransitioning) return
                    if (filterOpened) {
                        if (viewModel.genre != animeGenre.value) {
                            viewModel.genre = animeGenre.value
                            (activity as? JikanActivity)?.supportActionBar?.title = animeGenre.name
                        }

                        viewDataBinding.motionLayout.transitionToStart()
                    } else {
                        viewDataBinding.motionLayout.transitionToEnd()
                    }
                }
            }).apply {
                submitList(animeGenres)
            }



        return viewDataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding.list.clearOnScrollListeners()
    }


}
package app.shynline.jikanium.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.shynline.jikanium.databinding.AnimeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/***
 * Anime Fragment
 *  Details of an anime
 */
class AnimeFragment : Fragment() {

    private val viewModel: AnimeViewModel by viewModel()
    private lateinit var viewDataBinding: AnimeFragmentBinding
    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = AnimeFragmentArgs.fromBundle(it).id
        }
        if (id == 0L) {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AnimeFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        if (savedInstanceState == null)
            viewModel.init(id)

        return viewDataBinding.root
    }


}

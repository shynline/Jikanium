package app.shynline.jikanium.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import app.shynline.jikanium.databinding.AnimeFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AnimeFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AnimeViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: AnimeFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AnimeFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return viewDataBinding.root
    }


}

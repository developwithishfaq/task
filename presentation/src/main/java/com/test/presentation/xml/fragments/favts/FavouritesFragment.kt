package com.test.presentation.xml.fragments.favts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.framework.core.core.makeGone
import com.test.framework.core.core.makeVisible
import com.test.presentation.databinding.FragmentFavouritesBinding
import com.test.presentation.xml.core.adapaters.PhotosAdapter
import com.test.presentation.xml.core.interfaces.AdapterListener
import com.test.presentation.xml.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : BaseFragment() {

    private val viewModel: FavtsFragmentViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null

    @Inject
    lateinit var photosAdapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initClickListener()
        collectState()

        setBackListener {
            getNavController()?.navigateUp()
        }

    }

    private fun initClickListener() {
        _binding?.let { binding ->
            binding.clearAll.setOnClickListener {
                viewModel.onEvent(FavtScreenEvents.ClearAll)
            }
        }
    }

    private fun initAdapter() {
        _binding?.let { binding ->
            binding.recycler.makeVisible()
            binding.recycler.apply {
                adapter = photosAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
            photosAdapter.attach(listener = object : AdapterListener {
                override fun onFavtClick(position: Int) {
                    val item = photosAdapter.currentList[position]
                    viewModel.onEvent(FavtScreenEvents.RemoveFromFav(item))
                }

                override fun onClick(position: Int) {
                }
            })
        }
    }

    private fun collectState() {
        _binding?.let { binding ->
            viewLifecycleOwner.lifecycleScope.apply {
                launch {
                    viewModel.images.collectLatest { images ->
                        binding.emptyLayout.makeVisible(images.isEmpty())
                        binding.clearAll.makeGone(images.isEmpty())
                        photosAdapter.submitList(images)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstance() = FavouritesFragment()
    }
}
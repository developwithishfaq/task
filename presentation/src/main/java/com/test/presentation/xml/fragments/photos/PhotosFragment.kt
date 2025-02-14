package com.test.presentation.xml.fragments.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.framework.core.NetworkResponse
import com.test.framework.core.core.makeGone
import com.test.framework.core.core.makeVisible
import com.test.presentation.databinding.FragmentPhotosBinding
import com.test.presentation.xml.core.adapaters.PhotosAdapter
import com.test.presentation.xml.core.interfaces.AdapterListener
import com.test.presentation.xml.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : BaseFragment() {
    private val TAG = "PhotosFragment"

    private var _binding: FragmentPhotosBinding? = null

    private val viewModel: PhotosFragmentViewModel by activityViewModels()

    @Inject
    lateinit var photosAdapter: PhotosAdapter

    private var canRefresh = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        collectState()
        initClickListeners()

    }

    private fun initClickListeners() {
        _binding?.let { binding ->
            binding.showFavt.setOnClickListener {
                canRefresh = true
                val action = PhotosFragmentDirections.actionPhotosFragmentToFavouritesFragment()
                getNavController()?.navigate(action)
            }
        }
    }

    private fun initAdapter() {
        _binding?.let { binding ->
            binding.recycler.apply {
                adapter = photosAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
            photosAdapter.attach(object : AdapterListener {
                override fun onFavtClick(position: Int) {
                    val model = photosAdapter.currentList[position]
                    viewModel.onEvent(PhotosScreenEvents.OnFavt(model))
                }

                override fun onClick(position: Int) {
                    val item = photosAdapter.currentList[position]
                    viewModel.onEvent(PhotosScreenEvents.SetImageModel(item))
                    val action =
                        PhotosFragmentDirections.actionPhotosFragmentToPhotosDetailFragment()
                    getNavController()?.navigate(action)
                }
            })
        }

    }

    private fun collectState() {
        _binding?.let { binding ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.state.collectLatest { state ->
                    when (state.photos) {
                        is NetworkResponse.Failure -> {

                        }

                        is NetworkResponse.Idle -> {
                        }

                        is NetworkResponse.Loading -> {
                            binding.progressLayout.makeVisible()
                            binding.recycler.makeGone()
                        }

                        is NetworkResponse.Success -> {
                            binding.progressLayout.makeGone()
                            binding.recycler.makeVisible()
                            photosAdapter.submitList(state.photos.data)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (canRefresh) {
            viewModel.onEvent(PhotosScreenEvents.Refresh)
            canRefresh = false
        }
    }

    companion object {
        fun getInstant() = PhotosFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
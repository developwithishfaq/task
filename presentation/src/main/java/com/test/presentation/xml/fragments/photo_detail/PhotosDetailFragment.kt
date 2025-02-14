package com.test.presentation.xml.fragments.photo_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.test.presentation.databinding.FragmentPhotosDetailBinding
import com.test.presentation.xml.core.adapaters.PhotosAdapter
import com.test.presentation.xml.core.interfaces.AdapterListener
import com.test.presentation.xml.fragments.BaseFragment
import com.test.presentation.xml.fragments.photos.PhotosFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotosDetailFragment : BaseFragment() {

    private var _binding: FragmentPhotosDetailBinding? = null

    private val viewModel: PhotosFragmentViewModel by activityViewModels()

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var photosAdapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotosDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        collectState()
        initClickListeners()

        setBackListener {
            getNavController()?.navigateUp()
        }

    }

    private fun initClickListeners() {
        _binding?.let { binding ->

        }
    }

    private fun initAdapter() {
        _binding?.let { binding ->
            photosAdapter.attach(object : AdapterListener {
                override fun onFavtClick(position: Int) {
                }

                override fun onClick(position: Int) {
                }
            })
        }

    }

    private fun collectState() {
        _binding?.let { binding ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.state.collectLatest { state ->
                    state.selectedImage?.let { model ->
                        requestManager.load(model.download_url)
                            .centerCrop()
                            .into(binding.fullImage)
                        binding.idTv.text = "Id : ${model.id}"
                        binding.authorTv.text = "Author : ${model.author}"
                    }
                }
            }
        }
    }

    companion object {
        fun getInstant() = PhotosDetailFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
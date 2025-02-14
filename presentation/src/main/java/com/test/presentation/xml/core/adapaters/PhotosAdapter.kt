package com.test.presentation.xml.core.adapaters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.test.domain.model.ImageModelUi
import com.test.presentation.databinding.PhotosGridItemBinding
import com.test.presentation.xml.core.interfaces.AdapterListener
import javax.inject.Inject

val diff = object : DiffUtil.ItemCallback<ImageModelUi>() {
    override fun areItemsTheSame(oldItem: ImageModelUi, newItem: ImageModelUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageModelUi, newItem: ImageModelUi): Boolean {
        return oldItem.download_url == newItem.download_url && oldItem.isFav == newItem.isFav
    }
}


class PhotosAdapter @Inject constructor(
    private val requestManager: RequestManager
) : ListAdapter<ImageModelUi, PhotosAdapter.PhotoViewHolder>(diff) {

    private var listener: AdapterListener? = null

    fun attach(listener: AdapterListener) {
        this.listener = listener
    }

    inner class PhotoViewHolder(
        private val binding: PhotosGridItemBinding
    ) : ViewHolder(binding.root) {
        init {
            binding.favBtn.setOnClickListener {
                val position = adapterPosition
                if (position != -1) {
                    listener?.onFavtClick(position)
                }
            }
            binding.imageView.setOnClickListener {
                val position = adapterPosition
                if (position != -1) {
                    listener?.onClick(position)
                }
            }
        }

        fun bindViews(item: ImageModelUi) {
            if (item.isFav) {
                binding.favBtn.setImageResource(com.test.framework.R.drawable.ic_fav_filled)
            } else {
                binding.favBtn.setImageResource(com.test.framework.R.drawable.ic_favourite)
            }
            requestManager.load(item.download_url).centerCrop().into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotosGridItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }
}
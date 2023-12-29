package com.example.bostatechnocalassignment.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bostatechnocalassignment.databinding.PhotosItemsBinding
import com.example.bostatechnocalassignment.model.photos.PhotoItem
import com.example.bostatechnocalassignment.utils.DiffUtilCallBack
import com.squareup.picasso.Picasso

class PhotosAdapter(private val onPhotoClickListeners: OnPhotoClickListeners) :
    RecyclerView.Adapter<PhotosAdapter.MyViewHolder>() {
    private var photoItemList = emptyList<PhotoItem>()

    inner class MyViewHolder(val binding: PhotosItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            PhotosItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(photoItemList[position]) {
                Picasso.get().load(thumbnailUrl).into(binding.albumPhoto)
                binding.root.setOnClickListener {
                    thumbnailUrl?.let { it1 -> onPhotoClickListeners.onPhotoItemClick(it1) }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return photoItemList.size
    }

    fun updateData(newPhotosList: List<PhotoItem>) {
        if (newPhotosList.isNullOrEmpty()) {
            onPhotoClickListeners.onEmptyListPassed()
        }
        val leadsListDiffUtil = DiffUtilCallBack(photoItemList, newPhotosList)
        val diffUtilResult = DiffUtil.calculateDiff(leadsListDiffUtil)
        photoItemList = newPhotosList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
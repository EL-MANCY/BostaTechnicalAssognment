package com.example.bostatechnocalassignment.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bostatechnocalassignment.databinding.AlbumsItemsBinding
import com.example.bostatechnocalassignment.model.albums.AlbumItem
import com.example.bostatechnocalassignment.utils.DiffUtilCallBack

class AlbumsAdapter(private val onAlbumsClickListener: OnAlbumsClickListener) :
    RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>() {

    private var albumsList = emptyList<AlbumItem>()

    inner class MyViewHolder(val binding: AlbumsItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AlbumsItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val albumItem = albumsList[position]
        with(holder.binding) {
            album.text = albumItem.title.toString()
            root.setOnClickListener {
                albumItem.id?.let { id ->
                    onAlbumsClickListener.onAlbumItemClick(albumId = id)
                }
            }
        }
    }

    override fun getItemCount(): Int = albumsList.size

    fun updateAlbum(newAlbumsList: List<AlbumItem>) {
        val diffUtilCallback = DiffUtilCallBack(albumsList, newAlbumsList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        albumsList = newAlbumsList
        diffResult.dispatchUpdatesTo(this)
    }
}

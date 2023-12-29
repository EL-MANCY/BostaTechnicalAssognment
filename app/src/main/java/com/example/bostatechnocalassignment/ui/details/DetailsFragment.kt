package com.example.bostatechnocalassignment.ui.details

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bostatechnocalassignment.databinding.FragmentDetailsBinding
import com.example.bostatechnocalassignment.model.photos.PhotoItem
import com.example.bostatechnocalassignment.network.httpStatus.ApiStatus
import com.example.bostatechnocalassignment.utils.hasInternetConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), OnPhotoClickListeners {

    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val photosAdapter by lazy { PhotosAdapter(this) }
    private val args by navArgs<DetailsFragmentArgs>()
    private var photosList = emptyList<PhotoItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailsBinding.inflate(inflater, container, false).apply {
        binding = this
        setupViews()
    }.root

    private fun setupViews() = with(binding) {
        rvAlbums.adapter = photosAdapter
        rvAlbums.layoutManager = GridLayoutManager(requireContext(), 3)
        isConnected()
        setListeners()
    }

    private fun setListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handleSearch(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                lifecycleScope.launchWhenResumed {
                    handleSearch(query)
                }
                return true
            }
        })
    }

    private fun handleSearch(query: String?) {
        val filteredList = photosList.filter {
            it.title?.contains(query.orEmpty(), ignoreCase = true) == true
        }
        photosAdapter.updateData(filteredList)
    }

    private fun isConnected() {
        if (hasInternetConnection(requireActivity().application)) {
            showConnectedViews()
            getAlbumPhotos()
        } else {
            showDisconnectedViews()
            Toast.makeText(requireContext(), "No Internet Connection!!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showConnectedViews() = with(binding) {
        rvAlbums.visibility = View.VISIBLE
    }

    private fun showDisconnectedViews() = with(binding) {
        rvAlbums.visibility = View.GONE
    }

    private fun getAlbumPhotos() {
        detailsViewModel.getAllPhotos(args.albumsId).observe(viewLifecycleOwner) { result ->
            when (result.status) {
                ApiStatus.SUCCESS -> handleSuccessResult(result.data)
                ApiStatus.ERROR -> handleErrorResult(result.message.toString())
                ApiStatus.LOADING -> {}
            }
        }
    }

    private fun handleSuccessResult(data: List<PhotoItem>?) {
        if (!data.isNullOrEmpty()) {
            photosList = data
            photosAdapter.updateData(data)
        }
    }

    private fun handleErrorResult(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onPhotoItemClick(imageUrl: String) {
        ImageViewerDialog(imageUrl).show(childFragmentManager, "Photo")
    }

    override fun onEmptyListPassed() {
        Toast.makeText(requireContext(), "No Matching Data!!!", Toast.LENGTH_SHORT).show()
    }
}

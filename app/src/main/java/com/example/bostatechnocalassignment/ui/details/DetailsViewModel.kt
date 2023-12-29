package com.example.bostatechnocalassignment.ui.details

import android.app.Application
import com.example.bostatechnocalassignment.baseViewModel.BaseViewModel
import com.example.bostatechnocalassignment.network.repository.PhotosRepo
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject


@HiltViewModel
class DetailsViewModel@Inject constructor(
    private val  photosRepo: PhotosRepo,
    application: Application
): BaseViewModel(application) {

    fun getAllPhotos(albumId:Int)=handleFlowResponse {
        photosRepo.getAlbumPhotos(albumId)
    }
}
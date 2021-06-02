package id.ac.scorn.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.scorn.domain.Photo
import id.ac.scorn.domain.PhotoUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val photoUseCase: PhotoUseCase) : ViewModel() {
    fun getAllPhotos(): LiveData<List<Photo>> = photoUseCase.getAllPhotos().asLiveData()

    fun insertPhoto(photo: Photo) = photoUseCase.insertPhoto(photo)
}
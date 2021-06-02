package id.ac.scorn.domain

import kotlinx.coroutines.flow.Flow

interface PhotoUseCase {
    fun getAllPhotos(): Flow<List<Photo>>
    fun insertPhoto(photo: Photo)
}
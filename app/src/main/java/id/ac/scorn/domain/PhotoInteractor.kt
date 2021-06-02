package id.ac.scorn.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoInteractor @Inject constructor(private val iRepository: IRepository) : PhotoUseCase {
    override fun getAllPhotos(): Flow<List<Photo>> {
        return iRepository.getAllPhotos()
    }

    override fun insertPhoto(photo: Photo) {
        return iRepository.insertPhoto(photo)
    }

}
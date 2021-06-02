package id.ac.scorn.domain

import id.ac.mymoviecatalogue.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllPhotos(): Flow<List<Photo>>

    fun insertPhoto(photo: Photo)
}


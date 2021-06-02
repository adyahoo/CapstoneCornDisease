package id.ac.scorn.data.local

import id.ac.scorn.data.local.db.PhotoDao
import id.ac.scorn.data.local.entity.PhotoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val photoDao: PhotoDao) {
    fun getAllPhotos() = photoDao.getAllPhotos()

    fun insertPhoto(photoEntity: PhotoEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            photoDao.insertPhoto(photoEntity)
        }
    }
}
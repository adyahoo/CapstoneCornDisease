package id.ac.scorn.data

import id.ac.scorn.data.local.LocalDataSource
import id.ac.scorn.domain.IRepository
import id.ac.scorn.domain.Photo
import id.ac.scorn.utils.AppExecutors
import id.ac.scorn.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IRepository {
    override fun getAllPhotos(): Flow<List<Photo>> {
        return localDataSource.getAllPhotos().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun insertPhoto(photo: Photo) {
        val photoEntity = DataMapper.mapDomainToEntity(photo)
        appExecutors.diskIO().execute { localDataSource.insertPhoto(photoEntity) }
    }

}
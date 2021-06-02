package id.ac.scorn.utils

import id.ac.mymoviecatalogue.core.vo.Resource
import id.ac.scorn.data.local.entity.PhotoEntity
import id.ac.scorn.domain.Photo

object DataMapper {
    fun mapEntitiesToDomain(input: List<PhotoEntity>): List<Photo> {
        val photoList = ArrayList<Photo>()
        input.map {
            val photo = Photo(
                it.photoId,
                it.photo
            )
            photoList.add(photo)
        }

        return photoList
    }

    fun mapDomainToEntity(input: Photo): PhotoEntity {
        return PhotoEntity(
            input.photoId,
            input.photo
        )
    }
}
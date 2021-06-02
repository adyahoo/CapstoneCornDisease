package id.ac.scorn.data.local.db

import androidx.room.*
import id.ac.scorn.data.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Query("SELECT * FROM tb_photo")
    fun getAllPhotos(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(photoEntity: PhotoEntity)
}
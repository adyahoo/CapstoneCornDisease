package id.ac.scorn.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_photo")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photoId")
    var photoId: Int?,

    @ColumnInfo(name = "photo")
    var photo: String
)

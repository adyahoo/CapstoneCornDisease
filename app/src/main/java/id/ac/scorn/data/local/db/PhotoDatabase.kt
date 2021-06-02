package id.ac.scorn.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.scorn.data.local.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}
package id.ac.scorn.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.ac.scorn.data.local.db.PhotoDao
import id.ac.scorn.data.local.db.PhotoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "db_photo"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providePhotoDao(database: PhotoDatabase): PhotoDao {
        return database.photoDao()
    }
}
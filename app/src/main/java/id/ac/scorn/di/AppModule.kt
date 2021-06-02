package id.ac.scorn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.scorn.domain.PhotoInteractor
import id.ac.scorn.domain.PhotoUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun providePhotoUseCase(photoInteractor: PhotoInteractor): PhotoUseCase
}
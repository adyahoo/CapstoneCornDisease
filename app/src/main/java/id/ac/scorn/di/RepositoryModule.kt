package id.ac.scorn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.scorn.data.Repository
import id.ac.scorn.domain.IRepository

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(photoRepository: Repository): IRepository
}
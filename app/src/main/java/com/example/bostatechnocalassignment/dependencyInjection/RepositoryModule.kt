package com.example.bostatechnocalassignment.dependencyInjection


import com.example.bostatechnocalassignment.network.repository.AlbumsRepo
import com.example.bostatechnocalassignment.network.repository.PhotosRepo
import com.example.bostatechnocalassignment.network.repository.UsersRepo
import com.example.bostatechnocalassignment.network.repositoryImpl.AlbumsRepoImpl
import com.example.bostatechnocalassignment.network.repositoryImpl.PhotosRepoImpl
import com.example.bostatechnocalassignment.network.repositoryImpl.UsersRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideUsersRepository(
        impl: UsersRepoImpl
    ): UsersRepo

    @Binds
    abstract fun providePhotosRepository(
        impl: PhotosRepoImpl
    ): PhotosRepo

    @Binds
    abstract fun provideAlbumsRepository(
        impl: AlbumsRepoImpl
    ): AlbumsRepo

}
package com.mackenzie.hogwarts.di

import androidx.lifecycle.SavedStateHandle
import com.mackenzie.hogwarts.ui.detail.DetailFragmentArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @HouseId
    fun provideHouseId(savedStateHandle: SavedStateHandle) =
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).houseId

}
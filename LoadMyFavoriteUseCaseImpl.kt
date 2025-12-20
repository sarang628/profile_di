package com.sarang.torang.di.profile_di

import com.sarang.torang.repository.feed.FeedLoadRepository
import com.sarang.torang.usecase.profile.LoadMyFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoadMyFavoriteUseCaseImpl {
    @Provides
    fun provideLoadMyFavoriteUseCase(
        feedLoadRepository: FeedLoadRepository
    ): LoadMyFavoriteUseCase {
        return object : LoadMyFavoriteUseCase {
            override suspend fun invoke() {
                feedLoadRepository.loadByFavorite()
            }
        }
    }
}
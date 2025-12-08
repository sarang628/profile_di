package com.sarang.torang.di.profile_di

import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.usecase.profile.LoadMyFavoriteUseCase
import com.sarang.torang.usecase.profile.LoadMyFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoadMyFavoriteUseCaseImpl {
    @Provides
    fun provideLoadMyFavoriteUseCase(
        feedRepository: FeedRepository
    ): LoadMyFavoriteUseCase {
        return object : LoadMyFavoriteUseCase {
            override suspend fun invoke() {
                feedRepository.loadByFavorite()
            }
        }
    }
}
package com.sarang.torang.di.profile_di

import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.usecase.profile.LoadMyFavoriteUseCase
import com.sarang.torang.usecase.profile.LoadMyFeedUseCase
import com.sarang.torang.usecase.profile.LoadMyLikeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoadMyLikeUseCaseImpl {
    @Provides
    fun provideLoadMyFavoriteUseCase(
        feedRepository: FeedRepository
    ): LoadMyLikeUseCase {
        return object : LoadMyLikeUseCase {
            override suspend fun invoke() {
                feedRepository.loadByLike()
            }
        }
    }
}
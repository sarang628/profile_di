package com.sarang.torang.di.profile_di

import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.usecase.profile.LoadMyFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoadMyFeedUseCaseImpl {
    @Provides
    fun provideLoadMyFeedUseCase(
        loggedInUserDao : LoggedInUserDao,
        feedRepository: FeedRepository
    ): LoadMyFeedUseCase {
        return object : LoadMyFeedUseCase {
            override suspend fun invoke() {
                loggedInUserDao.getLoggedInUser()?.let {
                    it.userId.let {
                        feedRepository.loadByUserId(it)
                    }
                }
            }
        }
    }
}
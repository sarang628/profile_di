package com.sarang.torang.di.profile_di

import com.sarang.torang.api.ApiReview
import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.core.database.dao.MyFeedDao
import com.sarang.torang.repository.ProfileRepository
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
        myFeedDao : MyFeedDao,
        apiFeed : ApiReview,
        profileRepository: ProfileRepository
    ): LoadMyFeedUseCase {
        return object : LoadMyFeedUseCase {
            override suspend fun invoke() {
                loggedInUserDao.getLoggedInUser()?.let {
                    it.userId.let {
                        profileRepository.loadMyFeed(it)
                        /*val result = apiFeed.getMyReviewsByUserId(it)
                        myFeedDao.insertAll(result.map { it.toMyFeedEntity() })
                        Log.d("__GetFeedUseCaseImpl", "myFeedDao.insertAll : ${result.size} items")*/
                    }
                }
            }
        }
    }
}
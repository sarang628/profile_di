package com.sarang.torang.di.profile_di

import android.util.Log
import com.sarang.torang.api.ApiReview
import com.sarang.torang.api.feed.ApiFeed
import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.core.database.dao.MyFeedDao
import com.sarang.torang.di.repository.toMyFeedEntity
import com.sarang.torang.usecase.profile.LoadMyFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
class LoadMyFeedUseCaseImpl {
    @Provides
    fun provideLoadMyFeedUseCase(
        loggedInUserDao : LoggedInUserDao,
        myFeedDao : MyFeedDao,
        apiFeed : ApiReview
    ): LoadMyFeedUseCase {
        return object : LoadMyFeedUseCase {
            override suspend fun invoke() {
                loggedInUserDao.getLoggedInUser()?.let {
                    it.userId.let {
                        val result = apiFeed.getMyReviewsByUserId(it)
                        myFeedDao.insertAll(result.map { it.toMyFeedEntity() })
                        Log.d("__GetFeedUseCaseImpl", "myFeedDao.insertAll : ${result.size} items")
                    }
                }
            }
        }
    }
}
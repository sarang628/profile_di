package com.sarang.torang.di.profile_di

import android.util.Log
import com.sarang.torang.Feed
import com.sarang.torang.api.ApiReview
import com.sarang.torang.core.database.dao.MyFeedDao
import com.sarang.torang.usecase.profile.GetFeedByUserIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetFeedByUserIdUseCaseImpl {
    @Provides
    fun providesGetFeedUseCase(
        apiFeed: ApiReview,
    ): GetFeedByUserIdUseCase {
        return object : GetFeedByUserIdUseCase {
            override suspend fun invoke(userId: Int): List<Feed> {
                val result = apiFeed.getMyReviewsByUserId(userId)
                Log.d("__GetFeedUseCaseImpl", "myFeedDao.insertAll : ${result.size} items")
                return result.map { it.toFeed() }
            }
        }
    }
}
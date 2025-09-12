package com.sarang.torang.di.profile_di

import android.util.Log
import com.sarang.torang.Feed
import com.sarang.torang.api.ApiReview
import com.sarang.torang.core.database.dao.MyFeedDao
import com.sarang.torang.di.repository.toMyFeedEntity
import com.sarang.torang.usecase.profile.GetMyFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetFeedUseCaseImpl {
    @Provides
    fun providesGetFeedUseCase(
        apiFeed: ApiReview,
        myFeedDao: MyFeedDao
    ): GetMyFeedUseCase {
        return object : GetMyFeedUseCase {
            override suspend fun invoke(userId: Int): List<Feed> {
                val result = apiFeed.getMyReviewsByUserId(userId)
                myFeedDao.insertAll(result.map { it.toMyFeedEntity() })
                Log.d("__GetFeedUseCaseImpl", "myFeedDao.insertAll : ${result.size} items")
                return result.map { it.toFeed() }
            }
        }
    }
}
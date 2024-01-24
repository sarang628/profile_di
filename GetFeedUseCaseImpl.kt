package com.sarang.torang.di.profile_di

import com.sarang.torang.Feed
import com.sarang.torang.profile.GetFeedUseCase
import com.sryang.torang_repository.api.ApiReview
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetFeedUseCaseImpl {
    @Provides
    fun providesGetFeedUseCase(
        apiFeed: ApiReview
    ): GetFeedUseCase {
        return object : GetFeedUseCase {
            override suspend fun invoke(userId: Int): List<Feed> {
                return apiFeed.getMyReviewsByUserId(userId).map { it.toFeed() }
            }
        }
    }
}
package com.sryang.torang.di.profile_di

import com.sryang.myapplication.di.profile_di.toFeed
import com.sryang.torang.uistate.Feed
import com.sryang.torang.usecase.profile.GetFeedUseCase
import com.sryang.torang_repository.api.ApiFeed
import com.sryang.torang_repository.api.ApiReport
import com.sryang.torang_repository.api.ApiReview
import com.sryang.torang_repository.di.repository.repository.impl.toFeedEntity
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
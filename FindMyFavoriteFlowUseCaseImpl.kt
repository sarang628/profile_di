package com.sarang.torang.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.repository.feed.FeedFlowRepository
import com.sarang.torang.usecase.profile.FineMyFavoriteFlowUseCase
import com.sarang.torang.viewmodel.profile.MyFavoriteItemUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
class FindMyFavoriteFlowUseCaseImpl {
    @Provides
    fun providesFineMyFavoriteFlowUseCase(feedFlowRepository: FeedFlowRepository): FineMyFavoriteFlowUseCase {
        return object : FineMyFavoriteFlowUseCase {
            override fun invoke(): Flow<List<MyFavoriteItemUiState>> {
                return feedFlowRepository.findByFavoriteFlow().map {
                    it.map {
                        MyFavoriteItemUiState(
                            reviewId = it.reviewId ?: 0,
                            reviewImage = BuildConfig.REVIEW_IMAGE_SERVER_URL + (it.pictureUrl ?: "")
                        )
                    }
                }
            }
        }
    }
}
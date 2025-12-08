package com.sarang.torang.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.repository.FeedRepository
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
    fun providesFineMyFavoriteFlowUseCase(feedRepository: FeedRepository): FineMyFavoriteFlowUseCase {
        return object : FineMyFavoriteFlowUseCase {
            override fun invoke(): Flow<List<MyFavoriteItemUiState>> {
                return feedRepository.findByFavoriteFlow().map {
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
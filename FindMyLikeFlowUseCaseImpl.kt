package com.sarang.torang.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.usecase.profile.FineMyFavoriteFlowUseCase
import com.sarang.torang.usecase.profile.FineMyLikeFlowUseCase
import com.sarang.torang.viewmodel.profile.MyFavoriteItemUiState
import com.sarang.torang.viewmodel.profile.MyLikeListViewUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
class FindMyLikeFlowUseCaseImpl {
    @Provides
    fun providesFineMyLikeFlowUseCase(feedRepository: FeedRepository): FineMyLikeFlowUseCase {
        return object : FineMyLikeFlowUseCase {
            override fun invoke(): Flow<List<MyLikeListViewUiState>> {
                return feedRepository.findByLikeFlow().map {
                    it.map {
                        MyLikeListViewUiState(
                            reviewId = it.reviewId ?: 0,
                            reviewImage = BuildConfig.REVIEW_IMAGE_SERVER_URL + (it.pictureUrl ?: "")
                        )
                    }
                }
            }
        }
    }
}
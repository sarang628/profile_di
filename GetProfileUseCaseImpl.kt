package com.sarang.torang.di.profile_di

import com.sarang.torang.api.ApiProfile
import com.sarang.torang.usecase.profile.GetProfileUseCase
import com.sarang.torang.viewmodel.FollowUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetProfileUseCaseImpl {
    @Provides
    fun providesGetProfileUseCase(
        apiProfile: ApiProfile
    ): GetProfileUseCase {
        return object : GetProfileUseCase {
            override suspend fun invoke(userId: Int): FollowUiState {
                return apiProfile.getProfile(userId.toString()).toFollowUiState()
            }
        }
    }
}
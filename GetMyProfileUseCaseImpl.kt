package com.sarang.torang.di.profile_di

import com.sarang.torang.api.ApiProfile
import com.sarang.torang.session.SessionService
import com.sarang.torang.usecase.profile.GetMyProfileUseCase
import com.sarang.torang.viewmodel.FollowUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetMyProfileUseCaseImpl {
    @Provides
    fun providesGetMyProfileUseCase(
        apiProfile: ApiProfile,
        sessionService: SessionService
    ): GetMyProfileUseCase {
        return object : GetMyProfileUseCase {
            override suspend fun invoke(): FollowUiState {
                sessionService.getToken()?.let {
                    return apiProfile.getProfileByToken(it).toFollowUiState()
                }
                throw Exception("로그인을 해주세요.")
            }
        }
    }
}
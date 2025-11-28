package com.sarang.torang.di.profile_di

import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.usecase.profile.UnFollowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UnFollowUseCaseImpl {
    @Provides
    fun providesUnFollowUseCase(
        followRepository: FollowRepository
    ): UnFollowUseCase {
        return object : UnFollowUseCase {
            override suspend fun invoke(id: Int): Boolean {
                return followRepository.unFollow(id)
            }
        }
    }
}
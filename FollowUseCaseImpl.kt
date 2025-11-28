package com.sarang.torang.di.profile_di

import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.usecase.profile.FollowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FollowUseCaseImpl {
    @Provides
    fun providesFollowUseCase(
        followRepository: FollowRepository
    ): FollowUseCase {
        return object : FollowUseCase {
            override suspend fun invoke(id: Int): Boolean {
                return followRepository.follow(id)
            }
        }
    }
}
package com.sarang.torang.di.profile_di

import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.usecase.profile.GetMyFollowerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetMyFollowerUseCaseImpl {
    @Provides
    fun providesGetMyFollowerUseCase(
        followRepository: FollowRepository
    ): GetMyFollowerUseCase {
        return object : GetMyFollowerUseCase {
            override suspend fun invoke(): List<Follow> {
                return followRepository.getMyFollower().map {
                    it.toFollow()
                }.toList()
            }
        }
    }
}
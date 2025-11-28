package com.sarang.torang.di.profile_di

import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.usecase.profile.GetFollowerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetFollowerUseCaseImpl {
    @Provides
    fun providesGetFollowerUseCase(
        followRepository: FollowRepository
    ): GetFollowerUseCase {
        return object : GetFollowerUseCase {
            override suspend fun invoke(userId: Int): List<Follow> {
                return followRepository.getFollower(userId).map {
                    it.toFollow()
                }.toList()
            }
        }
    }
}
package com.sarang.torang.di.profile_di

import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.usecase.profile.GetFollowingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetFollowingUseCaseImpl {
    @Provides
    fun providesGetFollowingUseCase(
        followRepository: FollowRepository
    ): GetFollowingUseCase {
        return object : GetFollowingUseCase {
            override suspend fun invoke(userId: Int): List<Follow> {
                return followRepository.getFollowing(userId).map {
                    it.toFollow()
                }.toList()
            }
        }
    }

}
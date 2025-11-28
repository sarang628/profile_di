package com.sarang.torang.di.profile_di

import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.usecase.profile.GetMyFollowingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetMyFollowingUseCaseImpl {
    @Provides
    fun providesGetMyFollowingUseCase(
        followRepository: FollowRepository
    ): GetMyFollowingUseCase {
        return object : GetMyFollowingUseCase {
            override suspend fun invoke(): List<Follow> {
                return followRepository.getMyFollowing().map {
                    it.toFollow()
                }.toList()
            }
        }
    }

}
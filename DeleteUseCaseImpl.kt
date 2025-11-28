package com.sarang.torang.di.profile_di

import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.usecase.profile.DeleteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DeleteUseCaseImpl {
    @Provides
    fun providesDeleteUseCase(
        followRepository: FollowRepository
    ): DeleteUseCase {
        return object : DeleteUseCase {
            override suspend fun invoke(id: Int): Boolean {
                return followRepository.delete(id)
            }
        }
    }
}
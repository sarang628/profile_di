package com.sarang.torang.di.profile_di

import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.usecase.profile.DeleteUseCase
import com.sarang.torang.usecase.profile.FollowUseCase
import com.sarang.torang.usecase.profile.GetMyFollowerUseCase
import com.sarang.torang.usecase.profile.GetMyFollowingUseCase
import com.sarang.torang.usecase.profile.GetMyProfileUseCase
import com.sarang.torang.usecase.profile.UnFollowUseCase
import com.sarang.torang.viewmodel.FollowUiState
import com.sarang.torang.api.ApiProfile
import com.sarang.torang.repository.ChatRepository
import com.sarang.torang.usecase.profile.GetFollowerUseCase
import com.sarang.torang.usecase.profile.GetFollowingUseCase
import com.sarang.torang.usecase.profile.GetProfileUseCase
import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.session.SessionService
import com.sarang.torang.usecase.profile.FindOrCreateChatRoomByUserIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FollowServiceModule {
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

    @Provides
    fun provideFindOrCreateChatRoomByUserIdUseCase(
        chatRepository: ChatRepository
    ): FindOrCreateChatRoomByUserIdUseCase {
        return object : FindOrCreateChatRoomByUserIdUseCase {
            override suspend fun invoke(userId: Int): Int {
                val result = chatRepository.getUserOrCreateRoomByUserId(userId)
                return result.roomId
            }
        }
    }

}
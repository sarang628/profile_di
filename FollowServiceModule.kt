package com.sarang.torang.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.Feed
import com.sarang.torang.ProfileUiState
import com.sarang.torang.profile.DeleteUseCase
import com.sarang.torang.profile.FollowUseCase
import com.sarang.torang.profile.GetMyFollowerUseCase
import com.sarang.torang.profile.GetMyFollowingUseCase
import com.sarang.torang.profile.GetMyProfileUseCase
import com.sarang.torang.profile.IsLoginUseCase
import com.sarang.torang.profile.ProfileService
import com.sarang.torang.profile.UnFollowUseCase
import com.sarang.torang.viewmodel.FollowUiState
import com.sarang.torang.api.ApiProfile
import com.sarang.torang.data.dao.LoggedInUserDao
import com.sarang.torang.di.repository.repository.impl.ProfileRepositoryImpl
import com.sarang.torang.profile.GetFollowerUseCase
import com.sarang.torang.profile.GetFollowingUseCase
import com.sarang.torang.profile.GetProfileUseCase
import com.sarang.torang.repository.EditProfileRepository
import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.map

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

}
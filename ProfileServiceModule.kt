package com.sryang.myapplication.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.di.profile_di.toFeeds
import com.sarang.torang.di.profile_di.toFollow
import com.sarang.torang.di.profile_di.toFollowUiState
import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.Feed
import com.sarang.torang.ProfileUiState
import com.sarang.torang.profile.DeleteUseCase
import com.sarang.torang.profile.FollowUseCase
import com.sarang.torang.profile.GetFollowerUseCase
import com.sarang.torang.profile.GetFollowingUseCase
import com.sarang.torang.profile.GetProfileUseCase
import com.sarang.torang.profile.IsLoginUseCase
import com.sarang.torang.profile.ProfileService
import com.sarang.torang.profile.UnFollowUseCase
import com.sarang.torang.viewmodel.FollowUiState
import com.sryang.torang_repository.api.ApiProfile
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import com.sryang.torang_repository.di.repository.repository.impl.ProfileRepositoryImpl
import com.sryang.torang_repository.repository.EditProfileRepository
import com.sryang.torang_repository.repository.FollowRepository
import com.sryang.torang_repository.session.SessionService
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
class ProfileServiceModule {
    @Provides
    fun provideProfileService(
        profileRepository: ProfileRepositoryImpl,
        editProfileRepository: EditProfileRepository
    ): ProfileService {
        return object : ProfileService {
            override suspend fun loadProfile(id: Int): ProfileUiState {
                val result = profileRepository.loadProfile(id)
                return ProfileUiState(
                    id = result.userId,
                    profileUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL + result.profilePicUrl,
                    feedCount = result.post,
                    following = result.following,
                    follower = result.follower,
                    name = result.userName,
                    isLogin = true,
                    isFollow = result.follow == 1
                )
            }

            override suspend fun loadProfileByToken(): ProfileUiState {
                val result = profileRepository.loadProfileByToken()
                return ProfileUiState(
                    profileUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL + result.profilePicUrl,
                    feedCount = result.post,
                    following = result.following,
                    follower = result.follower,
                    name = result.userName,
                    isLogin = true,
                    id = result.userId
                )
            }

            override suspend fun getFavorites(): kotlinx.coroutines.flow.Flow<List<Feed>> {
                return MutableStateFlow<List<Feed>>(ArrayList()).combineTransform(
                    profileRepository.getMyFavorite(
                        1
                    )
                ) { feed, feedEntity ->
                    emit(feedEntity.toFeeds())
                }
            }

            override suspend fun updateProfile(uri: String) {
                editProfileRepository.editProfile(name = null, uri = uri)
            }
        }
    }

    @Provides
    fun provideIsLoginUseCase(
        loggedInUserDao: LoggedInUserDao
    ): IsLoginUseCase {
        return object : IsLoginUseCase {
            override val isLogin: Flow<Boolean>
                get() = loggedInUserDao.getLoggedInUser().map { it != null }

        }
    }

    @Provides
    fun ProvidesGetFollowerUseCase(
        followRepository: FollowRepository
    ): GetFollowerUseCase {
        return object : GetFollowerUseCase {
            override suspend fun invoke(): List<Follow> {
                return followRepository.getFollower().map {
                    it.toFollow()
                }.toList()
            }
        }
    }

    @Provides
    fun ProvidesGetFollowingUseCase(
        followRepository: FollowRepository
    ): GetFollowingUseCase {
        return object : GetFollowingUseCase {
            override suspend fun invoke(): List<Follow> {
                return followRepository.getFollowing().map {
                    it.toFollow()
                }.toList()
            }
        }
    }

    @Provides
    fun ProvidesGetProfileUseCase(
        apiProfile: ApiProfile,
        sessionService: SessionService
    ): GetProfileUseCase {
        return object : GetProfileUseCase {
            override suspend fun invoke(): FollowUiState {
                sessionService.getToken()?.let {
                    return apiProfile.getProfileByToken(it).toFollowUiState()
                }
                throw Exception("로그인을 해주세요.")
            }
        }
    }

    @Provides
    fun ProvidesDeleteUseCase(
        followRepository: FollowRepository
    ): DeleteUseCase {
        return object : DeleteUseCase {
            override suspend fun invoke(id: Int): Boolean {
                return followRepository.delete(id)
            }
        }
    }

    @Provides
    fun ProvidesUnFollowUseCase(
        followRepository: FollowRepository
    ): UnFollowUseCase {
        return object : UnFollowUseCase {
            override suspend fun invoke(id: Int): Boolean {
                return followRepository.unFollow(id)
            }
        }
    }

    @Provides
    fun ProvidesFollowUseCase(
        followRepository: FollowRepository
    ): FollowUseCase {
        return object : FollowUseCase {
            override suspend fun invoke(id: Int): Boolean {
                return followRepository.follow(id)
            }
        }
    }
}
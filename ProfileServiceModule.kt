package com.sryang.myapplication.di.profile_di

import com.sarang.profile.compose.follow.Follow
import com.sarang.profile.uistate.Feed
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.usecase.DeleteUseCase
import com.sarang.profile.usecase.GetFollowerUseCase
import com.sarang.profile.usecase.GetFollowingUseCase
import com.sarang.profile.usecase.GetProfileUseCase
import com.sarang.profile.usecase.IsLoginUseCase
import com.sarang.profile.usecase.ProfileService
import com.sarang.profile.usecase.UnFollowUseCase
import com.sarang.profile.viewmodel.FollowUiState
import com.sryang.torang_repository.api.ApiProfile
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import com.sryang.torang_repository.data.remote.response.RemoteFollower
import com.sryang.torang_repository.data.remote.response.RemoteUser
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
                    profileUrl = result.profilePicUrl,
                    feedCount = result.reviewCount,
                    following = result.following,
                    follower = result.followers,
                    name = result.userName,
                    isLogin = true
                )
            }

            override suspend fun loadProfileByToken(): ProfileUiState {
                val result = profileRepository.loadProfileByToken()
                return ProfileUiState(
                    profileUrl = result.profilePicUrl,
                    feedCount = result.reviewCount,
                    following = result.following,
                    follower = result.followers,
                    name = result.userName,
                    isLogin = true
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
            override fun invoke(id: Int): Boolean {
                return false
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

    fun RemoteUser.toFollowUiState(): FollowUiState {
        return FollowUiState(
            name = this.userName,
            following = this.following,
            follower = this.followers,
            subscription = 0
        )
    }
}
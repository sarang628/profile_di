package com.sarang.torang.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.compose.profile.ProfileUiState
import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.data.profile.Feed
import com.sarang.torang.repository.EditProfileRepository
import com.sarang.torang.repository.UserRepository
import com.sarang.torang.repository.feed.FeedRepository
import com.sarang.torang.usecase.profile.IsLoginUseCase
import com.sarang.torang.usecase.profile.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
class ProfileServiceModule {
    @Provides
    fun provideProfileService(
        editProfileRepository: EditProfileRepository,
        userRepository: UserRepository,
        feedRepository: FeedRepository
    ): ProfileService {
        return object : ProfileService {
            override suspend fun loadProfile(id: Int): ProfileUiState {
                val result = userRepository.findById(id)
                return ProfileUiState.Success(
                    id = result.userId,
                    profileUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL + result.profilePicUrl,
                    feedCount = result.post.toString(),
                    following = result.following.toString(),
                    follower = result.follower.toString(),
                    name = result.userName,
                    isFollow = result.follow == 1
                )
            }

            override suspend fun loadProfileByToken(): ProfileUiState {
                val result = userRepository.findByToken()
                return ProfileUiState.Success(
                    profileUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL + result.profilePicUrl,
                    feedCount = result.post.toString(),
                    following = result.following.toString(),
                    follower = result.follower.toString(),
                    name = result.userName,
                    id = result.userId
                )
            }

            override suspend fun getFavorites(): Flow<List<Feed>> {
                return MutableStateFlow(listOf())
                /*return feedRepository.findByFavoriteFlow().map {
                    it.toFeeds()
                }*/
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
                get() = loggedInUserDao.getLoggedInUserFlow().map { it != null }

        }
    }
}
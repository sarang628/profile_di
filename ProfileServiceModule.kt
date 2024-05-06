package com.sarang.torang.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.Feed
import com.sarang.torang.ProfileUiState
import com.sarang.torang.usecase.IsLoginUseCase
import com.sarang.torang.usecase.ProfileService
import com.sarang.torang.data.dao.LoggedInUserDao
import com.sarang.torang.di.repository.repository.impl.ProfileRepositoryImpl
import com.sarang.torang.repository.EditProfileRepository
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
                return ProfileUiState.Success(
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
                return ProfileUiState.Success(
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
}
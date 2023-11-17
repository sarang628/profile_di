package com.sryang.myapplication.di.profile_di

import com.sarang.profile.uistate.Feed
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.usecase.IsLoginUseCase
import com.sarang.profile.usecase.ProfileService
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import com.sryang.torang_repository.di.repository.repository.impl.ProfileRepositoryImpl
import com.sryang.torang_repository.repository.EditProfileRepository
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
}
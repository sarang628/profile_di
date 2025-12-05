package com.sarang.torang.di.profile_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.compose.profile.MyProfileUiState
import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.usecase.profile.GetMyProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
class GetMyProfileUseCaseImpl {
    @Provides
    fun providesGetMyProfileUseCase(loggedInUserDao : LoggedInUserDao,
                                    userDao         : UserDao): GetMyProfileUseCase {
        return object : GetMyProfileUseCase {
            override fun invoke(): Flow<MyProfileUiState> {
                return loggedInUserDao.getLoggedInUserFlow().map { loggedInUserEntity ->
                    loggedInUserEntity?.userId?.let {
                        userDao.findById(it)?.let { user -> MyProfileUiState.Success(name           = user.userName,
                                                                                             following      = user.following,
                                                                                             follower       = user.followers,
                                                                                             feedCount      = user.reviewCount,
                                                                                             profileUrl     = BuildConfig.PROFILE_IMAGE_SERVER_URL + user.profilePicUrl)
                                                          } ?: run {
                                                                      MyProfileUiState.NeedLogin
                                                                   }
                                                    } ?: run {
                                                        MyProfileUiState.NeedLogin
                                                    }
                }
            }
        }
    }
}
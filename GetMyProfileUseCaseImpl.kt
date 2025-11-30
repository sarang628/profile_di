package com.sarang.torang.di.profile_di

import com.sarang.torang.api.ApiProfile
import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.session.SessionService
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.profile.GetMyProfileUseCase
import com.sarang.torang.viewmodel.FollowUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
class GetMyProfileUseCaseImpl {
    @Provides
    fun providesGetMyProfileUseCase(loggedInUserDao : LoggedInUserDao,
                                    userDao         : UserDao): GetMyProfileUseCase {
        return object : GetMyProfileUseCase {
            override fun invoke(): Flow<FollowUiState> {
                return loggedInUserDao.getLoggedInUserFlow().map {
                    it?.userId?.let {
                        userDao.findById(it)?.let {
                            FollowUiState( name = it.userName,
                                           following = it.following,
                                           follower = it.followers,
                                           subscription = it.reviewCount)
                        } ?: run {
                            FollowUiState()
                        }
                    } ?: run {
                        FollowUiState()
                    }
                }
            }
        }
    }
}
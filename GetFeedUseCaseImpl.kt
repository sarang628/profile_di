package com.sarang.torang.di.profile_di

import com.sarang.torang.data.profile.Feed
import com.sarang.torang.core.database.dao.MyFeedDao
import com.sarang.torang.usecase.profile.GetMyFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
class GetFeedUseCaseImpl {
    @Provides
    fun providesGetFeedUseCase(myFeedDao: MyFeedDao): GetMyFeedUseCase {
        return object : GetMyFeedUseCase {
            override fun invoke(): Flow<List<Feed>> {
                return myFeedDao.findAllFlow().map {
                    val result = it.toFeeds()
                    result
                }
            }
        }
    }
}
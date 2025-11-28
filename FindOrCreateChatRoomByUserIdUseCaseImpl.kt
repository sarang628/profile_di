package com.sarang.torang.di.profile_di

import com.sarang.torang.repository.ChatRepository
import com.sarang.torang.usecase.profile.FindOrCreateChatRoomByUserIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FindOrCreateChatRoomByUserIdUseCaseImpl {
    @Provides
    fun provideFindOrCreateChatRoomByUserIdUseCase(
        chatRepository: ChatRepository
    ): FindOrCreateChatRoomByUserIdUseCase {
        return object : FindOrCreateChatRoomByUserIdUseCase {
            override suspend fun invoke(userId: Int): Int {
                val result = chatRepository.createChatRoomByUserId(userId)
                return result.roomId
            }
        }
    }
}
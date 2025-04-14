package com.sarang.torang.di.profile_di

import androidx.compose.runtime.Composable
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.di.image.provideTorangAsyncImage

internal fun provideEditProfileScreen(rootNavController: RootNavController): @Composable () -> Unit =
    {
        EditProfileScreen(
            onEditImage = { rootNavController.editProfileImage() },
            onBack = {
                rootNavController.popBackStack()
            },
            image = provideTorangAsyncImage()
        )
    }
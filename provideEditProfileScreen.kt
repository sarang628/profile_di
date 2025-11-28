package com.sarang.torang.di.profile_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.LocalProfileImage
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.di.image.provideTorangAsyncImage

internal fun provideEditProfileScreen(rootNavController: RootNavController): @Composable () -> Unit =
    {
        CompositionLocalProvider(LocalProfileImage provides {provideTorangAsyncImage().invoke(
            it.modifier,
            it.url,
            it.errorIconSize,
            it.progressSize,
            it.contentScale)}) {
        EditProfileScreen(
            onEditImage = { rootNavController.editProfileImage() },
            onBack = {
                rootNavController.popBackStack()
            }
        )
    }}
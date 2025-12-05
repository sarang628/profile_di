package com.sarang.torang.di.profile_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.profile.LocalProfileImage
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.viewmodel.profile.MyProfileViewModel

internal fun provideEditProfileScreen(rootNavController: RootNavController): @Composable () -> Unit =
    {
        CompositionLocalProvider(LocalProfileImage provides {provideTorangAsyncImage().invoke(
            it.modifier,
            it.url,
            it.errorIconSize,
            it.progressSize,
            it.contentScale)}) {
            val profileViewModel = hiltViewModel<MyProfileViewModel>()
            val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()
        EditProfileScreen(
            uiState = uiState,
            onEditImage = { rootNavController.editProfileImage() },
            onBack = {
                rootNavController.popBackStack()
            }
        )
    }}
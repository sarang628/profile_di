package com.sryang.myapplication.di.profile_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.sarang.instagralleryModule.GalleryNavHost
import com.sryang.base.feed.compose.feed.Feeds
import com.sryang.torang.compose.FeedListScreen
import com.sryang.torang.compose.edit.ProfileNavHost
import com.sryang.torang.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    profileImageUrl: String,
    imageServerUrl: String,
    onSetting: () -> Unit,
    navBackStackEntry: NavBackStackEntry?
) {
    val uiState by profileViewModel.uiState.collectAsState()

    ProfileNavHost(
        profileViewModel = profileViewModel,
        onSetting = onSetting,
        favorite = {

            FeedListScreen(
                userId = uiState.id
            )

        },
        wantToGo = {
            FeedListScreen(
                userId = uiState.id
            )
        },
        galleryScreen = { onNext, onClose ->
            GalleryNavHost(onNext = onNext, onClose = { onClose.invoke() })
        },
        isMyProfile = navBackStackEntry == null,
        id = navBackStackEntry?.arguments?.getString("id")?.toInt(),
        profileImageServerUrl = profileImageUrl
    )
}
package com.sryang.myapplication.di.profile_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.profile.compose.edit.ProfileNavHost
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.base.feed.compose.feed.Feeds
import com.sryang.base.feed.uistate.FeedUiState


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    profileImageUrl: String,
    imageServerUrl: String,
    onSetting: () -> Unit,
    navBackStackEntry: NavBackStackEntry?
)
{
    val uiState by profileViewModel.uiState.collectAsState()

    ProfileNavHost(
        profileViewModel = profileViewModel,
        onSetting = onSetting,
        favorite = {
            Feeds(list = ArrayList(),
                onProfile = {},
                onLike = {},
                onComment = {},
                onShare = {},
                onFavorite = {},
                onMenu = { /*TODO*/ },
                onName = { /*TODO*/ },
                onRestaurant = { /*TODO*/ },
                onImage = {},
                onRefresh = { /*TODO*/ },
                isRefreshing = false,
                profileImageServerUrl = profileImageUrl,
                imageServerUrl = imageServerUrl,
                ratingBar = {},
                isLoaded = false,
                isVisibleList = false,
                isEmpty = false,
                onBottom = {})
        },
        wantToGo = {
            Feeds(list = ArrayList(),
                onProfile = {},
                onLike = {},
                onComment = {},
                onShare = {},
                onFavorite = {},
                onMenu = { /*TODO*/ },
                onName = { /*TODO*/ },
                onRestaurant = { /*TODO*/ },
                onImage = {},
                onRefresh = { /*TODO*/ },
                isRefreshing = false,
                ratingBar = {},
                imageServerUrl = imageServerUrl,
                profileImageServerUrl = profileImageUrl,
                isEmpty = false,
                isVisibleList = false,
                isLoaded = false,
                onBottom = {})
        },
        galleryScreen = { onNext, onClose ->
            GalleryNavHost(onNext = onNext, onClose = { onClose.invoke() })
        },
        isMyProfile = navBackStackEntry == null,
        id = navBackStackEntry?.arguments?.getString("id")?.toInt(),
        profileImageServerUrl = profileImageUrl
    )
}
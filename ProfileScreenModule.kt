package com.sarang.torang.di.profile_di

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.compose.profile._MyProfileScreen
import com.sarang.torang.viewmodel.MyProfileViewModel


@Composable
fun MyProfileScreen(
    profileViewModel: MyProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onProfile: ((Int) -> Unit)? = null,
    myFeed: @Composable (NavBackStackEntry) -> Unit
) {
    _MyProfileScreen(
        myProfileViewModel = profileViewModel,
        onSetting = onSetting,
        galleryScreen = { onNext, onClose ->
            GalleryNavHost(onNext = onNext, onClose = { onClose.invoke() })
        },
        onClose = onClose,
        onEmailLogin = onEmailLogin,
        onProfile = onProfile,
        myFeed = myFeed
    )
}
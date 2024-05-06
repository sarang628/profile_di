package com.sarang.torang.di.profile_di

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.compose._MyProfileScreenNavHost
import com.sarang.torang.viewmodel.MyProfileViewModel


@Composable
fun MyProfileScreenNavHost(
    profileViewModel: MyProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
    onReview: ((Int) -> Unit)? = null,
) {
    _MyProfileScreenNavHost(
        myProfileViewModel = profileViewModel,
        onSetting = onSetting,
        galleryScreen = { onNext, onClose ->
            GalleryNavHost(onNext = onNext, onClose = { onClose.invoke() })
        },
        onClose = onClose,
        onEmailLogin = onEmailLogin,
        myFeed = myFeed,
        onReview = onReview
    )
}
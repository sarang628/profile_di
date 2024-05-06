package com.sarang.torang.di.profile_di

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.compose._MyProfileScreenNavHost
import com.sarang.torang.viewmodel.MyProfileViewModel
import com.sarang.torang.viewmodel.ProfileViewModel


@Composable
fun MyProfileScreenNavHost(
    navController: NavHostController,
    onSetting: () -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
    onReview: ((Int) -> Unit)? = null
) {
    _MyProfileScreenNavHost(
        navController = navController,
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
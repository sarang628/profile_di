package com.sarang.torang.di.profile_di

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.sarang.instagralleryModule.compose.GalleryNavHost
import com.sarang.torang.compose._MyProfileScreenNavHost
import com.sarang.torang.di.image.provideTorangAsyncImage


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyProfileScreenNavHost(
    navController: NavHostController,
    onSetting: () -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
    onReview: ((Int) -> Unit)? = null,
    onMessage: (Int) -> Unit,
) {
    _MyProfileScreenNavHost(
        navController = navController,
        onSetting = onSetting,
        galleryScreen = { onNext, onClose ->
            GalleryNavHost(
                onNext = onNext,
                onClose = { onClose.invoke() },
                onBack = { onClose.invoke() })
        },
        onClose = onClose,
        onEmailLogin = onEmailLogin,
        myFeed = myFeed,
        onReview = onReview,
        image = provideTorangAsyncImage(),
        onMessage = onMessage
    )
}
package com.sarang.torang.di.profile_di

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.profile.LocalProfileImage
import com.sarang.torang.compose.profile._MyProfileScreenNavHost
import com.sarang.torang.di.image.provideTorangAsyncImage


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyProfileScreenNavHost(
    navController               : NavHostController = rememberNavController(),
    onSetting                   : () -> Unit        = {},
    onClose                     : () -> Unit        = {},
    onEmailLogin                : () -> Unit        = {},
    onReview                    : (Int) -> Unit     = {},
    onMessage                   : (Int) -> Unit     = {},
    myProfileBackgroundColor    : Color             = Color.Transparent
) {
    CompositionLocalProvider(LocalProfileImage provides {provideTorangAsyncImage().invoke(
        it.modifier,
        it.url,
        it.errorIconSize,
        it.progressSize,
        it.contentScale)}) {
        _MyProfileScreenNavHost(
            navController = navController,
            onSetting = onSetting,
            galleryScreen = { onNext, onClose ->
                /*GalleryNavHost(
                    onNext = onNext,
                    onClose = { onClose.invoke() },
                    onBack = { onClose.invoke() })*/
            },
            onClose = onClose,
            onEmailLogin = onEmailLogin,
            onReview = onReview,
            onMessage = onMessage,
            myProfileBackgroundColor = myProfileBackgroundColor
        )
    }
}
package com.sarang.torang.di.profile_di

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.profile.LocalProfileImage
import com.sarang.torang.compose.profile._MyProfileScreenNavHost
import com.sarang.torang.di.image.TorangAsyncImageData
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.viewmodel.profile.MyFeedListViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyProfileScreenNavHost(
    navController               : NavHostController     = rememberNavController(),
    feedListViewModel           : MyFeedListViewModel   = hiltViewModel(),
    onSetting                   : () -> Unit            = {},
    onClose                     : () -> Unit            = {},
    onEmailLogin                : () -> Unit            = {},
    onReview                    : (Int) -> Unit         = {},
    onMessage                   : (Int) -> Unit         = {},
    myProfileBackgroundColor    : Color                 = Color.Transparent,
    contentWindowInsets         : WindowInsets          = ScaffoldDefaults.contentWindowInsets
) {
    CompositionLocalProvider(LocalProfileImage provides {provideTorangAsyncImage().invoke(
        TorangAsyncImageData(
            it.modifier,
            it.url,
            it.errorIconSize,
            it.progressSize,
            it.contentScale
        )
    )}) {
        _MyProfileScreenNavHost(
            navController               = navController,
            feedListViewModel           = feedListViewModel,
            onSetting                   = onSetting,
            galleryScreen               = { onNext, onClose ->
                                            /*GalleryNavHost(
                                                onNext = onNext,
                                                onClose = { onClose.invoke() },
                                                onBack = { onClose.invoke() })*/
                                           },
            onClose                     = onClose,
            onEmailLogin                = onEmailLogin,
            onReview                    = onReview,
            onMessage                   = onMessage,
            myProfileBackgroundColor    = myProfileBackgroundColor,
            contentWindowInsets         = contentWindowInsets
        )
    }
}
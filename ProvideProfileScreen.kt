package com.sarang.torang.di.profile_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.LocalProfileImage
import com.sarang.torang.compose.ProfileScreenNavHost
import com.sarang.torang.di.image.provideTorangAsyncImage

@Composable
fun ProvideProfileScreen(
    id : Int = 0,
    rootNavController: RootNavController = RootNavController()
) {
    CompositionLocalProvider(LocalProfileImage provides {provideTorangAsyncImage().invoke(
        it.modifier,
        it.url,
        it.errorIconSize,
        it.progressSize,
        it.contentScale)}) {
        ProfileScreenNavHost(
            id = id,
            onClose = { },
            onEmailLogin = { },
            onReview = { rootNavController.myReview(it) },
            onMessage = { }
        )
    }
}
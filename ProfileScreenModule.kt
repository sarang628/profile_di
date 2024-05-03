package com.sarang.torang.di.profile_di

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.ProfileUiState

import com.sarang.torang.compose.FeedListScreen
import com.sarang.torang.compose.edit.ProfileNavHost
import com.sarang.torang.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onReview: ((Int) -> Unit)? = null
) {
    val uiState by profileViewModel.uiState.collectAsState()

    ProfileNavHost(
        profileViewModel = profileViewModel,
        onSetting = onSetting,
        favorite = {
            if (uiState is ProfileUiState.Success) {
                FeedListScreen(/*favorite*/
                    userId = (uiState as ProfileUiState.Success).id,
                    onReview = onReview
                )
            }

        },
        wantToGo = {
            if (uiState is ProfileUiState.Success) {
                FeedListScreen(/*wantToGo*/
                    userId = (uiState as ProfileUiState.Success).id,
                    onReview = onReview
                )
            }
        },
        galleryScreen = { onNext, onClose ->
            GalleryNavHost(onNext = onNext, onClose = { onClose.invoke() })
        },
        isMyProfile = navBackStackEntry == null,
        id = navBackStackEntry?.arguments?.getString("id")?.toInt(),
        onClose = onClose,
        onEmailLogin = onEmailLogin
    )
}

fun provideProfileScreen(navController : NavHostController) : @Composable () -> Unit = {
    ProfileScreen(
        onSetting = { navController.navigate("settings") },
        navBackStackEntry = null,
        onClose = { navController.popBackStack() },
        onEmailLogin = { navController.navigate("emailLogin") },
        onReview = {
            Log.d("__Main", "reviewId : ${it}")
            navController.navigate("myFeed/${it}")
        }
    )
}
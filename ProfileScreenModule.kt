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
import com.sarang.torang.compose.profile.MyProfileNavHost
import com.sarang.torang.compose.profile.ProfileNavHost
import com.sarang.torang.viewmodel.MyProfileViewModel
import com.sarang.torang.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onReview: ((Int) -> Unit)? = null,
    userId: Int,
    onProfile: ((Int) -> Unit)? = null
) {
    val uiState by profileViewModel.uiState.collectAsState()

    ProfileNavHost(
        profileViewModel = profileViewModel,
        onSetting = onSetting,
        id = userId,
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
        onClose = onClose,
        onEmailLogin = onEmailLogin,
        onProfile = onProfile
    )
}

@Composable
fun MyProfileScreen(
    profileViewModel: MyProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onReview: ((Int) -> Unit)? = null,
    onProfile: ((Int) -> Unit)? = null
) {
    val uiState by profileViewModel.uiState.collectAsState()

    MyProfileNavHost(
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
        onClose = onClose,
        onEmailLogin = onEmailLogin,
        onProfile = onProfile
    )
}

fun provideProfileScreen(navController: NavHostController): @Composable () -> Unit = {
    MyProfileScreen(
        onSetting = { navController.navigate("settings") },
        navBackStackEntry = null,
        onClose = { navController.popBackStack() },
        onEmailLogin = { navController.navigate("emailLogin") },
        onReview = {
            Log.d("__Main", "reviewId : ${it}")
            navController.navigate("myFeed/${it}")
        },
        onProfile = {
            navController.navigate("profile/${it}")
        }
    )
}
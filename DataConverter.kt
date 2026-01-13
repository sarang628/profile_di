package com.sarang.torang.di.profile_di


import com.sarang.torang.BuildConfig
import com.sarang.torang.Follower
import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import com.sarang.torang.data.profile.FeedListItemUIState
import com.sarang.torang.data.remote.response.FeedApiModel
import com.sarang.torang.data.remote.response.FollowerApiModel
import com.sarang.torang.data.remote.response.UserApiModel
import com.sarang.torang.data.profile.Feed
import com.sarang.torang.viewmodel.profile.FollowUiState


fun ReviewAndImageEntity.toFeedListItemUIState(): Feed {
    return Feed(reviewId        = this.review.reviewId,
                userId          = this.review.userId ?: 0,
                restaurantId    = this.review.restaurantId,
                userName        = this.review.userName ?: "",
                restaurantName  = this.review.restaurantName,
                profilePicUrl   = this.review.profilePicUrl ?: "",
                contents        = this.review.contents ?: "",
                rating          = this.review.rating ?: 0f,
                likeAmount      = this.review.likeAmount ?: 0,
                commentAmount   = this.review.commentAmount ?: 0,
                createDate      = this.review.createDate ?: "",
                reviewImages    = this.images.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.pictureUrl },
                isLike          = this.like != null,
                isFavorite      = this.favorite != null)
}

fun FeedApiModel.toFeedListItemUIState(): FeedListItemUIState {
    return FeedListItemUIState(reviewId = this.reviewId,
                               url      = BuildConfig.REVIEW_IMAGE_SERVER_URL + this.url)
}

val FeedApiModel.url : String get() = if(this.pictures.isEmpty()) "" else this.pictures[0].picture_url

fun List<ReviewAndImageEntity>.toFeeds(): List<Feed> {
    return this.map { it.toFeedListItemUIState() }
}

fun Follower.toFollow(): Follow {
    return Follow(
        url = BuildConfig.PROFILE_IMAGE_SERVER_URL + this.profilePicUrl,
        name = this.userName,
        nickname = this.userName,
        id = this.followerId,
        isFollow = false
    )
}

fun UserApiModel.toFollowUiState(): FollowUiState {
    return FollowUiState(name = this.userName,
                         following = this.following.toString(),
                         follower = this.follower.toString(),
                         subscription = this.post.toString())
}
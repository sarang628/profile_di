package com.sarang.torang.di.profile_di


import com.sarang.torang.BuildConfig
import com.sarang.torang.Feed
import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.viewmodel.FollowUiState
import com.sryang.torang_repository.data.entity.ReviewAndImageEntity
import com.sryang.torang_repository.data.remote.response.RemoteFeed
import com.sryang.torang_repository.data.remote.response.RemoteFollower
import com.sryang.torang_repository.data.remote.response.RemoteUser


fun ReviewAndImageEntity.toFeed(): Feed {
    return Feed(
        this.review.reviewId,
        this.review.userId,
        this.review.restaurantId,
        this.review.userName,
        this.review.restaurantName,
        this.review.profilePicUrl,
        this.review.contents,
        this.review.rating,
        this.review.likeAmount,
        this.review.commentAmount,
        this.review.createDate,
        reviewImage = this.images.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.pictureUrl },
        isLike = this.like != null,
        isFavorite = this.favorite != null
    )
}

fun RemoteFeed.toFeed(): Feed {
    return Feed(
        this.reviewId,
        this.user.userId,
        this.restaurant.restaurantId,
        "",
        "",
        "",
        this.contents,
        this.rating,
        this.like_amount,
        this.comment_amount,
        this.create_date,
        reviewImage = this.pictures.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.picture_url },
        isLike = this.like != null,
        isFavorite = this.favorite != null
    )
}

fun List<ReviewAndImageEntity>.toFeeds(): List<Feed> {
    return this.map { it.toFeed() }
}

fun RemoteFollower.toFollow(): Follow {
    return Follow(
        url = BuildConfig.PROFILE_IMAGE_SERVER_URL + this.profilePicUrl,
        name = this.userName,
        nickname = this.userName,
        id = this.followerId,
        isFollow = false
    )
}

fun RemoteUser.toFollowUiState(): FollowUiState {
    return FollowUiState(
        name = this.userName, following = this.following, follower = this.follower, subscription = 0
    )
}
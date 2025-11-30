package com.sarang.torang.di.profile_di


import com.sarang.torang.BuildConfig
import com.sarang.torang.Feed
import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.viewmodel.FollowUiState
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import com.sarang.torang.data.remote.response.FeedApiModel
import com.sarang.torang.data.remote.response.FollowerApiModel
import com.sarang.torang.data.remote.response.UserApiModel


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
        reviewImages = this.images.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.pictureUrl },
        isLike = this.like != null,
        isFavorite = this.favorite != null
    )
}

fun FeedApiModel.toFeed(): Feed {
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
        reviewImages = this.pictures.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.picture_url },
        isLike = this.like != null,
        isFavorite = this.favorite != null
    )
}

fun List<ReviewAndImageEntity>.toFeeds(): List<Feed> {
    return this.map { it.toFeed() }
}

fun FollowerApiModel.toFollow(): Follow {
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
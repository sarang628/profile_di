package com.sryang.myapplication.di.profile_di


import com.sarang.profile.compose.follow.Follow
import com.sarang.profile.uistate.Feed
import com.sarang.profile.viewmodel.FollowUiState
import com.sryang.base.feed.uistate.FeedUiState
import com.sryang.torang_repository.data.entity.ReviewAndImageEntity
import com.sryang.torang_repository.data.remote.response.RemoteFollower
import com.sryang.torang_repository.data.remote.response.RemoteUser

fun List<Feed>.toFeedUiState(): ArrayList<FeedUiState>
{
    return ArrayList(this.stream().map { it.toFeedUiState() }.toList())
}

fun Feed.toFeedUiState(): FeedUiState
{
    return FeedUiState(
        reviews = ArrayList(), isLoaded = true, isRefreshing = false
    )
}

fun ReviewAndImageEntity.toFeed(): Feed
{
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
        reviewImage = this.images.stream().map { it.pictureUrl }.toList(),
        isLike = this.like != null,
        isFavorite = this.favorite != null
    )
}

fun List<ReviewAndImageEntity>.toFeeds(): List<Feed>
{
    return this.stream().map { it.toFeed() }.toList()
}

fun RemoteFollower.toFollow(): Follow
{
    return Follow(
        url = this.profilePicUrl,
        name = this.userName,
        nickname = this.userName,
        id = this.followerId,
        isFollow = false
    )
}

fun RemoteUser.toFollowUiState(): FollowUiState
{
    return FollowUiState(
        name = this.userName, following = this.following, follower = this.follower, subscription = 0
    )
}
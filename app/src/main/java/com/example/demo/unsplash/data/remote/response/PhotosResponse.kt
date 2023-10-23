package com.example.demo.unsplash.data.remote.response


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class PhotosResponseItem(
  @Json(name = "alt_description")
  val altDescription: String?,
  @Json(name = "blur_hash")
  val blurHash: String?,
  @Json(name = "breadcrumbs")
  val breadcrumbs: List<Any?>?,
  @Json(name = "color")
  val color: String?,
  @Json(name = "created_at")
  val createdAt: String?,
  @Json(name = "current_user_collections")
  val currentUserCollections: List<Any?>?,
  @Json(name = "description")
  val description: String?,
  @Json(name = "height")
  val height: Int?,
  @Json(name = "id")
  val id: String?,
  @Json(name = "liked_by_user")
  val likedByUser: Boolean?,
  @Json(name = "likes")
  val likes: Int?,
  @Json(name = "promoted_at")
  val promotedAt: String?,
  @Json(name = "slug")
  val slug: String?,
  @Json(name = "updated_at")
  val updatedAt: String?,
  @Json(name = "urls")
  val urls: Urls?,
  @Json(name = "user")
  val user: User?,
  @Json(name = "width")
  val width: Int?
) {

  @Keep
  data class Urls(
    @Json(name = "full")
    val full: String?,
    @Json(name = "raw")
    val raw: String?,
    @Json(name = "regular")
    val regular: String?,
    @Json(name = "small")
    val small: String?,
    @Json(name = "small_s3")
    val smallS3: String?,
    @Json(name = "thumb")
    val thumb: String?
  )

  @Keep
  data class User(
    @Json(name = "accepted_tos")
    val acceptedTos: Boolean?,
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "for_hire")
    val forHire: Boolean?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "instagram_username")
    val instagramUsername: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "portfolio_url")
    val portfolioUrl: String?,
    @Json(name = "profile_image")
    val profileImage: ProfileImage?,
    @Json(name = "total_collections")
    val totalCollections: Int?,
    @Json(name = "total_likes")
    val totalLikes: Int?,
    @Json(name = "total_photos")
    val totalPhotos: Int?,
    @Json(name = "twitter_username")
    val twitterUsername: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "username")
    val username: String?
  ) {
    @Keep
    data class ProfileImage(
      @Json(name = "large")
      val large: String?,
      @Json(name = "medium")
      val medium: String?,
      @Json(name = "small")
      val small: String?
    )
  }
}

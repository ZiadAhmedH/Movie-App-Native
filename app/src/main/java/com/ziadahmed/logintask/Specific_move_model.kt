package com.ziadahmed.logintask

data class Specific_move_model(

val id: Long,
val url: String,
val imdbCode: String,
val title: String,
val titleEnglish: String,
val titleLong: String,
val slug: String,
val year: Long,
val rating: Double,
val runtime: Long,
val genres: List<String>,
val likeCount: Long,
val descriptionIntro: String,
val descriptionFull: String,
val ytTrailerCode: String,
val language: String,
val mpaRating: String,
val backgroundImage: String,
val backgroundImageOriginal: String,
val smallCoverImage: String,
val mediumCoverImage: String,
val largeCoverImage: String,
val dateUploaded: String,
val dateUploadedUnix: Long,
)


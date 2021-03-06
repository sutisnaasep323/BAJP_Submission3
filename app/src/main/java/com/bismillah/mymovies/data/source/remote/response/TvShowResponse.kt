package com.bismillah.mymovies.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(

    @field:SerializedName("results")
    val results: List<TvShow>
)

data class TvShow(

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("original_language")
    val originalLanguage: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("poster_path")
    val posterPath: String,

    )

package com.cheesecake.data.models


import com.cheesecake.data.local.models.TeamLocalDto
import com.google.gson.annotations.SerializedName

data class TeamInformationResponse(
    @SerializedName("team")
    val teamResponse: TeamResponse,
    @SerializedName("venue")
    val venue: Venue
) {
    data class TeamResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("founded")
        val founded: Int,
        @SerializedName("national")
        val national: Boolean,
        @SerializedName("logo")
        val logo: String
    )

    data class Venue(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("capacity")
        val capacity: Int,
        @SerializedName("surface")
        val surface: String,
        @SerializedName("image")
        val image: String
    )
}

fun TeamInformationResponse.toLocal() = TeamLocalDto(
    teamId = this.teamResponse.id,
    teamName = this.teamResponse.name,
    founded = this.teamResponse.founded,
    teamCountry = this.teamResponse.country,
    venueCapacity = this.venue.capacity,
    venueName = this.venue.name,
    imageUrl = this.teamResponse.logo
)
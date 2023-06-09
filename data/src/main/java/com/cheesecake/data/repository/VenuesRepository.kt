package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.VenuesResponse
import com.cheesecake.data.remote.venues.IVenuesApiService
import retrofit2.Response
import javax.inject.Inject

class VenuesRepository @Inject constructor (private val venuesApiService: IVenuesApiService) {

    suspend fun getVenueById(venueId: Int): Response<BaseResponse<VenuesResponse>> {
        return venuesApiService.getVenueById(venueId)
    }

    suspend fun getVenueByName(venueName: String): Response<BaseResponse<VenuesResponse>> {
        return venuesApiService.getVenueByName(venueName)
    }

    suspend fun getVenuesByCityName(cityName: String): Response<BaseResponse<VenuesResponse>> {
        return venuesApiService.getVenuesByCityName(cityName)
    }

    suspend fun getVenuesByCountryName(countryName: String): Response<BaseResponse<VenuesResponse>> {
        return venuesApiService.getVenuesByCountryName(countryName)
    }

    suspend fun searchVenueByName(venueName: String): Response<BaseResponse<VenuesResponse>> {
        return venuesApiService.searchVenue(venueName)
    }

}

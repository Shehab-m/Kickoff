package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.VenuesResponse
import com.cheesecake.data.remote.venues.IVenuesApiService
import retrofit2.Response
import javax.inject.Inject

class VenuesRepository @Inject constructor (private val venuesApiService: IVenuesApiService) {


}

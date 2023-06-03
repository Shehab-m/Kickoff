package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.remote.timezone.ITimeZoneApiService
import retrofit2.Response
import javax.inject.Inject

class TimeZoneRepository @Inject constructor (private val timeZoneApiService: ITimeZoneApiService) {
    suspend fun getTimezoneList(): Response<BaseResponse<String>> {
        return timeZoneApiService.getTimezoneList()
    }
}
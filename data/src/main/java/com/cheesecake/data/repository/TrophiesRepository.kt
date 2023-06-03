package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.SingleTrophyResponse
import com.cheesecake.data.remote.trophies.ITrophiesApiService
import retrofit2.Response
import javax.inject.Inject

class TrophiesRepository @Inject constructor(private val trophiesApiService: ITrophiesApiService) {


}
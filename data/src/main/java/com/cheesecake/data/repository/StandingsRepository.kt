package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.StandingsResponse
import com.cheesecake.data.remote.standings.IStandingsApiService
import retrofit2.Response
import javax.inject.Inject

class StandingsRepository @Inject constructor(private val standingsApiService: IStandingsApiService) {

}
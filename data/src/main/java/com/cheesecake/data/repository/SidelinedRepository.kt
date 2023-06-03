package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.SingleSidelinedResponse
import com.cheesecake.data.remote.sidliend.ISidelinedApiService
import retrofit2.Response
import javax.inject.Inject

class SidelinedRepository @Inject constructor(private val sidelinedApiService: ISidelinedApiService) {

    suspend fun getPlayerSidelined(playerId: Int): Response<BaseResponse<SingleSidelinedResponse>> {
        return sidelinedApiService.getPlayerSidelined(playerId)
    }

    suspend fun getCoachSidelined(coachId: Int): Response<BaseResponse<SingleSidelinedResponse>> {
        return sidelinedApiService.getCoachSidelined(coachId)
    }
}
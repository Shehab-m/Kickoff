package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.LeagueResponse
import com.cheesecake.data.remote.league.ILeaguesApiService
import retrofit2.Response
import javax.inject.Inject

class LeagueRepository @Inject constructor(private val leaguesApiService: ILeaguesApiService) {

}

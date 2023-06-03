package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.SinglePlayerResponse
import com.cheesecake.data.models.SingleSquadResponse
import com.cheesecake.data.remote.player.IPlayersApiService
import retrofit2.Response
import javax.inject.Inject

class PlayerRepository @Inject constructor(private val playersApiService: IPlayersApiService) {


}

package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.FixtureResponse
import com.cheesecake.data.models.FixtureStatistics
import com.cheesecake.data.models.HeadToHeadResponse
import com.cheesecake.data.models.SingleEventResponse
import com.cheesecake.data.models.SingleFixtureResponse
import com.cheesecake.data.models.SingleLineupResponse
import com.cheesecake.data.remote.fixture.IFixturesApiService
import com.cheesecake.data.utils.FixtureStatus
import retrofit2.Response
import javax.inject.Inject

class FixturesRepository @Inject constructor(private val fixturesApiService: IFixturesApiService) {

}
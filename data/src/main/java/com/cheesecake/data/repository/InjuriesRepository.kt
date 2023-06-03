package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.SingleInjuriesResponse
import com.cheesecake.data.remote.injuries.IInjuriesApiService

import retrofit2.Response
import javax.inject.Inject

class InjuriesRepository @Inject constructor(private val injuriesApiService: IInjuriesApiService) {


}
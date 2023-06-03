package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.SingleTransferResponse
import com.cheesecake.data.remote.transfers.ITransferApiService
import retrofit2.Response
import javax.inject.Inject

class TransfersRepository @Inject constructor(private val transferApiService: ITransferApiService) {


}
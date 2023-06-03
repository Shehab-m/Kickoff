package com.cheesecake.data.repository

import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.TeamCountries
import com.cheesecake.data.remote.countries.ICountriesApiService
import javax.inject.Inject
import retrofit2.Response

class CountriesRepository @Inject constructor (private val countriesApiService: ICountriesApiService) {

}

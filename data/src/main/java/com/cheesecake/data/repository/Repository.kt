package com.cheesecake.data.repository

import android.util.Log
import com.cheesecake.data.di.DefaultDispatcher
import com.cheesecake.data.local.daos.TeamsDao
import com.cheesecake.data.local.models.LocalTeam
import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.BaseStaticResponse
import com.cheesecake.data.models.TeamCountries
import com.cheesecake.data.models.TeamInformationResponse
import com.cheesecake.data.models.TeamStatisticsResponse
import com.cheesecake.data.models.toLocal
import com.cheesecake.data.remote.teams.ITeamsApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val teamsApiService: ITeamsApiService,
    private val teamsDao: TeamsDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) {


    suspend fun getTeamsByLeagueAndSeason(
        leagueId: Int,
        seasonId: Int
    ): Flow<List<LocalTeam>> {
        return try {
            refresh(leagueId, seasonId)
            teamsDao.observeAll()
        } catch (e:Exception) {
            throw java.lang.Exception("Failed to connect to the server. Please check your internet connection and try again.")
        }
    }
    private suspend fun refresh(leagueId: Int, seasonId: Int) {
        withContext(defaultDispatcher) {
//            try {
                val remoteTeams = teamsApiService.getTeamsByLeagueAndSeason(leagueId, seasonId)
                teamsDao.deleteAll()
                Log.d("refresh: ", "here")
                remoteTeams.body()?.response?.let { teamInformation ->
                    teamsDao.upsertAll(teamInformation.map {
                        it.toLocal()
                    })
                }
//            } catch (e: Exception) {
//                val errorMessage = when (e) {
//                    is IOException -> "Failed to connect to the server. Please check your internet connection and try again."
//                    is HttpException -> "An error occurred while fetching data. Please try again later."
//                    else -> "An unexpected error occurred. Please try again later."
//                }
//                Log.d("refresh: ", e.message.toString())
//                Log.d("refresh: ",errorMessage)
//                // Display error message to the user (e.g., show a toast or update UI)
//                // You can use the Android Toast API to show a temporary message
//                // Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
//                // Alternatively, you can update a TextView or any other UI element with the error message
//                // textViewError.text = errorMessage
//               // throw Exception(errorMessage)
//            }
        }
    }

//    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
//        val response = function()
//        return if (response.isSuccessful) {
//            response.body()?.response
//            } else {
//            throw Throwable("Network Error")
//
//        } as T
//    }


    suspend fun getTeamById(teamId: Int): Response<BaseResponse<TeamInformationResponse>> {
        return teamsApiService.getTeamById(teamId)
    }

    suspend fun getTeamStatistics(
        teamId: Int,
        season: Int,
        leagueId: Int
    ): Response<BaseStaticResponse<TeamStatisticsResponse>> {
        return teamsApiService.getTeamStatistics(teamId, season, leagueId)
    }

    suspend fun getTeamSeasons(teamId: Int): Response<BaseResponse<Int>> {
        return teamsApiService.getTeamSeasons(teamId)
    }

    suspend fun getTeamCountries(): Response<BaseResponse<TeamCountries>> {
        return teamsApiService.getTeamCountries()
    }





}






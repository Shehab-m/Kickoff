package com.cheesecake.data.repository

import android.util.Log
import com.cheesecake.data.di.DefaultDispatcher
import com.cheesecake.data.local.daos.TeamsDao
import com.cheesecake.data.local.dataSource.LocalDataSource
import com.cheesecake.data.local.models.TeamLocalDto
import com.cheesecake.data.models.BaseResponse
import com.cheesecake.data.models.BaseStaticResponse
import com.cheesecake.data.models.CoachResponse
import com.cheesecake.data.models.FixtureResponse
import com.cheesecake.data.models.FixtureStatistics
import com.cheesecake.data.models.HeadToHeadResponse
import com.cheesecake.data.models.LeagueResponse
import com.cheesecake.data.models.SingleEventResponse
import com.cheesecake.data.models.SingleFixtureResponse
import com.cheesecake.data.models.SingleInjuriesResponse
import com.cheesecake.data.models.SingleLineupResponse
import com.cheesecake.data.models.SinglePlayerResponse
import com.cheesecake.data.models.SinglePredictionsResponse
import com.cheesecake.data.models.SingleSidelinedResponse
import com.cheesecake.data.models.SingleSquadResponse
import com.cheesecake.data.models.SingleTransferResponse
import com.cheesecake.data.models.SingleTrophyResponse
import com.cheesecake.data.models.StandingsResponse
import com.cheesecake.data.models.TeamCountries
import com.cheesecake.data.models.TeamInformationResponse
import com.cheesecake.data.models.TeamStatisticsResponse
import com.cheesecake.data.models.VenuesResponse
import com.cheesecake.data.models.toLocal
import com.cheesecake.data.remote.teams.ITeamsApiService
import com.cheesecake.data.utils.FixtureStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val teamsApiService: ITeamsApiService,
    private val teamsDao: TeamsDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) {


    suspend fun getTeamsByLeagueAndSeason(
        leagueId: Int,
        seasonId: Int
    ): Flow<List<TeamLocalDto>> {
        return try {
            refresh(leagueId, seasonId)
            localDataSource.getAllTeams()
        } catch (e:Exception) {
            throw java.lang.Exception("Failed to connect to the server. Please check your internet connection and try again.")
        }
    }
    private suspend fun refresh(leagueId: Int, seasonId: Int) {
        withContext(defaultDispatcher) {
//            try {
                val remoteTeams = teamsApiService.getTeamsByLeagueAndSeason(leagueId, seasonId)
                localDataSource.deleteAll()
                Log.d("refresh: ", "here")
                remoteTeams.body()?.response?.let { teamInformation ->
                    localDataSource.upsertAll(teamInformation.map {
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
//
//
//    suspend fun getCoachById(playerID: Int): Response<BaseResponse<CoachResponse>> {
//        return coachApiService.getCoachById(playerID)
//    }
//
//    suspend fun getCoachByTeam(teamID: Int): Response<BaseResponse<CoachResponse>> {
//        return coachApiService.getCoachByTeam(teamID)
//    }
//
//    suspend fun getCoachBySearch(coachName: String): Response<BaseResponse<CoachResponse>> {
//        return coachApiService.getCoachBySearch(coachName)
//    }
//
//    suspend fun getAllCountries(): Response<BaseResponse<TeamCountries>> {
//        return countriesApiService.getAllCountries()
//    }
//
//    suspend fun getCountryByName(countryName: String): Response<BaseResponse<TeamCountries>> {
//        return countriesApiService.getCountryByName(countryName)
//    }
//
//    suspend fun getCountryByCode(countryCode: String): Response<BaseResponse<TeamCountries>> {
//        return countriesApiService.getCountryByCode(countryCode)
//    }
//
//    suspend fun searchInCountries(searchQuery: String): Response<BaseResponse<TeamCountries>> {
//        return countriesApiService.searchInCountries(searchQuery)
//    }
//
//
//
//    //region ROUNDS
//    suspend fun getFixtureRounds(seasonId: Int, leagueId: Int): Response<BaseResponse<String>> {
//        return fixturesApiService.getFixtureRounds(seasonId, leagueId)
//    }
//
//    suspend fun getFixtureRoundsCurrentOnly(
//        seasonId: Int,
//        leagueId: Int,
//        current: Boolean
//    ): Response<BaseResponse<String>> {
//        return fixturesApiService.getFixtureRoundsCurrentOnly(seasonId, leagueId, current)
//    }
//
//    //endregion
//
//    //region FIXTURES
//    suspend fun getFixtureById(
//        timeZone: String,
//        fixtureId: Int
//    ): Response<BaseResponse<SingleFixtureResponse>> {
//        return fixturesApiService.getFixtureById(timeZone, fixtureId)
//    }
//
//    suspend fun getFixturesBySeasonIdByTeamId(
//        timeZone: String,
//        season: String,
//        teamId: Int
//    ): Response<BaseResponse<SingleFixtureResponse>> {
//        return fixturesApiService.getFixturesBySeasonIdByTeamId(timeZone, season, teamId)
//    }
//
//    suspend fun getFixturesByDate(
//        timeZone: String,
//        date: String
//    ): Response<BaseResponse<SingleFixtureResponse>> {
//        return fixturesApiService.getFixturesByDate(timeZone, date)
//    }
//
//    suspend fun getFixturesFromDate(
//        timeZone: String,
//        date: String
//    ): Response<BaseResponse<SingleFixtureResponse>> {
//        return fixturesApiService.getFixturesFromDate(timeZone, date)
//    }
//
//    suspend fun getFixturesToDate(
//        timeZone: String,
//        date: String
//    ): Response<BaseResponse<SingleFixtureResponse>> {
//        return fixturesApiService.getFixturesToDate(timeZone, date)
//    }
//
//    suspend fun getFixturesFromDateToDate(
//        timeZone: String,
//        season: String,
//        teamId: Int,
//        from: String,
//        to: String
//    ): Response<BaseResponse<SingleFixtureResponse>> {
//        return fixturesApiService.getFixturesFromDateToDate(timeZone, season, teamId, from, to)
//    }
//
//    suspend fun getFixturesStatus(
//        timeZone: String,
//        fixtureStatusType: String
//    ): Response<BaseResponse<SingleFixtureResponse>> {
//        return fixturesApiService.getFixturesStatus(timeZone, fixtureStatusType)
//    }
//    //endregion
//
//    //region HEAD 2 HEAD
//    suspend fun getHeadToHead(
//        teamsId: String,
//        seasonId: Int,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHead(teamsId, seasonId, timeZone)
//    }
//
//    suspend fun getHeadToHeadByDate(
//        teamsId: String,
//        date: String,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHeadByDate(teamsId, date, timeZone)
//    }
//
//    suspend fun getHeadToHeadByStatus(
//        teamsId: String,
//        status: FixtureStatus,
//        seasonId: Int,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHeadByStatus(teamsId, status, seasonId, timeZone)
//    }
//
//    suspend fun getHeadToHeadByFromAndTo(
//        teamsId: String,
//        from: String,
//        to: String,
//        seasonId: Int,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHeadByFromAndTO(teamsId, from, to, seasonId, timeZone)
//    }
//
//    suspend fun getHeadToHeadByLeague(
//        teamsId: String,
//        leagueId: Int,
//        seasonId: Int,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHeadByLeague(teamsId, leagueId, seasonId, timeZone)
//    }
//
//    suspend fun getHeadToHeadByDateAndLeague(
//        teamsId: String,
//        leagueId: Int,
//        date: String,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHeadByByDateAndLeague(teamsId, leagueId, date, timeZone)
//    }
//
//    suspend fun getHeadToHeadByStatusAndLeague(
//        teamsId: String,
//        leagueId: Int,
//        status: FixtureStatus,
//        seasonId: Int,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHeadByStatusAndLeague(
//            teamsId,
//            leagueId,
//            status,
//            seasonId,
//            timeZone
//        )
//    }
//
//    suspend fun getHeadToHeadByFromAndToAndLeague(
//        teamsId: String,
//        leagueId: Int,
//        from: String,
//        to: String,
//        seasonId: Int,
//        timeZone: String
//    ): Response<BaseResponse<HeadToHeadResponse>> {
//        return fixturesApiService.getHeadToHeadByFromAndTOAndLeague(
//            teamsId,
//            leagueId,
//            from,
//            to,
//            seasonId,
//            timeZone
//        )
//    }
//    //endregion
//
//    //region STATISTICS
//    suspend fun getFixtureStatisticsByFixtureId(
//        fixtureId: Int
//    ): Response<BaseResponse<FixtureStatistics>> {
//        return fixturesApiService.getFixtureStatisticsByFixtureId(fixtureId)
//    }
//
//    suspend fun getFixtureStatisticsByFixtureIdByTeamId(
//        fixtureId: Int,
//        teamId: Int
//    ): Response<BaseResponse<FixtureStatistics>> {
//        return fixturesApiService.getFixtureStatisticsByFixtureIdByTeamId(fixtureId, teamId)
//    }
//    //endregion
//
//    //region EVENTS
//    suspend fun getFixtureEventsByFixtureId(fixtureId: Int): Response<BaseResponse<SingleEventResponse>> {
//        return fixturesApiService.getFixtureEventsByFixtureId(fixtureId)
//    }
//
//    suspend fun getFixtureEventsByFixtureIdByTeamId(
//        fixtureId: Int,
//        teamId: Int
//    ): Response<BaseResponse<SingleEventResponse>> {
//        return fixturesApiService.getFixtureEventsByFixtureIdByTeamId(fixtureId, teamId)
//    }
//
//    suspend fun getFixtureEventsByFixtureIdByTeamIdByPlayerId(
//        fixtureId: Int,
//        teamId: Int,
//        playerId: Int
//    ): Response<BaseResponse<SingleEventResponse>> {
//        return fixturesApiService.getFixtureEventsByFixtureIdByTeamIdByPlayerId(
//            fixtureId,
//            teamId,
//            playerId
//        )
//    }
//
//    suspend fun getFixtureEventsByFixtureIdByTeamIdByPlayerIdByType(
//        fixtureId: Int,
//        teamId: Int,
//        playerId: Int,
//        fixtureEventType: String
//    ): Response<BaseResponse<SingleEventResponse>> {
//        return fixturesApiService.getFixtureEventsByFixtureIdByTeamIdByPlayerIdByType(
//            fixtureId,
//            teamId,
//            playerId,
//            fixtureEventType
//        )
//    }
//    //endregion
//
//    //region PLAYERS
//    suspend fun getFixturePlayersByFixtureId(fixtureId: String
//    ): Response<BaseResponse<FixtureResponse>> {
//        return fixturesApiService.getFixturePlayersByFixtureId(fixtureId)
//    }
//
//    suspend fun getFixturePlayersByFixtureIdByTeamId(
//        fixtureId: Int,
//        teamId: Int
//    ): Response<BaseResponse<FixtureResponse>> {
//        return fixturesApiService.getFixturePlayersByFixtureIdByTeamId(fixtureId, teamId)
//    }
//    //endregion
//
//    //region LINEUPS
//    suspend fun getFixtureLineupsByFixtureId(
//        fixtureId: Int
//    ): Response<BaseResponse<SingleLineupResponse>> {
//        return fixturesApiService.getFixtureLineupsByFixtureId(fixtureId)
//    }
//
//    suspend fun getFixtureLineupsByFixtureIdByTeamId(
//        fixtureId: Int,
//        teamId: Int
//    ): Response<BaseResponse<SingleLineupResponse>> {
//        return fixturesApiService.getFixtureLineupsByFixtureIdByTeamId(fixtureId, teamId)
//    }
//
//    suspend fun getFixtureLineupsByFixtureIdByPlayerId(
//        fixtureId: Int,
//        playerId: Int
//    ): Response<BaseResponse<SingleLineupResponse>> {
//        return fixturesApiService.getFixtureLineupsByFixtureIdByPlayerId(fixtureId, playerId)
//    }
//    //endregion
//
//    suspend fun getInjuriesByFixtureID(fixtureId: Int)
//            :Response<BaseResponse<SingleInjuriesResponse>> {
//        return injuriesApiService.getInjuriesByFixtureID(fixtureId)
//    }
//
//
//    suspend fun getInjuriesByTeamIDAndSeason(teamId:Int, season: Int):
//            Response<BaseResponse<SingleInjuriesResponse>>{
//        return injuriesApiService.getInjuriesByTeamIDAndSeason(teamId,season)
//    }
//
//    suspend fun getInjuriesByPlayerIDAndSeason(playerId:Int,season: Int)
//            :Response<BaseResponse<SingleInjuriesResponse>>{
//        return injuriesApiService.getInjuriesByPlayerIDAndSeason(playerId,season)
//    }
//    suspend fun getInjuriesByTimeZone(timeZone: String):
//            Response<BaseResponse<SingleInjuriesResponse>>{
//        return injuriesApiService.getInjuriesByTimeZone(timeZone)
//    }
//    suspend fun getInjuriesByDate(date: String)
//            :Response<BaseResponse<SingleInjuriesResponse>>{
//        return injuriesApiService.getInjuriesByDate(date)
//
//    }
//
//
//    suspend fun getAllLeagues(): Response<BaseResponse<LeagueResponse>> {
//        return leaguesApiService.getAllLeagues()
//    }
//
//    suspend fun getLeaguesById(leagueId: Int): Response<BaseResponse<LeagueResponse>> {
//        return leaguesApiService.getLeaguesById(leagueId)
//    }
//
//    suspend fun getLeaguesByName(leagueName: String): Response<BaseResponse<LeagueResponse>> {
//        return leaguesApiService.getLeaguesByName(leagueName)
//    }
//
//    suspend fun getLeaguesByCountryName(countryName: String): Response<BaseResponse<LeagueResponse>> {
//        return leaguesApiService.getLeaguesByCountryName(countryName)
//    }
//
//    suspend fun getLeaguesByCountryCode(countryCode: String): Response<BaseResponse<LeagueResponse>>{
//        return leaguesApiService.getLeaguesByCountryCode(countryCode)
//    }
//
//    suspend fun getLeaguesOfSeason(season: Int): Response<BaseResponse<LeagueResponse>> {
//        return leaguesApiService.getLeaguesOfSeason(season)
//    }
//
//    suspend fun getLeagueByIdBySeason(leagueId: Int, season: Int): Response<BaseResponse<LeagueResponse>> {
//        return leaguesApiService.getLeagueByIdBySeason(leagueId, season)
//    }
//
//    suspend fun getPlayerBySeasonByPlayerId(
//        season: String,
//        playerId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.getPlayerBySeasonByPlayerId(season, playerId)
//    }
//
//    suspend fun getPlayerBySeasonByTeamId(
//        season: String,
//        teamId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.getPlayerBySeasonByTeamId(season, teamId)
//    }
//
//    suspend fun getPlayerBySeasonByLeagueId(
//        season: String,
//        leagueId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.getPlayerBySeasonByLeagueId(season, leagueId)
//    }
//
//    suspend fun searchPlayerNameByTeamId(
//        playerName: String,
//        teamId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.searchPlayerNameByTeamId(playerName, teamId)
//    }
//
//    suspend fun searchPlayerNameByLeagueId(
//        playerName: String,
//        leagueId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.searchPlayerNameByLeagueId(playerName, leagueId)
//    }
//
//    suspend fun getPlayerSeasons(): Response<BaseResponse<Int>> {
//        return playersApiService.getPlayerSeasons()
//    }
//
//    suspend fun getSquadByPlayerId(playerId: Int): Response<BaseResponse<SingleSquadResponse>> {
//        return playersApiService.getSquadByPlayerId(playerId)
//    }
//
//    suspend fun getSquadByTeamId(teamId: Int): Response<BaseResponse<SingleSquadResponse>> {
//        return playersApiService.getSquadByTeamId(teamId)
//    }
//
//    suspend fun getTopScorers(
//        seasonId: Int,
//        leagueId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.getTopScorers(seasonId, leagueId)
//    }
//
//    suspend fun getTopAssists(
//        seasonId: Int,
//        leagueId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.getTopAssists(seasonId, leagueId)
//    }
//
//    suspend fun getTopYellowCards(
//        seasonId: Int,
//        leagueId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.getTopYellowCards(seasonId, leagueId)
//    }
//
//    suspend fun getTopRedCards(
//        seasonId: Int,
//        leagueId: Int
//    ): Response<BaseResponse<SinglePlayerResponse>> {
//        return playersApiService.getTopRedCards(seasonId, leagueId)
//    }
//
//    suspend fun getPredictionsByFixtureId(fixtureId: Int): Response<BaseResponse<SinglePredictionsResponse>> {
//        return predictionsApiService.getPredictionsByFixtureId(fixtureId)
//    }
//
//    suspend fun getPlayerSidelined(playerId: Int): Response<BaseResponse<SingleSidelinedResponse>> {
//        return sidelinedApiService.getPlayerSidelined(playerId)
//    }
//
//    suspend fun getCoachSidelined(coachId: Int): Response<BaseResponse<SingleSidelinedResponse>> {
//        return sidelinedApiService.getCoachSidelined(coachId)
//    }
//
//
//    suspend fun getStandingsByTeamId(
//        seasonId: Int,
//        teamId: Int
//    ): Response<BaseResponse<StandingsResponse>> {
//        return standingsApiService.getStandingsByTeamId(seasonId, teamId)
//    }
//
//    suspend fun getStandingsByLeagueId(
//        seasonId: Int,
//        leagueId: Int
//    ): Response<BaseResponse<StandingsResponse>> {
//        return standingsApiService.getStandingsByLeagueId(seasonId, leagueId)
//    }
//
//    suspend fun getStandingsByTeamIdAndLeagueId(
//        seasonId: Int,
//        teamId: Int,
//        leagueId: Int
//    ): Response<BaseResponse<StandingsResponse>> {
//        return standingsApiService.getStandingsByTeamIdAndLeagueId(seasonId, teamId, leagueId)
//    }
//
//    suspend fun getTimezoneList(): Response<BaseResponse<String>> {
//        return timeZoneApiService.getTimezoneList()
//    }
//
//    suspend fun getTransfersByPlayerId(playerId: Int): Response<BaseResponse<SingleTransferResponse>> {
//        return transferApiService.getTransfersByPlayerId(playerId)
//    }
//
//    suspend fun getTransfersByTeamId(teamId: Int): Response<BaseResponse<SingleTransferResponse>> {
//        return transferApiService.getTransfersByTeamId(teamId)
//    }
//
//    suspend fun getPlayerTrophies(playerId: Int): Response<BaseResponse<SingleTrophyResponse>> {
//        return trophiesApiService.getPlayerTrophies(playerId)
//    }
//
//    suspend fun getCoachTrophies(coachId: Int): Response<BaseResponse<SingleTrophyResponse>> {
//        return trophiesApiService.getCoachTrophies(coachId)
//    }
//
//
//    suspend fun getVenueById(venueId: Int): Response<BaseResponse<VenuesResponse>> {
//        return venuesApiService.getVenueById(venueId)
//    }
//
//    suspend fun getVenueByName(venueName: String): Response<BaseResponse<VenuesResponse>> {
//        return venuesApiService.getVenueByName(venueName)
//    }
//
//    suspend fun getVenuesByCityName(cityName: String): Response<BaseResponse<VenuesResponse>> {
//        return venuesApiService.getVenuesByCityName(cityName)
//    }
//
//    suspend fun getVenuesByCountryName(countryName: String): Response<BaseResponse<VenuesResponse>> {
//        return venuesApiService.getVenuesByCountryName(countryName)
//    }
//
//    suspend fun searchVenueByName(venueName: String): Response<BaseResponse<VenuesResponse>> {
//        return venuesApiService.searchVenue(venueName)
//    }
}






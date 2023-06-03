package com.cheesecake.usecases

import com.cheesecake.data.local.models.TeamLocalDto
import com.cheesecake.data.repository.Repository
import com.cheesecake.models.TeamEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTeamsInLeagueWithSeason @Inject constructor(
    private val repository: Repository,
    ) {

    suspend operator fun invoke(leagueId: Int, season: Int) =
         repository.getTeamsByLeagueAndSeason(leagueId, season).map { localTeams ->
             localTeams.map {
                 TeamEntity(
                     teamId = it.teamId,
                     teamName = it.teamName,
                     founded = it.founded,
                     teamCountry = it.teamCountry,
                     venueCapacity = it.venueCapacity,
                     venueName = it.venueName,
                     imageUrl = it.imageUrl
                 )
             }
         }
//        fun TeamLocalDto.toEntity(): TeamEntity {
//            return
//        }


//    suspend operator fun invoke(leagueId: Int, season: Int) {
//        try {
//            teamsRepository.getTeamsByLeagueAndSeason(leagueId, season)
//                .map { localTeams ->
//                    localTeams.map {
//                        Team(
//                            teamId = it.teamId,
//                            teamName = it.teamName,
//                            founded = it.founded,
//                            teamCountry = it.teamCountry,
//                            venueCapacity = it.venueCapacity,
//                            venueName = it.venueName,
//                            imageUrl = it.imageUrl
//                        )
//                    }
//                }
//                .collect { list ->
//                    // Update the ViewModel with the successful result
//                    _leagueTeamsUIState.update {
//                        it.copy(data = list, isLoading = false, isError = null)
//                    }
//                }
//        } catch (e: Exception) {
//            // Handle the exception and update the ViewModel with the error message
//            _leagueTeamsUIState.update {
//                it.copy(isError = e.message)
//            }
//        }
//    }


}
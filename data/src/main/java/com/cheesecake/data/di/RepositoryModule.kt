package com.cheesecake.data.di

import com.cheesecake.data.local.daos.TeamsDao
import com.cheesecake.data.repository.CoachRepository
import com.cheesecake.data.remote.coach.ICoachApiService
import com.cheesecake.data.repository.CountriesRepository
import com.cheesecake.data.remote.countries.ICountriesApiService
import com.cheesecake.data.repository.FixturesRepository
import com.cheesecake.data.remote.fixture.IFixturesApiService
import com.cheesecake.data.remote.injuries.IInjuriesApiService
import com.cheesecake.data.repository.InjuriesRepository
import com.cheesecake.data.remote.league.ILeaguesApiService
import com.cheesecake.data.repository.LeagueRepository
import com.cheesecake.data.remote.player.IPlayersApiService
import com.cheesecake.data.repository.PlayerRepository
import com.cheesecake.data.remote.predictions.IPredictionsApiService
import com.cheesecake.data.repository.PredictionsRepository
import com.cheesecake.data.remote.sidliend.ISidelinedApiService
import com.cheesecake.data.repository.SidelinedRepository
import com.cheesecake.data.remote.standings.IStandingsApiService
import com.cheesecake.data.repository.StandingsRepository
import com.cheesecake.data.remote.teams.ITeamsApiService
import com.cheesecake.data.repository.Repository
import com.cheesecake.data.remote.timezone.ITimeZoneApiService
import com.cheesecake.data.repository.TimeZoneRepository
import com.cheesecake.data.remote.transfers.ITransferApiService
import com.cheesecake.data.repository.TransfersRepository
import com.cheesecake.data.remote.trophies.ITrophiesApiService
import com.cheesecake.data.repository.TrophiesRepository
import com.cheesecake.data.remote.venues.IVenuesApiService
import com.cheesecake.data.repository.VenuesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCoachRepository(coachApiService: ICoachApiService): CoachRepository {
        return CoachRepository(coachApiService)
    }

    @Singleton
    @Provides
    fun provideCountriesRepository(countriesApiService: ICountriesApiService): CountriesRepository {
        return CountriesRepository(countriesApiService)
    }

    @Singleton
    @Provides
    fun provideFixturesRepository(fixturesApiService: IFixturesApiService): FixturesRepository {
        return FixturesRepository(fixturesApiService)
    }

    @Singleton
    @Provides
    fun provideLeagueRepository(leagueApiService: ILeaguesApiService): LeagueRepository {
        return LeagueRepository(leagueApiService)
    }

    @Singleton
    @Provides
    fun providePlayerRepository(playersApiService: IPlayersApiService): PlayerRepository {
        return PlayerRepository(playersApiService)
    }

    @Singleton
    @Provides
    fun providePredictionsRepository(predictionsApiService: IPredictionsApiService): PredictionsRepository {
        return PredictionsRepository(predictionsApiService)
    }

    @Singleton
    @Provides
    fun provideSidelinedRepository(sidelinedApiService: ISidelinedApiService): SidelinedRepository {
        return SidelinedRepository(sidelinedApiService)
    }

    @Singleton
    @Provides
    fun provideStandingsRepository(standingsApiService: IStandingsApiService): StandingsRepository {
        return StandingsRepository(standingsApiService)
    }

    @Singleton
    @Provides
    fun provideTeamsRepository(
        teamsApiService: ITeamsApiService,
        teamsDao: TeamsDao,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): Repository {
        return Repository(teamsApiService, teamsDao, defaultDispatcher)
    }

    @Singleton
    @Provides
    fun provideTimezoneRepository(timezoneApiService: ITimeZoneApiService): TimeZoneRepository {
        return TimeZoneRepository(timezoneApiService)
    }

    @Singleton
    @Provides
    fun provideTransfersRepository(transferApiService: ITransferApiService): TransfersRepository {
        return TransfersRepository(transferApiService)
    }

    @Singleton
    @Provides
    fun provideTrophiesRepository(trophiesApiService: ITrophiesApiService): TrophiesRepository {
        return TrophiesRepository(trophiesApiService)
    }

    @Singleton
    @Provides
    fun provideVenuesRepository(venuesApiService: IVenuesApiService): VenuesRepository {
        return VenuesRepository(venuesApiService)
    }

    @Singleton
    @Provides
    fun provideInjuriesRepository(injuriesApiService: IInjuriesApiService): InjuriesRepository {
        return InjuriesRepository(injuriesApiService)
    }

}
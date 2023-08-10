package com.bonobono.di

import com.bonobono.data.local.PreferenceDataSource
import com.bonobono.data.remote.CommunityService
import com.bonobono.data.remote.MissionService
import com.bonobono.data.repository.MissionRepositoryImpl
import com.bonobono.data.remote.RegisterService
import com.bonobono.data.repository.community.CommunityRepositoryImpl
import com.bonobono.domain.repository.MissionRepository
import com.bonobono.data.repository.register.RegisterRepositoryImpl
import com.bonobono.domain.repository.community.CommunityRepository
import com.bonobono.domain.repository.registration.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCommunityRepository(communityService: CommunityService): CommunityRepository {
        return CommunityRepositoryImpl(communityService)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(registerService: RegisterService) : RegisterRepository {
        return RegisterRepositoryImpl(registerService)
    }

    @Provides
    @Singleton
    fun provideMissionRepository(
        missionService: MissionService,
        preferenceDataSource: PreferenceDataSource
    ): MissionRepository {
        return MissionRepositoryImpl(
            missionService = missionService,
            preferenceDatasource = preferenceDataSource
        )
    }
}
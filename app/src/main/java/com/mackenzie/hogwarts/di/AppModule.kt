package com.mackenzie.hogwarts.di

import android.app.Application
import androidx.room.Room
import com.mackenzie.data.datasources.HeadsLocalDataSource
import com.mackenzie.data.datasources.HousesLocalDataSource
import com.mackenzie.data.datasources.HousesServerDataSource
import com.mackenzie.hogwarts.data.db.HeadsDataSource
import com.mackenzie.hogwarts.data.db.HogwartsDataBase
import com.mackenzie.hogwarts.data.db.HousesDataSource
import com.mackenzie.hogwarts.data.server.HogwartsService
import com.mackenzie.hogwarts.data.server.ServerDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        HogwartsDataBase::class.java,
        "hogwarts-database"
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(db: HogwartsDataBase) = db.usersDao()

    @Provides
    @Singleton
    fun provideHousesDao(db: HogwartsDataBase) = db.housesDao()

    @Provides
    @Singleton
    fun provideHeadDao(db: HogwartsDataBase) = db.headsDao()

    @Provides
    @Singleton
    fun provideApiUrl(): String = "https://wizard-world-api.herokuapp.com"

    @Provides
    @Singleton
    fun provideWaifuService(apiUrl: String): HogwartsService {

        val builder = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = builder.create(HogwartsService::class.java)

        return service
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: HousesDataSource): HousesLocalDataSource

    @Binds
    abstract fun bindFavoriteDataSource(headsDataSource: HeadsDataSource): HeadsLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteBestDataSource: ServerDataSource): HousesServerDataSource

}
package uz.gita.fooddedivery347.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.fooddedivery347.domain.repository.AppRepository
import uz.gita.fooddedivery347.domain.repository.AppRepositoryImpl
import uz.gita.fooddedivery347.domain.repository.auth.AuthRepository
import uz.gita.fooddedivery347.domain.repository.auth.AuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindAppRepository(impl: AppRepositoryImpl): AppRepository

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}
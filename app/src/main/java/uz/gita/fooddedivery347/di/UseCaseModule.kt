package uz.gita.fooddedivery347.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.fooddedivery347.domain.usecase.UseCase
import uz.gita.fooddedivery347.domain.usecase.UseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindUseCase(impl: UseCaseImpl): UseCase
}
package uz.gita.fooddedivery347.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.fooddedivery347.presentation.screen.home.HomeDirection
import uz.gita.fooddedivery347.presentation.screen.home.HomeDirectionImpl
import uz.gita.fooddedivery347.presentation.screen.home.page1.Page1Direction
import uz.gita.fooddedivery347.presentation.screen.home.page1.Page1DirectionImpl
import uz.gita.fooddedivery347.presentation.screen.home.page2.Page2Direction
import uz.gita.fooddedivery347.presentation.screen.home.page2.Page2DirectionImpl
import uz.gita.fooddedivery347.presentation.screen.home.page3.Page3Direction
import uz.gita.fooddedivery347.presentation.screen.home.page3.Page3DirectionImpl
import uz.gita.fooddedivery347.presentation.screen.login.LoginScreenContract
import uz.gita.fooddedivery347.presentation.screen.login.LoginScreenDirection
import uz.gita.fooddedivery347.presentation.screen.verify.VerifyScreenContract
import uz.gita.fooddedivery347.presentation.screen.verify.VerifyScreenDirection

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @Binds
    fun bindHomeDirection(impl: HomeDirectionImpl): HomeDirection

    @Binds
    fun bindPage1Direction(impl: Page1DirectionImpl): Page1Direction

    @Binds
    fun bindPage2Direction(impl: Page2DirectionImpl): Page2Direction

    @Binds
    fun bindPage3Direction(impl: Page3DirectionImpl): Page3Direction


    @Binds
    fun bindLoginDirection(impl: LoginScreenDirection): LoginScreenContract.Direction

    @Binds
    fun bindVerifyDirection(impl: VerifyScreenDirection): VerifyScreenContract.Direction
}
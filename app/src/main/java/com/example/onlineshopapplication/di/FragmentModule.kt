package com.example.onlineshopapplication.di

import academy.nouri.storeapp.data.models.login.BodyLogin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {
    @Provides
    fun provideBodyLogin() = BodyLogin()
}
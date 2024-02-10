package com.top.best.ecommerce.echoexpress.di

import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.data.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebase(mAuth: FirebaseAuth): AuthService{
        return AuthService(mAuth)
    }
}
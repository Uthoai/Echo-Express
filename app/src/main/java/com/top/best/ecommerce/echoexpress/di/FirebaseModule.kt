package com.top.best.ecommerce.echoexpress.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.top.best.ecommerce.echoexpress.data.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.grpc.Context.Storage
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
    fun provideFirebaseFireStoreDB(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebase(mAuth: FirebaseAuth,db: FirebaseFirestore): AuthRepository{
        return AuthRepository(mAuth,db)
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStorage(): StorageReference{
        return FirebaseStorage.getInstance().reference
    }
}

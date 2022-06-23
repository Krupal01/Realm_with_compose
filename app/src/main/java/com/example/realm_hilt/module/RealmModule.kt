package com.example.realm_hilt.module

import com.example.realm_hilt.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RealmModule {


    @Singleton
    @Provides
    fun getRealmInstance():Realm{
        return Realm.getDefaultInstance()
    }

    @Singleton
    @Provides
    fun getUserDao(realm : Realm): UserDao {
        return UserDao(realm)
    }

}
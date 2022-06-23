package com.example.realm_and_compose

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .schemaVersion(1L)
                .name("realm_and_compose")
                .build()
        )
    }
}
package com.example.realm_and_compose.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class User(

    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    var name : String = "",
    var phone_number : String = "",


) : RealmObject()

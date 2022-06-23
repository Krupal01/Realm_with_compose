package com.example.realm_hilt.dao

import com.example.realm_hilt.model.User
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import javax.inject.Inject


class UserDao @Inject constructor(private val realmInstance : Realm) {

    fun getAllUsers(): List<User> {
        return realmInstance.where(User::class.java).findAll()
    }

    suspend fun addUser(user : User) {
        realmInstance.executeTransactionAwait { it.insert(user) }
    }

    suspend fun updateUser(user : User) : Boolean {
        var result  = false
        realmInstance.executeTransactionAwait {
            val userOld = it.where(User::class.java).equalTo("id" ,user.id).findFirst()
            if (userOld != null) {
                userOld.name = user.name
                userOld.phone_number = user.phone_number
                result = true
            }
        }
        return result
    }

    suspend fun deleteUser(userId : String){
        realmInstance.executeTransactionAwait {
            val user = it.where(User::class.java).equalTo("id",userId).findFirst()
            user?.deleteFromRealm()
        }
    }

}
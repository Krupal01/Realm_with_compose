package com.example.realm_and_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realm_and_compose.dao.UserDao
import com.example.realm_and_compose.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {


    private val dao = UserDao()
    private val _users = MutableLiveData<List<User>>()
    val users = _users as  LiveData<List<User>>


    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        _users.value =  ArrayList(dao.getAllUsers())
    }

    fun addUser(user : User) {
        viewModelScope.launch {
            dao.addUser(user)
            getAllUsers()
        }
    }

    fun updateUser(user : User) : Boolean {
        var result = false
        viewModelScope.launch {
            result = dao.updateUser(user)
            getAllUsers()
        }
        return result
    }

    fun deleteUser(userId : String){
        viewModelScope.launch {
            dao.deleteUser(userId)
            withContext(Dispatchers.Main){
                getAllUsers()
            }
        }
    }

}
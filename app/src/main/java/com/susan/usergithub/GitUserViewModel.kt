package com.susan.usergithub

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.susan.usergithub.api.RetrofitUser
import com.susan.usergithub.model.GitUser
import com.susan.usergithub.model.GitUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitUserViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<GitUser>>()

    fun setSearchUsers(query: String) {
        RetrofitUser.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<GitUserResponse> {
                override fun onResponse(
                    call: Call<GitUserResponse>,
                    response: Response<GitUserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<GitUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getSearchUsers(): MutableLiveData<ArrayList<GitUser>> {
        return listUsers
    }
}
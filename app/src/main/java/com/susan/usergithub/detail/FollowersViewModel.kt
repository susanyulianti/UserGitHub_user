package com.susan.usergithub.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.susan.usergithub.api.RetrofitUser
import com.susan.usergithub.model.GitUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<GitUser>>()

    fun setListFollowers(username: String) {
        RetrofitUser.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<GitUser>> {
                override fun onResponse(
                    call: Call<ArrayList<GitUser>>,
                    response: Response<ArrayList<GitUser>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<GitUser>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<GitUser>> {
        return listFollowers
    }
}
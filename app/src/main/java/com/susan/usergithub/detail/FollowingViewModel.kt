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

class FollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<GitUser>>()

    fun setListFollowing(username: String) {
        RetrofitUser.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<GitUser>> {
                override fun onResponse(
                    call: Call<ArrayList<GitUser>>,
                    response: Response<ArrayList<GitUser>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<GitUser>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<GitUser>> {
        return listFollowing
    }
}
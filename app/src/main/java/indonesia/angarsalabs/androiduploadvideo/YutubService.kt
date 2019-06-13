/*
 *  Creator: Angga Arya Saputra on 6/13/19 9:23 PM Last modified: 6/13/19 9:22 PM
 *  Copyright: All rights reserved Ⓒ 2019 Angarsa Labs
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *  either express or implied. See the License for the specific language
 *   governing permissions and limitations under the License.
 *
 *
 */

package indonesia.angarsalabs.androiduploadvideo

import android.util.Log
import indonesia.angarsalabs.androiduploadvideo.model.Response
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class YutubService {
    companion object {
        private fun getAPI(): YutubAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(YutubAPI.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(YutubAPI::class.java)
        }

        private fun getAPI2(): YutubAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(YutubAPI.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return retrofit.create(YutubAPI::class.java)
        }

        fun signIn(email: String, password: String, callback: YutubCallback<Response>) {
            getAPI().signInAPI(email, password).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.d("EEEE", t.message)
                    callback.onFailed("tut")
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    Log.d("content", response.toString())
                    response.body()?.let {
                        callback.onSuccess(it)
                    } ?: kotlin.run {
                        Log.d("EEEE", response.toString())
                        callback.onFailed("tut")
                    }
                }

            })
        }

        fun uploadVideos(token: String, video: File, title: String, desc: String, callback: YutubCallback<String>) {
            val videoR = RequestBody.create(MediaType.parse("video/*"), video)
            val titleR = RequestBody.create(MediaType.parse("text/plain"), title)
            val descR = RequestBody.create(MediaType.parse("text/plain"), desc)


            val body = MultipartBody.Builder()
                .addFormDataPart("video", video.name, videoR)
                .build()

            Log.d("ERR-", body.boundary())

            getAPI2().uploadVideosAPI(
                videoR, titleR, descR, token
            ).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.message?.let { callback.onFailed(it) }
                }

                override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                    response.body()?.let { callback.onSuccess(it) }
                }

            })


        }

    }
}
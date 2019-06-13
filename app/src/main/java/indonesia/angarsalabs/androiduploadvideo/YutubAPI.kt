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

import indonesia.angarsalabs.androiduploadvideo.model.Response
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface YutubAPI {
    companion object {
        val URL_BASE = "https://yutub-api.herokuapp.com/"
    }

    @Multipart
    @POST("/videos")
    fun uploadVideosAPI(@Part("video\"; filename=\"vid.mp4\" ") video: RequestBody,
                        @Part("title") title: RequestBody,
                        @Part("description") desc: RequestBody,
                        @Header("x-access-token") token: String) : Call<String>

    @FormUrlEncoded
    @POST("users/login")
    fun signInAPI(@Field("email") email: String, @Field("password") password: String) : Call<Response>


}
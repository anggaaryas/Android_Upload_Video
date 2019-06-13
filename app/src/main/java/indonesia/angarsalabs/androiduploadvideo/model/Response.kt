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

package indonesia.angarsalabs.androiduploadvideo.model

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("auth")
    val auth: Boolean? = null,

    @field:SerializedName("token")
    val token: String? = null
)
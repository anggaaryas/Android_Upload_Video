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

import android.content.Context
import android.content.SharedPreferences

class SharedPref(val context: Context, val mode: ModePreferences) {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor

    private val NAME_VIDEO = "nameVid"
    private val IS_LOGED = "isLoged"
    private val TOKEN = "token"

    init {
        pref = context.getSharedPreferences(mode.value, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    companion object {
        enum class ModePreferences(val value: String) {
            CURRENT_VIDEO("currentVid"),
            ACCOUNT("account")
        }
    }

    fun checkAccount(): Boolean {
        if (mode.value == Companion.ModePreferences.ACCOUNT.value)
            return pref.getBoolean(IS_LOGED, false)
        else {
            throw Throwable("Shared preference not access Account")
        }

    }

    fun setAccount(login: Boolean){
        if (mode.value == Companion.ModePreferences.ACCOUNT.value) {
            editor.putBoolean(IS_LOGED, login)
            editor.commit()
        }
        else {
            throw Throwable("Shared preference is not accessing Account")
        }
    }

    fun setToken(token: String){
        if (mode.value == Companion.ModePreferences.ACCOUNT.value) {
            editor.putString(TOKEN, token)
            editor.commit()
        }
        else {
            throw Throwable("Shared preference is not accessing Account")
        }
    }

    fun getToken() : String{
        if (mode.value == Companion.ModePreferences.ACCOUNT.value)
            return pref.getString(TOKEN, "")
        else {
            throw Throwable("Shared preference is not accessing Account")
        }
    }


}
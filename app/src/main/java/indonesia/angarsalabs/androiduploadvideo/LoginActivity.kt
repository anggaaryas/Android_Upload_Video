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

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import indonesia.angarsalabs.androiduploadvideo.model.Response
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), YutubCallback<Response> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_login.setOnClickListener {
            YutubService.signIn(et_email.text.toString() , et_password.text.toString(), this)
        }
    }

    override fun onSuccess(x: Response) {
        val sp = SharedPref(this, SharedPref.Companion.ModePreferences.ACCOUNT)
        sp?.let {
            it.setAccount(true)
            it.setToken(x.token!!)
        }

        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    override fun onFailed(message: String) {
        Toast.makeText(this, "GAGAL LOGIN", Toast.LENGTH_SHORT).show()
    }

}

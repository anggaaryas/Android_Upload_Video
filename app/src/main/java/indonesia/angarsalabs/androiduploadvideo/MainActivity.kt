/*
 *  Creator: Angga Arya Saputra on 6/13/19 9:23 PM Last modified: 6/13/19 9:23 PM
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

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.media.ThumbnailUtils
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionRequest
import android.Manifest.permission
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.READ_CONTACTS
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var selectedVideoPath: String? = ""
    private val INTENT_VIDEO: Int = 900

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dexter.withActivity(this)
            .withPermissions(
                permission.READ_EXTERNAL_STORAGE,
                permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    ifGranted()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    Toast.makeText(applicationContext,"Gagal Izin", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }).check()

    }

    private fun ifGranted() {
        btn_openFile.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                ), INTENT_VIDEO
            )
        }

        btn_upload.setOnClickListener {
            when {
                selectedVideoPath.equals("") -> toast("Select the video first").show()
                et_title.text.toString().equals("") -> et_title.error = "Isi dulu"
                et_desc.text.toString().equals("") -> et_desc.error = "isi dulu"

                else -> {
                    uploadVideo()
                }
            }

        }
    }

    private fun uploadVideo() {
        val shared = SharedPref(this, SharedPref.Companion.ModePreferences.ACCOUNT)
        YutubService.uploadVideos(shared.getToken(), File(selectedVideoPath),
            et_title.text.toString(), et_desc.text.toString(), object : YutubCallback<String> {
                override fun onSuccess(message: String) {
                    finish()
                    Log.d("ERR-", message)
                }

                override fun onFailed(message: String) {

                    toast("FAILED").show()
                    Log.d("ERR-", message)
                }

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTENT_VIDEO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    selectedVideoPath = getPath(data.getData())
                }
                try {
                    if (selectedVideoPath == null) {
                        Log.e("ERR-", "selected video path = null!")
                        finish()
                    } else {
                        /**
                         * try to do something there
                         * selectedVideoPath is path to the selected video
                         */

                        getVideo()
                    }
                } catch (e: IOException) {
                    //#debug
                    e.printStackTrace()
                }

            }
        }
    }

    private fun getVideo() {
        tv_judulVideo.text = selectedVideoPath


        var bMap = ThumbnailUtils.createVideoThumbnail(
            selectedVideoPath,
            MediaStore.Video.Thumbnails.MICRO_KIND
        )
        iv_thumbnail.setImageBitmap(bMap)
    }

    private fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } else
            return null
    }


}

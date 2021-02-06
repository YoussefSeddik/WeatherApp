package com.example.weather_app.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.weather_app.R
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    const val FULL_DATE_FORMAT = "dd-MMM-yy hh.mm aa"

    fun showLoadingDialog(context: Context, withOutShadow: Boolean = true): AlertDialog? {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        val builder = context.let { it1 ->
            AlertDialog.Builder(it1).setView(mDialogView)
        }.show()
        builder.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (withOutShadow) {
                clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
        return builder
    }

    fun fullScreen(activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun String?.getImageIcon(): Int {
        return when (this) {
            "Thunderstorm" -> R.drawable.ic_atmosphere
            "Drizzle" -> R.drawable.ic_drizzle
            "Rain" -> R.drawable.ic_rain
            "Snow" -> R.drawable.ic_snow
            "Atmosphere" -> R.drawable.ic_atmosphere
            "Clear" -> R.drawable.ic_clear
            "Clouds" -> R.drawable.ic_cloudy
            "Extreme" -> R.drawable.ic_extreme
            else -> R.mipmap.ic_launcher
        }
    }

    fun longToString(timeInMillis: Long, dateFormat: String): String {
        val simpleDatFormat = SimpleDateFormat(dateFormat, Locale.US)
        return simpleDatFormat.format(Date(timeInMillis)).toString()
    }

    fun createImageFile(context: Context): File? {
        val fileFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
        val fileExtension = "jpg"
        val timeStamp: String = SimpleDateFormat(fileFormat, Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".$fileExtension", storageDir)
    }

    fun createFileFromBitmap(context: Context, bitmap: Bitmap): File? {
        val fileFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
        val timeStamp: String = SimpleDateFormat(fileFormat, Locale.US).format(Date())
        val path: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(path, "WeatherStatus$timeStamp.jpg")
        var fileOutPutStream: FileOutputStream? = null
        try {
            fileOutPutStream = FileOutputStream(file)
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                85,
                fileOutPutStream
            )
        } catch (e: FileNotFoundException) {
            Log.e(
                "Elgoe",
                e.localizedMessage ?: ""
            )
        } finally {
            if (fileOutPutStream != null) {
                try {
                    fileOutPutStream.flush()
                    fileOutPutStream.getFD().sync()
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    Log.e(
                        "Elgoe",
                        e.localizedMessage ?: ""
                    )
                }
            }
        }
        MediaStore.Images.Media.insertImage(
            context.contentResolver,
            file.absolutePath,
            file.name,
            file.name
        )
        return file
    }

    fun shareToFaceBook(activity: Activity, path: String) {
        val imageUri: Uri = FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.provider",
            File(path)
        )
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.type = "image/*"
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val matches: List<ResolveInfo> = activity.packageManager
            .queryIntentActivities(sendIntent, PackageManager.MATCH_DEFAULT_ONLY)
        var faceBookExist = false
        for (info in matches) {
            if (info.activityInfo.name.contains("facebook")) {
                sendIntent.setPackage(info.activityInfo.packageName)
                faceBookExist = true
                break
            }
        }
        if (faceBookExist) {
            activity.startActivity(sendIntent)
        } else {
            openGooglePlayLink(activity,"com.facebook.katana")
        }
    }

    fun shareToTwitter(activity: Activity, path: String) {
        val imageUri: Uri = FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.provider",
            File(path)
        )
        val tweetIntent = Intent(Intent.ACTION_SEND)
        tweetIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        tweetIntent.type = "image/jpeg"

        val matches: List<ResolveInfo> = activity.packageManager
            .queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY)
        var twitterExist = false
        for (info in matches) {
            if (info.activityInfo.name.contains("twitter")) {
                tweetIntent.setPackage(info.activityInfo.packageName)
                twitterExist = true
                break
            }
        }
        if (twitterExist) {
            activity.startActivity(tweetIntent)
        } else {
            openGooglePlayLink(activity,"com.twitter.android")
        }
    }

    private fun openGooglePlayLink(activity: Activity,packageName:String) {
        try {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    fun getRandomString(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }


}
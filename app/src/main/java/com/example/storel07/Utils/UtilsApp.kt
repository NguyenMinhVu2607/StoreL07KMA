package com.example.storel07.Utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.storel07.BuildConfig
import com.example.storel07.R


object UtilsApp {

    const val SHARE_EMAIL = "vubk34@gmail.com"

    const val LINK_PRIVACY = "https://sites.google.com/view/ppeditoroffice/home"

    fun sendFeedback(activity: Activity) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto: " + SHARE_EMAIL)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack Document Reader")
        activity.startActivity(
            Intent.createChooser(
                emailIntent,
                activity.getString(R.string.dm_send_email)
            )
        )
    }

//    fun showRateIfNeed(activity: Activity) {
//        FiveStarsDialog.COUNT_SHOW_RATE++
//        if (FiveStarsDialog.COUNT_SHOW_RATE === 1 || FiveStarsDialog.COUNT_SHOW_RATE % 3 === 0) FiveStarsDialog.showDialogRateApp(
//            activity, 0, SHARE_EMAIL, "This is title app: Text On...."
//        )
//    }

    fun openPrivacyPolicy(activity: Activity) {
        val url = LINK_PRIVACY
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)
    }

    fun share(activity: Activity) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
            var shareMessage = "\nLet me recommend you this application\n\n"
            shareMessage =
                """
        ${shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID}""".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }
}
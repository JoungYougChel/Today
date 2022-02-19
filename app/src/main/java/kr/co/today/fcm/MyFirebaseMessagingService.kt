package kr.co.today.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.co.today.App
import kr.co.today.R
import kr.co.today.server.HttpAppClient

//import io.ohmyappv2.ui.LoadingActivity
//import io.ohmyappv2.ui.main.MainActivity



class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val appOption = App.appOptions
    private var channelId = "default"
    private var channelName = "Channel human readable title"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
//            sendNotification(remoteMessage.data["pushType"].toString(),remoteMessage.data["pushMsg"].toString(),remoteMessage.data["pushTitle"].toString())
            channelName = remoteMessage.data["pushType"].toString()
            appOption.pushCnt = appOption.pushCnt!!+1
            when(remoteMessage.data["pushType"].toString()){
                "challenge" ->{
                    if (appOption.isPushChallenge!!) sendNotification(remoteMessage.data["pushType"].toString(),remoteMessage.data["pushMsg"].toString(),remoteMessage.data["pushTitle"].toString(),remoteMessage.data["image"].toString())
                }
                "service" ->{
                    if (appOption.isPushService!!) sendNotification(remoteMessage.data["pushType"].toString(),remoteMessage.data["pushMsg"].toString(),remoteMessage.data["pushTitle"].toString(),remoteMessage.data["image"].toString())
                }
                "marketing" ->{
                    if (appOption.isPushMarketing!!) sendNotification(remoteMessage.data["pushType"].toString(),remoteMessage.data["pushMsg"].toString(),remoteMessage.data["pushTitle"].toString(),remoteMessage.data["image"].toString())
                }
                else ->{
                    sendNotification(remoteMessage.data["pushType"].toString(),remoteMessage.data["pushMsg"].toString(),remoteMessage.data["pushTitle"].toString(),remoteMessage.data["image"].toString())
                }
            }

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }


    }

    private fun sendNotification(pushType: String, pushMsg: String, title: String,imageUrl:String) {
//        val intent =
//            if ((appOption.isAutoLogin!! || appOption.isSocialAutoLogin!!) || appOption.token!!.isNotEmpty()){
//                Intent(this, MainActivity::class.java)
//            }else{
//                Intent(this, LoadingActivity::class.java)
//            }
//        if ((appOption.isAutoLogin!! || appOption.isSocialAutoLogin!!) || appOption.token!!.isNotEmpty()){
//            appOption.isGetPush = true
//        }
//        intent.putExtra("pushType",pushType)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
//        val futureTarget = Glide.with(this)
//            .asBitmap()
//            .load(
//                if (imageUrl == "null"){
//                    R.mipmap.ic_launcher_foreground
//                }else{
//                    if (imageUrl.contains("http"))imageUrl
//                    else "${HttpAppClient.SCHEME + BuildConfig.BaseUrl}${imageUrl}"
//                }
//            )
//            .submit()
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_IMMUTABLE)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//        try {
//            notificationBuilder
//                .setContentTitle(title)
//                .setContentText(pushMsg)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.mipmap.ic_launcher_foreground)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setLargeIcon(
//
//                    futureTarget.get()
//                )
//        }catch (e:Exception){
//            notificationBuilder
//                .setContentTitle(title)
//                .setContentText(pushMsg)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.mipmap.ic_launcher_foreground)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//        }
//
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        val channel = NotificationChannel(channelId,
//            channelName,
//            NotificationManager.IMPORTANCE_HIGH)
//        notificationManager.createNotificationChannel(channel)
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }

    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance().beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     *
     *
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    companion object {
        private const val TAG = "MyFirebaseMessagingService"
    }

}
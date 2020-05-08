package ir.mortezarastgoo.androidtvtools.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.*
import android.graphics.Color
import android.net.VpnService
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import ir.mortezarastgoo.androidtvtools.R


/**
 * @author : Morteza Rastgoo
 * @since : 8/5/2020 AD, Fri
 **/
class BackgroundService : Service() {

    //    private val myBroadCastReciever = MyBroadCastReciever()
    private var myBroadCastReciever: BroadcastReceiver? = null


    override fun onCreate() {
        super.onCreate()

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("VPN Service", "VPN Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId )
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(1, notification)
        Log.d("BackgroundService", "registerReceiver");
        myBroadCastReciever = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent!!.action == Intent.ACTION_SCREEN_OFF) {
                    Log.d("broadcast", "ACTION_SCREEN_OFF")

                    val vpnService = VpnService.prepare(applicationContext)
                    if (vpnService != null) {
                        startActivity(vpnService)
                    }
//                    startService(getServiceIntent().setAction(ToyVpnService.ACTION_CONNECT));
//                    startService(getServiceIntent().setAction(ToyVpnService.ACTION_DISCONNECT));

                    //DO HERE
                } else if (intent.action == Intent.ACTION_SCREEN_ON) {
                    Log.d("broadcast", "ACTION_SCREEN_ON")
                    //DO HERE
                }
            }
        }
        registerReceiver(
            myBroadCastReciever,
            IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_OFF)
                addAction(Intent.ACTION_SCREEN_ON)
            })

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun startService(service: Intent?): ComponentName? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(service)
        } else {
            startServiceSuper(service)
        }
        return super.startService(service)
    }

    private fun startServiceSuper(service: Intent?): ComponentName? {
        return super.startService(service)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadCastReciever)
    }


    private fun getServiceIntent(): Intent {
        return Intent(this, ToyVpnService::class.java)
    }
}
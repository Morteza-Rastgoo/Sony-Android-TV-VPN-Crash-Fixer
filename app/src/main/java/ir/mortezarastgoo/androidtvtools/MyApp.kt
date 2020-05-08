package ir.mortezarastgoo.androidtvtools

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.Build
import ir.mortezarastgoo.androidtvtools.service.BackgroundService


/**
 * @author : Morteza Rastgoo
 * @since : 8/5/2020 AD, Fri
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val serviceIntent =
            Intent(applicationContext, BackgroundService::class.java)
        startServiceCompat(serviceIntent)

       /* val intent = VpnService.prepare(applicationContext)
        if (intent != null) {
            startActivity(intent)
        }*/
    }
}

fun Context.startServiceCompat(intent: Intent){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}
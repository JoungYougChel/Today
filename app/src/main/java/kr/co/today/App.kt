package kr.co.today

import android.app.Application
import com.iamport.sdk.domain.core.Iamport
import com.jakewharton.threetenabp.AndroidThreeTen
import kr.co.today.model.db.OhmyAppDatabase

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)
        appOptions = AppOptions(this)
        db = OhmyAppDatabase.getInstance(this, false)

        //아임포트
        Iamport.create(this)
    }

    companion object {
        const val TAG = "App"

        lateinit var instance: App
        lateinit var db: OhmyAppDatabase
        lateinit var appOptions: AppOptions
    }
}
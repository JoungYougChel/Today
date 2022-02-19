package kr.co.today

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.today.rx.AutoDisposable
import kr.co.today.rx.addTo
import kr.co.today.server.HttpAppClient
import kr.co.today.utils.initActivityBinding
import kr.co.today.utils.showAlertDialog

class LoadingActivity : AppCompatActivity() {
    private lateinit var appOption: AppOptions
    private val disposable = AutoDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityBinding(fullScreen = false, hasToolbar = false)
        disposable.bindTo(lifecycle)


    }

    override fun onResume() {
        this.appOption = App.appOptions
        GlobalScope.launch {
//            delay(2500)
            startProcess()


        }
        super.onResume()
    }

    private fun startProcess(){

        //앱버전, os버전 저장
        appOption.appVersion = BuildConfig.VERSION_NAME
        appOption.osVersion = android.os.Build.VERSION.SDK_INT


        HttpAppClient.setUrl("apis.data.go.kr/")
        moveToMain()
    }

    //메인 화면으로 이동
    private fun moveToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}


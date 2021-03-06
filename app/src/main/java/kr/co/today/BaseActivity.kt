package kr.co.today

//import com.kakao.sdk.common.KakaoSdk
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.today.rx.AutoDisposable
import kr.co.today.rx.addTo
import kr.co.today.server.HttpAppClient
import kr.co.today.utils.Interaction
import kr.co.today.utils.LongInteraction
import kr.co.today.utils.showAlertDialogNoTitle
import org.json.JSONObject

open class BaseActivity: AppCompatActivity(), Interaction, LongInteraction {
    val disposable = AutoDisposable()
    val appOption = App.appOptions
    val db = App.db

    var mLastClickTime : Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        disposable.bindTo(lifecycle)

        if(!HttpAppClient.checkAppRetrofitInterfaceInit()){
            triggerRestart(this)
        }
    }

    override fun onResume() {
        super.onResume()

        mLastClickTime = 0L
    }

    override fun onPause() {
        val focusView: View? = currentFocus
        if (focusView != null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusView.windowToken,0)
            focusView.clearFocus()
        }
        super.onPause()
    }

    private fun triggerRestart(context: Context) {
        val intent = Intent(context, LoadingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
        Runtime.getRuntime().exit(0)
    }
    
    override fun onClick(v: View?) {}
    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    // ?????? ?????? ?????? ??????
    fun checkOverlap() : Boolean {
        // ????????????
        val currentClickTime = SystemClock.uptimeMillis()
        // ???????????? - ????????? ????????????
        val elapsedTime = currentClickTime - mLastClickTime
        // ????????? ??????????????? ?????????????????? ??????
        mLastClickTime = currentClickTime

        // ?????? ?????? ?????? ????????? ???????????? ??????
        if (elapsedTime > BUTTON_SKIP_DURATION) {
            return false
        }
        // true??? ?????? ?????? ??????
        return true
    }



    private fun callWeb(url: String){
        if (!url.contains("http")){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://$url")))
        }else{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    // ?????????
    private fun showAlert(msg: String) {

        this.let {
            showAlertDialogNoTitle(it, msg)
                    .toObservable()
                    .subscribe()
                    .addTo(disposable)
        }

    }


}
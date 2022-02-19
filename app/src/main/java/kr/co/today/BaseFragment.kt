package kr.co.today

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.sendbird.uikit.SendBirdUIKit
import com.sendbird.uikit.adapter.SendBirdUIKitAdapter
import com.sendbird.uikit.interfaces.UserInfo
import kr.co.today.rx.AutoDisposable
import kr.co.today.utils.Interaction
import org.json.JSONObject
import kotlin.random.Random

open class BaseFragment: Fragment(), Interaction {
    val disposable = AutoDisposable()
    var mLastClickTime : Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable.bindTo(lifecycle)
        val idList = listOf("흑우_","코인_","아저씨_")



    }

    override fun onResume() {
        super.onResume()

        mLastClickTime = 0L
    }

    override fun onClick(v: View?) {}

    // 화면 중복 클릭 방지
    fun checkOverlap() : Boolean {
        // 현재시간
        val currentClickTime = SystemClock.uptimeMillis()
        // 현재시간 - 마지막 클릭시간
        val elapsedTime = currentClickTime - mLastClickTime
        // 마지막 클릭시간을 현재시간으로 변경
        mLastClickTime = currentClickTime

        // 버튼 클릭 간격 이상의 경우에만 실행
        if (elapsedTime > BUTTON_SKIP_DURATION) {
            return false
        }
        // true의 경우 실행 방지
        return true
    }




}
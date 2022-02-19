package kr.co.today

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.today.rx.AutoDisposable
import kr.co.today.rx.addTo
import kr.co.today.server.TempAppClient
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var appOption: AppOptions
    private val disposable = AutoDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable.bindTo(lifecycle)
        TempAppClient.setUrl("apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/")
        TempAppClient.getAppInstance().tempRecipeData().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
//                    Log.e(TAG,result.toString())
                    try {
                        val dataList = ((result["body"] as Map<*,*>)["items"] as List<Map<*, *>>)
                        dataList.forEach {
                            Log.e(TAG,it.toString())
                        }
                    }catch (e:Exception){

                    }
                },
                onError ={
                    Log.e(TAG,it.stackTraceToString())
                    Toast.makeText(this, "서버 통신 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            ).addTo(disposable)

        setContentView(R.layout.activity_main)
    }

    companion object{
        const val TAG = "LoadingActivity"
    }
}
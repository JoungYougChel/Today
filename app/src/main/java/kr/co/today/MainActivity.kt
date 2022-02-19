package kr.co.today

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.jakewharton.rxbinding3.material.itemSelections
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.today.databinding.ActivityMainBinding
import kr.co.today.rx.addTo
import kr.co.today.server.HttpAppClient
import kr.co.today.ui.foodinfo.FoodInfoFragment
import kr.co.today.ui.foodrecipe.FoodRecipeFragment
import kr.co.today.utils.initActivityBinding
import kr.co.today.utils.showToast

class MainActivity : BaseActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private var backKeyPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivityBinding(hasToolbar = false)

        setNavigation()
    }

    override fun onResume() {
        super.onResume()

    }


    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showToast(this, resources.getString(R.string.alert_exit))
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            setResult(Activity.RESULT_CANCELED,intent)
            finish()
        }
    }

    private fun setNavigation(){
        Log.e(TAG,R.id.navInfo.toString())
        binding.navBottom.itemSelections()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e(TAG,it.itemId.toString())
                Log.e(TAG,R.id.navInfo.toString())
                when(it.itemId){
                    R.id.navInfo ->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer,FoodInfoFragment.newInstance())
                            .commit()
                    }
                    R.id.navRecipe ->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer,FoodRecipeFragment.newInstance())
                            .commit()
                    }
                }
            }.addTo(disposable)
    }

    companion object{
        const val TAG = "MainActivity"
    }
}
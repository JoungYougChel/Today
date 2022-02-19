package kr.co.today.ui.foodrecipe

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kr.co.today.BaseActivity
import kr.co.today.R
import kr.co.today.databinding.ActivityFoodRecipeDetailBinding
import kr.co.today.utils.initActivityBinding

class FoodRecipeDetailActivity : BaseActivity() {
    private var mBinding: ActivityFoodRecipeDetailBinding? = null
    private val binding get() = mBinding!!
    private lateinit var item:Map<*,*>
    private var backKeyPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFoodRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivityBinding(hasToolbar = false)
        item = intent.getSerializableExtra("item") as Map<*, *>
        initUI()
    }

    private fun initUI(){
        Glide.with(this).load(item["ATT_FILE_NO_MK"].toString()).centerCrop().into(binding.ivThumbnail)
        binding.tvTitle.textSize = 20f
        binding.tvTitle.typeface = resources.getFont(R.font.pretendard_bold)
        binding.tvTitle.text = item["RCP_NM"].toString()
        if (item["RCP_PAT2"] != null && item["RCP_PAT2"].toString().isNotEmpty()){
            binding.tvRcpPat2.visibility = View.VISIBLE
            binding.tvRcpPat2.text = item["RCP_PAT2"].toString()
        }else{
            binding.tvRcpPat2.visibility = View.GONE
        }
        if (item["RCP_WAY2"] != null && item["RCP_WAY2"].toString().isNotEmpty()){
            binding.tvRcpWay2.visibility = View.VISIBLE
            binding.tvRcpWay2.text = item["RCP_WAY2"].toString()
        }else{
            binding.tvRcpWay2.visibility = View.GONE
        }
        if (item["HASH_TAG"] != null && item["HASH_TAG"].toString().isNotEmpty()){
            binding.tvHashTag.visibility = View.VISIBLE
            binding.tvHashTag.text = item["HASH_TAG"].toString()
        }else{
            binding.tvHashTag.visibility = View.GONE
        }

        binding.tvMake.text = item["RCP_PARTS_DTLS"].toString()

        try {
            val list = listOf<Map<*,*>>(
                mapOf("image" to item["MANUAL_IMG01"],"desc" to item["MANUAL01"]),
                mapOf("image" to item["MANUAL_IMG02"],"desc" to item["MANUAL02"]),
                mapOf("image" to item["MANUAL_IMG03"],"desc" to item["MANUAL03"]),
                mapOf("image" to item["MANUAL_IMG04"],"desc" to item["MANUAL04"]),
                mapOf("image" to item["MANUAL_IMG05"],"desc" to item["MANUAL05"]),
                mapOf("image" to item["MANUAL_IMG06"],"desc" to item["MANUAL06"]),
                mapOf("image" to item["MANUAL_IMG07"],"desc" to item["MANUAL07"]),
                mapOf("image" to item["MANUAL_IMG08"],"desc" to item["MANUAL08"]),
                mapOf("image" to item["MANUAL_IMG09"],"desc" to item["MANUAL09"]),
                mapOf("image" to item["MANUAL_IMG10"],"desc" to item["MANUAL10"]),
                mapOf("image" to item["MANUAL_IMG11"],"desc" to item["MANUAL11"]),
                mapOf("image" to item["MANUAL_IMG12"],"desc" to item["MANUAL12"]),
                mapOf("image" to item["MANUAL_IMG13"],"desc" to item["MANUAL13"]),
                mapOf("image" to item["MANUAL_IMG14"],"desc" to item["MANUAL14"]),
                mapOf("image" to item["MANUAL_IMG15"],"desc" to item["MANUAL15"]),
                mapOf("image" to item["MANUAL_IMG16"],"desc" to item["MANUAL16"]),
                mapOf("image" to item["MANUAL_IMG17"],"desc" to item["MANUAL17"]),
                mapOf("image" to item["MANUAL_IMG18"],"desc" to item["MANUAL18"]),
                mapOf("image" to item["MANUAL_IMG19"],"desc" to item["MANUAL19"]),
                mapOf("image" to item["MANUAL_IMG20"],"desc" to item["MANUAL20"]),
            )
            val viewAdapter = FoodRecipeMakeViewAdapter(this,list,this,disposable)
            val viewManager = LinearLayoutManager(this)
            binding.rvList.apply {
                adapter = viewAdapter
                layoutManager = viewManager
            }

        }catch (e:Exception){
            Log.e(TAG,e.stackTraceToString())
        }
    }

    companion object{
        const val TAG = "FoodRecipeDetailActivity"
    }
}
package kr.co.today.ui.foodrecipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.today.App
import kr.co.today.BaseFragment
import kr.co.today.MainActivity
import kr.co.today.databinding.FragmentFoodRecipeBinding
import kr.co.today.rx.addTo
import kr.co.today.server.HttpAppClient
import kr.co.today.utils.showAlertDialogNoTitle
import java.io.Serializable

class FoodRecipeFragment: BaseFragment() {

    private var mBinding: FragmentFoodRecipeBinding? = null
    private val binding get() = mBinding!!

    private val appOption = App.appOptions
    private lateinit var toolbarTitle:String
    private var list: MutableList<Map<*, *>> = mutableListOf()
    private lateinit var viewAdapter: FoodRecipeViewAdapter
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFoodRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    private fun initUI(){
        binding.rvList.setOnScrollChangeListener(object : RecyclerView.OnScrollListener(),
            View.OnScrollChangeListener {
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                binding.toolbar.toolbarTitle.alpha = ((scrollY * 0.01).toFloat())
                binding.toolbar.toolbarUnderLine.alpha = ((scrollY * 0.01).toFloat())
                if (!binding.rvList.canScrollVertically(-1)) {
                    binding.lyRefresh.isEnabled = true
                    //top
                } else if (!binding.rvList.canScrollVertically(1)) {
                    //bottom
                }else{
                    binding.lyRefresh.isEnabled = false
                }
            }
        })



         viewAdapter = FoodRecipeViewAdapter(requireActivity(),list,this,disposable)
         viewManager = LinearLayoutManager(requireActivity())
        binding.rvList.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
        searchDataList()

    }
    private fun searchDataList(){
        binding.loading.loading.visibility = View.VISIBLE
        HttpAppClient.getAppInstance().tempRecipeData().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
                    binding.loading.loading.visibility = View.GONE
//                    Log.e(TAG,result.toString())
                    try {
                        val dataList = ((result["COOKRCP01"] as Map<*,*>)["row"] as List<Map<*, *>>)
                        if (dataList.isNotEmpty()){

                            list.addAll(dataList)
                            viewAdapter.notifyDataSetChanged()
                        }
                    }catch (e:Exception){

                    }
                },
                onError ={
                    Log.e(MainActivity.TAG,it.stackTraceToString())
                    Toast.makeText(requireActivity(), "서버 통신 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            ).addTo(disposable)
    }


    override fun onRecipeItemClick(item: Map<*, *>) {
        super.onRecipeItemClick(item)
        val intent = Intent(requireActivity(),FoodRecipeDetailActivity::class.java)
        intent.putExtra("item",item as Serializable)
        startActivity(intent)
    }
    // 경고창
    private fun showAlert(msg: String) {

        requireActivity().let {
            showAlertDialogNoTitle(it, msg)
                .toObservable()
                .subscribe()
                .addTo(disposable)
        }

    }

    companion object{
        const val TAG = "FoodInfoFragment"
        @JvmStatic
        fun newInstance(): FoodRecipeFragment = FoodRecipeFragment()

    }
}
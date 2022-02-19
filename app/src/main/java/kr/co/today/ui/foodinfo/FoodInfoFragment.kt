package kr.co.today.ui.foodinfo

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
import kr.co.today.databinding.FragmentFoodInfoBinding
import kr.co.today.rx.addTo
import kr.co.today.server.HttpAppClient
import kr.co.today.utils.showAlertDialogNoTitle

class FoodInfoFragment: BaseFragment() {

    private var mBinding: FragmentFoodInfoBinding? = null
    private val binding get() = mBinding!!

    private val appOption = App.appOptions
    private lateinit var toolbarTitle:String
    private var list: MutableList<Map<*, *>> = mutableListOf()
    private lateinit var viewAdapter: FoodInfoViewAdapter
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFoodInfoBinding.inflate(inflater, container, false)
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



         viewAdapter = FoodInfoViewAdapter(requireActivity(),list,this,disposable)
         viewManager = LinearLayoutManager(requireActivity())
        binding.rvList.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
        binding.etSearch.textChanges().skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe {
            searchDataList()
        }.addTo(disposable)

    }
    private fun searchDataList(){
        HttpAppClient.getAppInstance().tempFoodData(
            "ytuls29FKLmnjkxuLv9KKVN0nCvyXe5v934wH9u6DLt7gcbkByqFiQa1c3owM4Be6zP+CFvjZSveVkL+oEx4Pg==",
            binding.etSearch.text.toString(),
            "2017",
            "1",
            "100",
            "json"
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
//                    Log.e(TAG,result.toString())
                    try {
                        val dataList = ((result["body"] as Map<*,*>)["items"] as List<Map<*, *>>)
                        if (dataList.isNotEmpty()){

                            list.clear()
                            list.addAll(dataList)
                            viewAdapter.notifyDataSetChanged()
                        }
                        dataList.forEach {
                            Log.e(MainActivity.TAG,it.toString())
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
//    private fun searchDataList(){
//        HttpAppClient.getAppInstance().tempRecipeData().observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .unsubscribeOn(Schedulers.io())
//            .subscribeBy(
//                onSuccess = { result ->
////                    Log.e(TAG,result.toString())
//                    try {
//                        val dataList = ((result["body"] as Map<*,*>)["items"] as List<Map<*, *>>)
//                        dataList.forEach {
//                            Log.e(MainActivity.TAG,it.toString())
//                        }
//                    }catch (e:Exception){
//
//                    }
//                },
//                onError ={
//                    Log.e(MainActivity.TAG,it.stackTraceToString())
//                    Toast.makeText(requireActivity(), "서버 통신 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
//                }
//            ).addTo(disposable)
//    }




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
        fun newInstance(): FoodInfoFragment = FoodInfoFragment()

    }
}
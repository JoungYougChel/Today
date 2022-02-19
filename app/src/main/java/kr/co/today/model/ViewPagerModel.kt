package kr.co.today.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewPagerModel: ViewModel() {
    private val _bannerItemList: MutableLiveData<List<String>> = MutableLiveData()
    private val _currentBannerPosition: MutableLiveData<Int> = MutableLiveData()

    val bannerItemList: LiveData<List<String>> get() = _bannerItemList
    val currentBannerPosition: LiveData<Int> get() = _currentBannerPosition

    init {
        _currentBannerPosition.value = 0
    }

    fun setBannerItem(list: List<String>){
        _bannerItemList.value = list
    }

    fun setCurrentBannerPosition(position: Int){
        _currentBannerPosition.value = position
    }

    fun getCurrentBannerPosition() = currentBannerPosition.value
}
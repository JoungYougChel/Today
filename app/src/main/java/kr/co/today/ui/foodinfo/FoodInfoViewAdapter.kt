package kr.co.today.ui.foodinfo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kr.co.today.R
import kr.co.today.databinding.ViewFoodInfoBinding
import kr.co.today.databinding.ViewFoodRepiceBinding
import kr.co.today.rx.AutoDisposable
import kr.co.today.rx.addTo
import kr.co.today.utils.Interaction

class FoodInfoViewAdapter(
    private val context: Context,
    private val list: List<Map<*,*>>,
    private val interaction: Interaction,
    private val disposable: AutoDisposable,
): RecyclerView.Adapter<FoodInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodInfoViewHolder =
            FoodInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.view_food_info, parent, false),context,interaction,disposable)

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: FoodInfoViewHolder, position: Int) {
        list.let { list ->
            holder.bind(list[position])
        }
    }
}

class FoodInfoViewHolder(itemView: View,private val context: Context,private val interaction: Interaction,private val disposable: AutoDisposable) : RecyclerView.ViewHolder(itemView){
    fun bind(item: Map<*,*>){
        val binding = ViewFoodInfoBinding.bind(itemView)
        binding.DESCKOR.text = item["DESC_KOR"].toString()
        binding.SERVINGWT.text = item["SERVING_WT"].toString()
        binding.NUTRCONT1.text = item["NUTR_CONT1"].toString()
        binding.NUTRCONT2.text = item["NUTR_CONT2"].toString()
        binding.NUTRCONT3.text = item["NUTR_CONT3"].toString()
        binding.NUTRCONT4.text = item["NUTR_CONT4"].toString()
        binding.NUTRCONT5.text = item["NUTR_CONT5"].toString()
        binding.NUTRCONT6.text = item["NUTR_CONT6"].toString()
        binding.NUTRCONT7.text = item["NUTR_CONT7"].toString()
        binding.NUTRCONT8.text = item["NUTR_CONT8"].toString()
        binding.NUTRCONT9.text = item["NUTR_CONT9"].toString()

        binding.ivDropDown.clicks().observeOn(AndroidSchedulers.mainThread()).subscribe {
            if ( binding.lyDetail.isVisible){
                binding.lyDetail.visibility = View.GONE
            }else{
                binding.lyDetail.visibility = View.VISIBLE
            }
        }.addTo(disposable)

    }
}


package kr.co.today.ui.foodrecipe

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
import kr.co.today.databinding.ViewFoodMakeBinding
import kr.co.today.databinding.ViewFoodRepiceBinding
import kr.co.today.rx.AutoDisposable
import kr.co.today.rx.addTo
import kr.co.today.utils.Interaction

class FoodRecipeMakeViewAdapter(
    private val context: Context,
    private val list: List<Map<*,*>>,
    private val interaction: Interaction,
    private val disposable: AutoDisposable,
): RecyclerView.Adapter<FoodRecipeMakeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecipeMakeViewHolder =
            FoodRecipeMakeViewHolder(LayoutInflater.from(context).inflate(R.layout.view_food_make, parent, false),context,interaction,disposable)

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: FoodRecipeMakeViewHolder, position: Int) {
        list.let { list ->
            holder.bind(list[position])
        }
    }
}

class FoodRecipeMakeViewHolder(itemView: View,private val context: Context,private val interaction: Interaction,private val disposable: AutoDisposable) : RecyclerView.ViewHolder(itemView){
    fun bind(item: Map<*,*>){
        val binding = ViewFoodMakeBinding.bind(itemView)

        if (item["image"] != null && item["image"].toString().isNotEmpty()){
            binding.ivThumbnail.visibility = View.VISIBLE
            Glide.with(context).load(item["image"].toString()).centerCrop().into(binding.ivThumbnail)
        }else{
            binding.ivThumbnail.visibility = View.GONE
        }

        if (item["desc"] != null && item["desc"].toString().isNotEmpty()){
            binding.tvDesc.visibility = View.VISIBLE
            binding.tvDesc.text = item["desc"].toString()
        }else{
            binding.tvDesc.visibility = View.GONE
        }


    }
}


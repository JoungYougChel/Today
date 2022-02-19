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
import kr.co.today.databinding.ViewFoodRepiceBinding
import kr.co.today.rx.AutoDisposable
import kr.co.today.rx.addTo
import kr.co.today.utils.Interaction

class FoodRecipeViewAdapter(
    private val context: Context,
    private val list: List<Map<*,*>>,
    private val interaction: Interaction,
    private val disposable: AutoDisposable,
): RecyclerView.Adapter<FoodRecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecipeViewHolder =
            FoodRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.view_food_repice, parent, false),context,interaction,disposable)

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: FoodRecipeViewHolder, position: Int) {
        list.let { list ->
            holder.bind(list[position])
        }
    }
}

class FoodRecipeViewHolder(itemView: View,private val context: Context,private val interaction: Interaction,private val disposable: AutoDisposable) : RecyclerView.ViewHolder(itemView){
    fun bind(item: Map<*,*>){
        val binding = ViewFoodRepiceBinding.bind(itemView)
        Glide.with(context).load(item["ATT_FILE_NO_MK"].toString()).centerCrop().into(binding.ivThumbnail)
        binding.lyLinear.background = ContextCompat.getDrawable(context,R.drawable.box_rounded_white)
        binding.lyLinear.clipToOutline = true
        binding.tvTitle.textSize = 20f
        binding.tvTitle.typeface = context.resources.getFont(R.font.pretendard_bold)
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


        itemView.clicks().observeOn(AndroidSchedulers.mainThread()).subscribe {
            interaction.onRecipeItemClick(item)
        }.addTo(disposable)

    }
}


package kr.co.today.utils

import android.view.View
import org.bson.Document

interface Interaction: View.OnClickListener{
    fun onRecipeItemClick(item: Map<*,*>){}
}

interface LongInteraction: View.OnLongClickListener{
}
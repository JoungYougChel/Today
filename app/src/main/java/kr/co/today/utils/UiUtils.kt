package kr.co.today.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kr.co.today.App
import kr.co.today.BUTTON_SKIP_DURATION
import kr.co.today.R
import java.util.concurrent.TimeUnit
import kr.co.today.databinding.LayoutToolbarBinding
import kr.co.today.databinding.DialogBinding

fun callDialog(
    context: Context,
    title: String?,
    message: String?,
    btnOk: String?,
    btnCancel: String?
) = Single.create<Boolean> { emitter ->
    val appOption = App.appOptions
    val dialog = AlertDialog.Builder(context).create()
    val edialog = LayoutInflater.from(context)
    val view = edialog.inflate(R.layout.dialog, null)
    val binding = DialogBinding.bind(view)
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    binding.tvDialogMessage.text = message
    if (title != null) binding.tbDialogTitle.text = title else binding.tbDialogTitle.visibility = View.GONE
    if (btnOk != null) binding.btnOk.text = btnOk else binding.btnOk.visibility = View.GONE
    if (btnCancel != null) binding.btnCancel.text = btnCancel else binding.btnCancel.visibility = View.GONE
    binding.tvDialogMessage.textSize = 17f
    binding.tvDialogMessage.typeface = context.resources.getFont(R.font.pretendard_medium)
    binding.tvDialogMessage.setTextColor(Color.parseColor(appOption.Base0))
    binding.btnCancel.textSize = 15f
    binding.btnCancel.typeface = context.resources.getFont(R.font.pretendard_medium)
    binding.btnCancel.setTextColor(Color.parseColor(appOption.BaseM1))
    binding.btnOk.textSize = 15f
    binding.btnOk.typeface = context.resources.getFont(R.font.pretendard_medium)
    binding.btnOk.setTextColor(Color.parseColor(appOption.Main))
    binding.btnOk.setOnClickListener {
        emitter.onSuccess(true)
        dialog.dismiss()
    }
    binding.btnCancel.setOnClickListener {
        emitter.onSuccess(false)
        dialog.dismiss()
    }
    dialog.setOnCancelListener {
        emitter.onSuccess(false)
        dialog.dismiss()
    }

    dialog.setView(view)
    dialog.show()
}

fun showAlertDialog(
    context: Context,
    title: String?,
    message: String?,
    button: String = "확인"
) = callDialog(context, title, message, button, null)

fun showAlertDialogNoTitle(
    context: Context,
    message: String?,
    button: String = "확인"
) = callDialog(context, null, message, button, null)

fun showConfirmDialog(
    context: Context,
    title: String?,
    message: String,
    positive: String = "예",
    negative: String = "아니오",
    cancellable: Boolean = true
) = callDialog(context, title, message, positive, negative)

@SuppressLint("CheckResult")
fun showToast(context: Context, message: String, short: Boolean = false) {
    Toast.makeText(context, message, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}
@SuppressLint("ResourceType")
fun AppCompatActivity.initActivityBinding(
    fullScreen: Boolean = false,
    hasToolbar: Boolean = true,
    toolbarBinding: LayoutToolbarBinding = LayoutToolbarBinding.inflate(
        layoutInflater
    )
) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//    window.navigationBarColor = ContextCompat.getColor(this, R.color.colorBg)

    // 스크린캡쳐 방지
//    if (App.appOptions.DISABLE_SCREENSHOT_CAPTURE) {
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )
//    }

    if (fullScreen) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    if(hasToolbar){
        toolbarBinding.btnBack.clicks()
            .throttleFirst(
                BUTTON_SKIP_DURATION,
                TimeUnit.MILLISECONDS,
                AndroidSchedulers.mainThread()
            )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onBackPressed()
            }
    }
}
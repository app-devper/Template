package com.devper.template.core.extension

import android.content.res.Resources
import android.graphics.Paint
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.devper.template.core.platform.helper.DecimalWatcher
import com.devper.template.core.platform.helper.MaskWatcher

fun TextView.applyColor(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan,
            startIndexOfLink,
            startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.toVisibleOrGone(isGone: Boolean) {
    if (isGone) toGone() else toVisible()
}

fun View.toEnable() {
    this.isEnabled = true
}

fun View.toDisable() {
    this.isEnabled = false
}

fun View.toEnableOrDisable(isEnableView: Boolean) {
    this.isEnabled = isEnableView
}

fun EditText.toText() = this.text.toString()

fun EditText.toDecimalFormat(format: String = "#,###,##0.00", digits: Int = 2) {
    val watcher = DecimalWatcher(
        this,
        format,
        digits
    )
    this.addTextChangedListener(watcher)
}

fun EditText.toLaserCode() {
    val watcher = MaskWatcher(
        this,
        "###-#######-####"
    )
    this.addTextChangedListener(watcher)
}

fun EditText.toTelNo() {
    val watcher =
        MaskWatcher(this, "##-###-####")
    this.addTextChangedListener(watcher)
}

fun EditText.toBankAcctNo() {
    val watcher = MaskWatcher(
        this,
        "###-#-#####-#"
    )
    this.addTextChangedListener(watcher)
}

fun EditText.toMobileNo() {
    val watcher = MaskWatcher(
        this,
        "###-###-####"
    )
    this.addTextChangedListener(watcher)
}

fun EditText.toCitizenId() {
    val watcher = MaskWatcher(
        this,
        "#-####-#####-##-#"
    )
    this.addTextChangedListener(watcher)
}

fun EditText.validateWhiteSpace() {
    var text = this.text.toString()
    if (text.contains(" ")) {
        text = text.replace("""\s""".toRegex(), "")
        this.setText(text)
        this.setSelection(text.length)
    }
}

fun EditText.toDecimalInput(format: String = "#,###,##0.00", digits: Int = 2) {
    val watcher = DecimalWatcher(
        this,
        format,
        digits
    )
    this.addTextChangedListener(watcher)
    this.onFocusChange { b ->
        val payAmount = this.toText().currencyToDouble()
        if (!b) {
            if (payAmount == 0.0) {
                this.setText("")
            } else {
                this.setText(payAmount.to2Digits())
            }
        } else {
            if (payAmount == 0.0) {
                this.setText("")
            } else {
                if (payAmount.isInteger()) {
                    this.setText(payAmount.toNoDigits())
                } else {
                    this.setText(payAmount.to2Digits())
                }
            }
        }
    }
}

inline fun EditText.onFocusChange(crossinline hasFocus: (Boolean) -> Unit) {
    onFocusChangeListener = View.OnFocusChangeListener { _, b -> hasFocus(b) }
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()



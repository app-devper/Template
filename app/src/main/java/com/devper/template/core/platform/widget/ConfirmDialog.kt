package com.devper.template.core.platform.widget

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.devper.template.R
import com.devper.template.databinding.DialogConfirmBinding

class ConfirmDialog (private val component: DslAlertComponent): DialogFragment() {

    private lateinit var binding: DialogConfirmBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, container, false)
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.tvTitle.text = component.title
        binding.tvMessage.text = component.description

        if (component.buttonCancelText.isEmpty()) {
            binding.lyCancel.gravity = Gravity.END
            binding.lyCancel.visibility = View.GONE
        } else {
            binding.btnCancel.text = component.buttonCancelText
        }

        if (component.buttonOkText.isNotEmpty()) {
            binding.btnConfirm.text = component.buttonOkText
        }

        binding.btnConfirm.setOnClickListener {
            component.actionClickConfirm.invoke()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            component.actionClickCancel.invoke()
            dismiss()
        }
    }

    class Builder {

        private var component = DslAlertComponent()

        fun withTitle(title: String): Builder {
            component = component.copy(title = title)
            return this
        }

        fun hasWarning(): Builder {
            component = component.copy(titleColor = R.color.black)
            return this
        }

        fun withDescription(description: String): Builder {
            component = component.copy(description = description)
            return this
        }

        fun withButtonConfirmText(text: String): Builder {
            component = component.copy(buttonOkText = text)
            return this
        }

        fun withButtonCancelText(text: String): Builder {
            component = component.copy(buttonCancelText = text)
            return this
        }

        fun withoutCancel(): Builder {
            component = component.copy(isAllowCancel = false)
            return this
        }

        fun withConfirm(action: () -> Unit): Builder {
            component = component.copy(actionClickConfirm = action)
            return this
        }

        fun withCancelAction(action: () -> Unit): Builder {
            component = component.copy(actionClickCancel = action)
            return this
        }

        fun build(): ConfirmDialog = ConfirmDialog(component)
    }


    data class DslAlertComponent(
        val title: String = "",
        val description: String = "",
        @ColorRes val titleColor: Int = R.color.black,
        val isAllowCancel: Boolean = true,
        val buttonOkText: String = "",
        val buttonCancelText: String = "",
        var actionClickConfirm:() -> Unit = {},
        val actionClickCancel:() -> Unit = {}
    )
}

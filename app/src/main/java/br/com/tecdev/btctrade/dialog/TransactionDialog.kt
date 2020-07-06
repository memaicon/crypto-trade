package br.com.tecdev.btctrade.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isEmpty
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.model.CoinType
import kotlinx.android.synthetic.main.transaction_dialog.*


class TransactionDialog(context: Context) : Dialog(context) {

    var onClickAccept: OnClickAccept? = null
    var onClickCancel: OnClickCancel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialog()
        setOnClick()
    }

    private fun initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.transaction_dialog)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }

    private fun setOnClick() {
        cancelButton.setOnClickListener {
            onClickCancel?.onClickCancel()
            cancel()
        }
        acceptButton.setOnClickListener {

            if (validate()) {
                onClickAccept?.onClickAccept(this)
            }

        }
    }

    fun validate() : Boolean {
        if (dialogAmount.text.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.dialog_amount_error) , Toast.LENGTH_SHORT).show()
            return false
        }

        if (dialogCoin.selectedItem.toString().isEmpty()) {
            Toast.makeText(context, context.getString(R.string.dialog_coin_error) , Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun setTitle(title: String) {
        titleText.text = title
    }

    fun setMessage(message: String) {
        messageText.text = message
    }

    fun setCoins() {
        val spinner = dialogCoin
        val items = CoinType.values()

        if (spinner != null) {
            val adapter = ArrayAdapter(context,
                android.R.layout.simple_spinner_item, items)
            spinner.adapter = adapter
        }
    }

    fun setPositiveButtonText(text: String) {
        acceptButton.text = text
    }

    fun setNegativeButtonText(text: String) {
        cancelButton.text = text
    }

    interface OnClickAccept {
        fun onClickAccept(dialog: TransactionDialog)
    }

    interface OnClickCancel {
        fun onClickCancel()
    }


    class Builder(val context: Context) {

        private var title = ""
        private var message = ""
        private var positiveButtonText = context.getString(R.string.dialog_accept)
        private var negativeButtonText = context.getString(R.string.dialog_cancel)
        var onClickAccept: OnClickAccept? = null
        var onClickCancel: OnClickCancel? = null

        fun setTitle(title: String) = apply {
            this.title = title
        }

        fun setMessage(message: String) = apply {
            this.message = message
        }

        fun setPositiveButtonText(text: String)  = apply {
            positiveButtonText = text
        }

        fun setNegativeButtonText(text: String)  = apply {
            negativeButtonText = text
        }


        inline fun setOnClickAccept(crossinline onClickAccept: (dialog: TransactionDialog) -> Unit) =
            apply {
                this.onClickAccept = object : OnClickAccept {
                    override fun onClickAccept(dialog: TransactionDialog) {
                        onClickAccept(dialog)
                    }
                }
            }

        inline fun setOnClickCancel(crossinline onClickCancel: () -> Unit) = apply {
            this.onClickCancel = object : OnClickCancel {
                override fun onClickCancel() {
                    onClickCancel()
                }
            }
        }

        fun show() {
            val dialog = TransactionDialog(context)
            dialog.show()
            dialog.setTitle(title)
            dialog.setMessage(message)
            dialog.setCoins()
            dialog.setPositiveButtonText(positiveButtonText)
            dialog.setNegativeButtonText(negativeButtonText)
            dialog.onClickAccept = onClickAccept
            dialog.onClickCancel = onClickCancel
        }
    }
}
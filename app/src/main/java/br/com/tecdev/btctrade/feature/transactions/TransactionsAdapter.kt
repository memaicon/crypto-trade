package br.com.tecdev.btctrade.feature.transactions

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.data.database.AppDatabase
import br.com.tecdev.btctrade.model.Transactions
import br.com.tecdev.btctrade.util.formatDateFromMillis
import br.com.tecdev.btctrade.util.formatNumber
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.util.*

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {

    var list: MutableList<Transactions>? = null
    var listSelected: Array<Boolean?>? = null
    var database: AppDatabase? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
    )

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        list?.get(position)?.run {
            listSelected?.get(position)?.let {
                holder.bind(this, it)
            }
        }
    }

    fun setData(list: MutableList<Transactions>) {
        listSelected = arrayOfNulls(list.size)
        Arrays.fill(listSelected, false)
        this.list = list
    }

    inner class TransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(transaction: Transactions, isSelected: Boolean) {
            itemView.apply {
                coinName.text = transaction.coin
                buyAmountText.text = transaction.amount.toString()
                buyDateText.text = formatDateFromMillis(transaction.date)

                val value = transaction.value
                val amount = transaction.amount
                val sellPrice = transaction.sell

                val result = (sellPrice * amount) - (value * amount)

                if (result > 0)
                    resultText.setTextColor(resources.getColor(R.color.green))
                else if (result < 0)
                    resultText.setTextColor(resources.getColor(R.color.red))

                resultText.text = resources.getString(R.string.currency, formatNumber(result))

                contentItemView.setBackgroundColor(
                    if (isSelected) ContextCompat.getColor(
                        context,
                        R.color.purpleAccentOpacity10
                    ) else Color.WHITE
                )
            }
        }
    }
}
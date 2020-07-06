package br.com.tecdev.btctrade.feature.transactions

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.dialog.TransactionDialog
import br.com.tecdev.btctrade.feature.coins.AllCoinsViewModel
import br.com.tecdev.btctrade.model.AllCoinsResponse
import br.com.tecdev.btctrade.model.Transactions
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_transactions.*
import kotlinx.android.synthetic.main.transaction_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class TransactionsActivity : AppCompatActivity() {

    private val viewModel: TransactionsViewModel by inject()
    private val transactionsAdapter: TransactionsAdapter by inject()
    private val viewCoinsModel: AllCoinsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_transactions)
        setViewModel()
        setOnClick()
        setRecycler()
    }

    private fun setViewModel() {
        viewModel.getTransactionsLiveData.observe(this, observerGetTransactions())
        viewModel.getAllTransactions()
    }

    private fun setOnClick() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        new_transaction.setOnClickListener {
            TransactionDialog.Builder(this)
                .setTitle(getString(R.string.new_transaction))
                .setMessage(getString(R.string.new_transaction_message))
                .setPositiveButtonText(getString(R.string.dialog_accept))
                .setOnClickAccept {

                    val dAmount: Double = it.dialogAmount.text.toString().toDouble()
                    val dCoin: String = it.dialogCoin.selectedItem.toString()

                    viewCoinsModel.getCoin(dCoin)
                    val coin = viewCoinsModel.getCoin

                    viewModel.insertTransactions(Transactions( 0, dCoin, coin.last().buy, dAmount , coin.last().sell, coin.last().date))

                    it.cancel()
                }.setOnClickCancel {

                }.show()
        }
    }

    private fun setRecycler() {
        allCoinsRecycler.adapter = transactionsAdapter
    }

    /** Observer **/

    private fun observerGetTransactions() = Observer<MutableList<Transactions>> {
        transactionsAdapter.setData(it)
        transactionsAdapter.notifyDataSetChanged()

        loadView.visibility = View.GONE
    }
}

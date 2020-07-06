package br.com.tecdev.btctrade.feature.transactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.model.Transactions
import kotlinx.android.synthetic.main.activity_transactions.*
import org.koin.android.ext.android.inject

class TransactionsActivity : AppCompatActivity() {

    private val viewModel: TransactionsViewModel by inject()
    private val transactionsAdapter: TransactionsAdapter by inject()

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

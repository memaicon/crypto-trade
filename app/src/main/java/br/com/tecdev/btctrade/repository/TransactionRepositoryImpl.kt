package br.com.tecdev.btctrade.repository

import android.content.Context
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.data.database.AppDatabase
import br.com.tecdev.btctrade.exception.NetworkException
import br.com.tecdev.btctrade.model.Transactions
import br.com.tecdev.btctrade.util.NetworkUtil

class TransactionRepositoryImpl(
    private val context: Context,
    private val database: AppDatabase
) : TransactionRepository {

    override suspend fun updateTransactionsData() {
        if (NetworkUtil.isOnline(context)) {

        } else {
            if (database.transactionsDao().getTransactions().toString().isEmpty()) {
                throw NetworkException(context.getString(R.string.network_error_message))
            }
        }
    }

    override suspend fun getAllTransactions(): MutableList<Transactions> {
        return database.transactionsDao().getTransactions()
    }

    override suspend fun insertTransactionData(transactions: MutableList<Transactions>) {
        database.transactionsDao().insertAll(transactions)
    }
}
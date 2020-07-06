package br.com.tecdev.btctrade.repository

import br.com.tecdev.btctrade.model.Transactions

interface TransactionRepository {

    suspend fun updateTransactionsData()

    suspend fun getAllTransactions(): MutableList<Transactions>

    suspend fun insertTransactionData(transactions: MutableList<Transactions>)

}
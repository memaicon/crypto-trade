package br.com.tecdev.btctrade.repository

import br.com.tecdev.btctrade.model.Transactions

interface TransactionRepository {

    suspend fun saveTransactionData()

    suspend fun getAllTransactions(): MutableList<Transactions>

}
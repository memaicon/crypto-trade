package br.com.tecdev.btctrade.feature.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tecdev.btctrade.model.Transactions
import br.com.tecdev.btctrade.repository.TransactionRepository
import com.crashlytics.android.Crashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    var getTransactionsLiveData = MutableLiveData<MutableList<Transactions>>()
    var insertTransactionData = mutableListOf<Transactions>()

    fun getAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allTransactions = transactionRepository.getAllTransactions()
                launch(Dispatchers.Main) {
                    getTransactionsLiveData.postValue(allTransactions)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }

    fun insertTransactions(transaction: Transactions) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                insertTransactionData.add(transaction)
                transactionRepository.insertTransactionData(insertTransactionData)
                insertTransactionData.remove(transaction)

                val allTransactions = transactionRepository.getAllTransactions()

                launch(Dispatchers.Main) {
                    getTransactionsLiveData.postValue(allTransactions)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }
}
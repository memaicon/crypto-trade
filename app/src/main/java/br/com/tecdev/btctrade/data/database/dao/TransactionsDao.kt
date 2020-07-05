package br.com.tecdev.btctrade.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.tecdev.btctrade.model.Transactions

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM Transactions")
    fun getTransactions(): Transactions

    @Query("SELECT * FROM Transactions WHERE coin LIKE '%' || :q || '%'")
    fun getTransactions(q : String): Transactions

    @Insert()
    fun insertAll(transaction: Transactions)

    @Query("DELETE FROM Transactions WHERE _id = :id")
    fun delete(id : Int)
}
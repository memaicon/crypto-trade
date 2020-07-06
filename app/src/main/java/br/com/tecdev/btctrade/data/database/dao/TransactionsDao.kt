package br.com.tecdev.btctrade.data.database.dao

import androidx.room.*
import br.com.tecdev.btctrade.model.Transactions

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM Transactions ORDER BY _id ASC")
    fun getTransactions(): MutableList<Transactions>

    @Query("SELECT * FROM Transactions WHERE coin LIKE '%' || :q || '%'")
    fun getTransactions(q : String): MutableList<Transactions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(transaction: MutableList<Transactions>)

    @Query("DELETE FROM Transactions WHERE _id = :id")
    fun delete(id : Int)

    @Query("DELETE FROM Transactions")
    fun deleteAll()
}
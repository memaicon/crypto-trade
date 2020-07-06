package br.com.tecdev.btctrade.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.tecdev.btctrade.model.AllCoinsResponse

@Dao
interface AllCoinsDao {

    @Query("SELECT * FROM AllCoinsResponse")
    fun getCoin(): MutableList<AllCoinsResponse>

    @Query("SELECT * FROM AllCoinsResponse WHERE coin LIKE '%' || :q || '%' LIMIT 1")
    fun getCoin(q : String): MutableList<AllCoinsResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(btc: MutableList<AllCoinsResponse>)

    @Query("DELETE FROM AllCoinsResponse")
    fun deleteAll()
}
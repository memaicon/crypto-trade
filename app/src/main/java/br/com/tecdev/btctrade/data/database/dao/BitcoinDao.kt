package br.com.tecdev.btctrade.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.tecdev.btctrade.model.MbtcResponse

@Dao
interface BitcoinDao {

    @Query("SELECT * FROM MbtcResponse")
    fun getMbtc(): MbtcResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(btc: MbtcResponse)

    @Query("DELETE FROM MbtcResponse")
    fun deleteAll()
}
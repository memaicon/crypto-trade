package br.com.tecdev.btctrade.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.tecdev.btctrade.BuildConfig
import br.com.tecdev.btctrade.data.database.dao.AllCoinsDao
import br.com.tecdev.btctrade.data.database.dao.BitcoinDao
import br.com.tecdev.btctrade.data.database.dao.TransactionsDao
import br.com.tecdev.btctrade.model.*

@Database(
    entities = [Transactions::class, AllCoinsResponse::class, MbtcResponse::class, TickerInfo::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(value = [TickerInfoConverter::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = BuildConfig.APPLICATION_ID
    }

    abstract fun bitcoinDao(): BitcoinDao
    abstract fun allCoinsDao(): AllCoinsDao
    abstract fun transactionsDao(): TransactionsDao
}
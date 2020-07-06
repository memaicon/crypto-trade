package br.com.tecdev.btctrade.repository

import android.content.Context
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.data.database.AppDatabase
import br.com.tecdev.btctrade.data.net.RestApi
import br.com.tecdev.btctrade.data.session.SessionManager
import br.com.tecdev.btctrade.exception.NetworkException
import br.com.tecdev.btctrade.model.AllCoinsResponse
import br.com.tecdev.btctrade.model.CoinType
import br.com.tecdev.btctrade.model.MbtcResponse
import br.com.tecdev.btctrade.util.NetworkUtil
import br.com.tecdev.btctrade.util.formatDateFromMillis

class MbtcRepositoryImpl(
    private val context: Context,
    private val restApi: RestApi,
    private val database: AppDatabase,
    private val sessionManager: SessionManager
) : MbtcRepository {

    override suspend fun saveMbtcData() {
        if (NetworkUtil.isOnline(context)) {

            val bitcoinResponse = restApi.getCoin(CoinType.BTC.name)

            database.bitcoinDao().deleteAll()
            database.bitcoinDao().insertAll(bitcoinResponse)

            val allCoins = mutableListOf<AllCoinsResponse>()

            enumValues<CoinType>().forEach {
                allCoins.add(AllCoinsResponse(
                    it.name,
                    restApi.getCoin(it.name).ticker.high,
                    restApi.getCoin(it.name).ticker.buy,
                    restApi.getCoin(it.name).ticker.sell,
                    restApi.getCoin(it.name).ticker.date
                ))
            }

            database.allCoinsDao().deleteAll()
            database.allCoinsDao().insertAll(allCoins)

            sessionManager.lastDateUpdate = formatDateFromMillis(bitcoinResponse.ticker.date)

        } else {
            if (database.bitcoinDao().getMbtc().toString().isEmpty()) {
                throw NetworkException(context.getString(R.string.network_error_message))
            }
        }
    }

    override suspend fun getBitcoin(): MbtcResponse {
        return database.bitcoinDao().getMbtc()
    }

    override suspend fun getAllCoins(): MutableList<AllCoinsResponse> {
        return database.allCoinsDao().getCoin()
    }

    override suspend fun getCoin(coin: String): MutableList<AllCoinsResponse> {
        return database.allCoinsDao().getCoin(coin)
    }

    override suspend fun getLastUpdate(): String {
        return sessionManager.lastDateUpdate
    }
}
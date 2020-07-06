package br.com.tecdev.btctrade.repository

import br.com.tecdev.btctrade.model.AllCoinsResponse
import br.com.tecdev.btctrade.model.MbtcResponse

interface MbtcRepository {

    suspend fun saveMbtcData()

    suspend fun getBitcoin(): MbtcResponse

    suspend fun getAllCoins(): MutableList<AllCoinsResponse>

    suspend fun getCoin(coin: String): MutableList<AllCoinsResponse>

    suspend fun getLastUpdate(): String
}
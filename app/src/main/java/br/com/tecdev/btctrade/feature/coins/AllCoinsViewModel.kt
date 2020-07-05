package br.com.tecdev.btctrade.feature.coins

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tecdev.btctrade.model.AllCoinsResponse
import com.crashlytics.android.Crashlytics
import br.com.tecdev.btctrade.model.MbtcResponse
import br.com.tecdev.btctrade.repository.MbtcRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllCoinsViewModel(private val btcRepository: MbtcRepository) : ViewModel() {

    var showMbtcLiveData = MutableLiveData<MbtcResponse>()

    var getCoinsLiveData = MutableLiveData<MutableList<AllCoinsResponse>>()

    var getLastUpdateLiveData = MutableLiveData<String>()

    fun getAllCois() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val mbtc = btcRepository.getBitcoin()
                val allCoins = btcRepository.getAllCoins()
                val lastUpdate = btcRepository.getLastUpdate()
                launch(Dispatchers.Main) {
                    showMbtcLiveData.postValue(mbtc)
                    getLastUpdateLiveData.postValue(lastUpdate)
                    getCoinsLiveData.postValue(allCoins)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }
}
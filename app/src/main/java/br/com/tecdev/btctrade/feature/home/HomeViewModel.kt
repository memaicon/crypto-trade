package br.com.tecdev.btctrade.feature.home

import android.app.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.tecdev.btctrade.feature.base.BaseViewModel
import br.com.tecdev.btctrade.model.MbtcResponse
import br.com.tecdev.btctrade.repository.MbtcRepository
import com.crashlytics.android.Crashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
    private val btcRepository: MbtcRepository
) : BaseViewModel(application) {

    var showMbtcLiveData = MutableLiveData<MbtcResponse>()

    fun getMbtc() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val mbtc = btcRepository.getBitcoin()
                launch(Dispatchers.Main) {
                    showMbtcLiveData.postValue(mbtc)
                }
            } catch (ex: Exception) {
                Crashlytics.logException(ex)
            }
        }
    }
}
package br.com.tecdev.btctrade.feature.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crashlytics.android.Crashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import br.com.tecdev.btctrade.BuildConfig
import br.com.tecdev.btctrade.feature.base.BaseViewModel
import br.com.tecdev.btctrade.repository.MbtcRepository
import br.com.tecdev.btctrade.repository.TransactionRepository
import br.com.tecdev.btctrade.util.getErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashViewModel(
    application: Application,
    private val btcRepository: MbtcRepository,
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : BaseViewModel(application) {

    var showLoadingLiveData = MutableLiveData<Boolean>()

    var getDataSuccessfulLiveData = MutableLiveData<Boolean>()

    var getDataFailedLiveData = MutableLiveData<String>()

    fun getData() {
        showLoadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val configSettings =
                    FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(
                        if (BuildConfig.DEBUG) 0 else 3600
                    ).build()

                firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
                firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
                    getMbtcData()
                }.addOnFailureListener {
                    Crashlytics.logException(it)
                }
            } catch (ex: Exception) {
                launch(Dispatchers.Main) {
                    showLoadingLiveData.postValue(false)
                    getDataFailedLiveData.postValue(getErrorMessage(context, ex))
                }
            }
        }
    }

    private fun getMbtcData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                btcRepository.saveMbtcData()

                launch(Dispatchers.Main) {
                    getDataSuccessfulLiveData.postValue(true)
                }
            } catch (ex: Exception) {
                launch(Dispatchers.Main) {
                    showLoadingLiveData.postValue(false)
                    getDataFailedLiveData.postValue(getErrorMessage(context, ex))
                }
            }
        }
    }
}
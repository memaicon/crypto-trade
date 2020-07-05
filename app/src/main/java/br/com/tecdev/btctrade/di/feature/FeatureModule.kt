package br.com.tecdev.btctrade.di.feature

import br.com.tecdev.btctrade.feature.coins.AllCoinsAdapter
import br.com.tecdev.btctrade.feature.coins.AllCoinsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import br.com.tecdev.btctrade.feature.home.HomeViewModel
import br.com.tecdev.btctrade.feature.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel { SplashViewModel(androidApplication(), get(), get()) }
}

val homeModule = module {
    viewModel { HomeViewModel(androidApplication(), get()) }
}

val allCoinsModule = module {
    viewModel { AllCoinsViewModel(get()) }
    factory { AllCoinsAdapter() }
}

val firebaseModule = module {
    single { FirebaseRemoteConfig.getInstance() }
    single { FirebaseFirestore.getInstance() }
}
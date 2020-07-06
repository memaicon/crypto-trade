package br.com.tecdev.btctrade.di.repository

import br.com.tecdev.btctrade.repository.MbtcRepository
import br.com.tecdev.btctrade.repository.MbtcRepositoryImpl
import br.com.tecdev.btctrade.repository.TransactionRepository
import br.com.tecdev.btctrade.repository.TransactionRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<MbtcRepository> { MbtcRepositoryImpl(androidContext(), get(), get(), get()) }
    factory<TransactionRepository> { TransactionRepositoryImpl(androidContext(), get()) }
}
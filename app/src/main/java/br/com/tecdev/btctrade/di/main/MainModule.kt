package br.com.tecdev.btctrade.di.main

import br.com.tecdev.btctrade.di.data.dataModule
import br.com.tecdev.btctrade.di.feature.*
import br.com.tecdev.btctrade.di.repository.repositoryModule

val listModule = listOf(
    dataModule,
    repositoryModule,
    splashModule,
    homeModule,
    allCoinsModule,
    transactionsModule,
    firebaseModule
)
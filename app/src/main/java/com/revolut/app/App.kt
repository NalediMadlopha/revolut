package com.revolut.app

import android.app.Application
import com.revolut.app.database.CurrencyDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CurrencyDatabase.getInstance(this)
    }

}
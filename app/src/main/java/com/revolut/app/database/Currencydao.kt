package com.revolut.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.revolut.app.model.Currency
import androidx.room.FtsOptions.Order



@Dao
interface Currencydao {

    @Insert(onConflict = REPLACE)
    fun insertCurrencies(currencies: List<Currency>)

    @Query("SELECT * FROM currency")
    fun getCurrencies(): LiveData<List<Currency>>

}

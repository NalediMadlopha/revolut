package com.revolut.app.database

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.revolut.app.model.Currency
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CurrencyDatabaseTest {

    private lateinit var currencyDao: Currencydao
    private lateinit var currencyDatabase: CurrencyDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        currencyDatabase =
            Room.inMemoryDatabaseBuilder(context, CurrencyDatabase::class.java).build()
        currencyDao = currencyDatabase.currencyDao()
    }

    @Test
    fun `insertCurrencies should insert currencies into the database`() {
        CurrencyDatabase.databaseWriteExecutor.execute {
            val expectedCurrencies = listOf(Currency(1, "ZAR", "RANDS", 16.167), Currency(1, "USD", "US Dollar", 1.133))

            currencyDao.insertCurrencies(expectedCurrencies)

            val currencies = currencyDao.getCurrencies().value

            assertThat(expectedCurrencies, equalTo(currencies))
        }
    }

    @Test
    fun `getCurrencies should return currencies in the database`() {
        CurrencyDatabase.databaseWriteExecutor.execute {
            val expectedCurrency = Currency(1, "ZAR", "RANDS", 16.167)
            currencyDao.insertCurrencies(listOf(expectedCurrency))

            val currency: Currency? = currencyDao.getCurrencies().value?.get(0)

            assertThat(expectedCurrency, equalTo(currency))
        }
    }

    @After
    fun tearDown() {
        currencyDatabase.close()
    }
}
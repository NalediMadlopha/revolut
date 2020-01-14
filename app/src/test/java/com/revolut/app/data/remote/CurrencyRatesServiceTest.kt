package com.revolut.app.data.remote

import com.google.gson.JsonObject
import com.revolut.app.model.CurrencyRate
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

class CurrencyRatesServiceTest {

    private lateinit var currencyRatesService: BehaviorDelegate<CurrencyRatesService>

    private lateinit var retrofit: Retrofit
    private lateinit var mockRetrofit: MockRetrofit

    @Before
    fun setUp() {
        initMocks(this)

        retrofit = Retrofit.Builder()
            .baseUrl(CurrencyRatesService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val behavior = NetworkBehavior.create()

        mockRetrofit = MockRetrofit.Builder(retrofit).networkBehavior(behavior).build()
        currencyRatesService = mockRetrofit.create(CurrencyRatesService::class.java)
    }

    @Test
    fun `getInstance should return an instance of the CurrencyRateService`() {
        assertTrue(CurrencyRatesService::class.java.isInstance(CurrencyRatesService.getInstance()))
    }

    @Test
    fun `getRates should return an error response if the api returns an error response`() {
        val call = currencyRatesService.returning(Calls.response(errorResponse)).getRates("Invalid base")

        val response = call.execute()

        assertFalse(response.isSuccessful)
    }

    @Test
    fun `getRates should return a success response when the api returns a success response`() {
        val call = currencyRatesService.returningResponse(Response.success(listOf<CurrencyRate>())).getRates("EUR")

        val response = call.execute()

        assertTrue(response.isSuccessful)
    }

    companion object {
        private val errorResponse = Response.error<JsonObject>(
            404, ResponseBody.create(MediaType.parse("application/json"), "{ \"error\": { \"text\": \"Error: Something went wrong!\" } }")
        )
    }

}


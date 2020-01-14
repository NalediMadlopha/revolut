package com.revolut.app.data.remote

import com.revolut.app.model.CurrencyRate
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesService {

    @GET(PATH_LATEST)
    fun getRates(
        @Query(QUERY_BASE) base: String
    ): Call<List<CurrencyRate>>

    companion object {
        private const val PATH_LATEST = "/latest"
        private const val QUERY_BASE = "base"

        const val BASE_URL = "https://revolut.duckdns.org/"

        fun getInstance() : CurrencyRatesService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyRatesService::class.java)
        }
    }

}
package com.revolut.app.model

import androidx.room.Entity

data class CurrencyRate(
    var countryAbbreviation: String,
    var rate: Float
)
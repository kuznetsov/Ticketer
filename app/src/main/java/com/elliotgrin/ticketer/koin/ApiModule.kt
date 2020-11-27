package com.elliotgrin.ticketer.koin

import com.elliotgrin.ticketer.api.Api
import com.elliotgrin.ticketer.common.HOST_URL
import com.elliotgrin.ticketer.util.api.ApiUtils
import org.koin.dsl.module

val apiModule = module {
    single { ApiUtils.createHttpClient(get()) }
    single { ApiUtils.createGsonConverter() }
    single { ApiUtils.createRetrofit(get(), get(), HOST_URL) }
    single { ApiUtils.getApi<Api>(get()) }
}

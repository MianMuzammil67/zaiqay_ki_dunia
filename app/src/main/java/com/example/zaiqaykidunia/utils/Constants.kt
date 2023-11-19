package com.example.zaiqaykidunia.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Constants {
    companion object {
        const val API_KEY = "dd8099f457ed423a9bd79e46584226ab"
        const val BASE_URL = "https://api.spoonacular.com/"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo: NetworkInfo?
            activeNetworkInfo = cm.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }

    }
}
package com.example.myapitest

import com.example.myapitest.database.DatabaseBuilder

class MyApiTestApp : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseBuilder.getInstance(this)
    }
}
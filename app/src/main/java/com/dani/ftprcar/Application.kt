package com.dani.ftprcar

import android.app.Application
import com.dani.ftprcar.database.DatabaseBuilder

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    /**
     * fun para inicializar nossas dependências através do nosso Context
     */
    private fun init() {
        DatabaseBuilder.getInstance(this)
    }
}
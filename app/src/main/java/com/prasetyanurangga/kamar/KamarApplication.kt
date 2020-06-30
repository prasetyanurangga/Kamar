package com.prasetyanurangga.kamar

import android.app.Application
import com.prasetyanurangga.kamar.di.component.AppComponent
import com.prasetyanurangga.kamar.di.component.DaggerAppComponent
import com.prasetyanurangga.kamar.di.module.DatabaseModule

class KamarApplication: Application() {

    companion object {
        lateinit var instance: KamarApplication
    }

    lateinit var appComponent: AppComponent

    override fun onCreate(){
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
             .databaseModule(DatabaseModule())
                .build()

    }

}
package com.prasetyanurangga.kamar.di.component

import android.app.Application
import com.prasetyanurangga.kamar.view.MainActivity
import com.prasetyanurangga.kamar.di.module.DatabaseModule
import com.prasetyanurangga.kamar.di.module.KamarModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        KamarModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder


        @BindsInstance
        fun databaseModule(databaseModule: DatabaseModule): Builder

        fun build(): AppComponent
    }
}
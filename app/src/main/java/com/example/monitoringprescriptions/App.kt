package com.example.monitoringprescriptions

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.monitoringprescriptions.data.room.HistoryAppointmentFullDao
import com.example.monitoringprescriptions.data.room.HistoryDataBase
import com.example.monitoringprescriptions.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModules)
        }

        appInstance = this

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        /**
         * двойное сравнение if (db == null) - это патерн дабл чек. Он применяется когда вкл. synchronized
         * synchronized - накладывает большие ограничения. В нем синхранизируются потоки если туда много
         * потоков будут обращатся.
         * Двойное сравнение необходимо для того чтобы когда зашли (после первой проверки) в synchronized то
         * при выходе из synchronized db уже может быть не null
         */
        fun getHistoryAppointmentFullDao(): HistoryAppointmentFullDao {
            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null)
                            throw IllegalStateException("Application is null DB")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            HistoryDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()//переделать, должно быть не в основном потоке
                            .build()
                    }
                }
            }
            return db!!.historyAppointmentFullDao()
        }
    }
}
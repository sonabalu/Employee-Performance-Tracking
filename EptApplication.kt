package com.example.employeeperformancetracker

import android.app.Application
import com.google.firebase.FirebaseApp

class EptApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

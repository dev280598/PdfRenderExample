package com.example.firebasestorage

import android.app.Application
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainApplication : Application() {
    override fun onCreate() {
        Firebase.initialize(this)
        Log.i("=====MainApplication" , "MainApplication")
        super.onCreate()
    }
}
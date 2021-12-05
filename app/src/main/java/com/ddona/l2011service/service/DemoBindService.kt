package com.ddona.l2011service.service

import android.app.IntentService
import android.content.Intent

class DemoBindService : IntentService("download") {
    override fun onHandleIntent(intent: Intent?) {
        if (intent?.action == "Download") {
            //start download file
        } else if (intent?.action == "Upload") {
            //upload action
        }
        //QUEUE
    }
}
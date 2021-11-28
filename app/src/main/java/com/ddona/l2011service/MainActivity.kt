package com.ddona.l2011service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ddona.l2011service.databinding.ActivityMainBinding
import java.net.URL

class MainActivity : AppCompatActivity(), Runnable {

    private lateinit var bitmap: Bitmap

    private lateinit var binding: ActivityMainBinding
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                binding.imgAvatar.setImageBitmap(bitmap)
            }
            if (msg.what == 2) {
                val number1 = msg.arg1
                val number2 = msg.arg2
                val obj = msg.obj
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        lifecycleScope.launch(Dispatchers.IO) {
//            JsoupParser.getAllQuestion("Jetpack")
//        }

        binding.btnLoad.setOnClickListener {
            DownloadFile(this).execute(
                "https://photo-cms-baonghean.zadn.vn/w607/Uploaded/2021/ftgbtgazsnzm/2020_07_14/ngoctrinhmuonsinhcon1_swej7996614_1472020.jpg",
            )
//            val thread = Thread(this)
//            thread.start()
        }
    }

    override fun run() {
        downloadImage()
    }

    inner class DownloadFile(private val context: Context) : AsyncTask<String, Void, Bitmap>() {
        private lateinit var dialog: AlertDialog
        override fun onPreExecute() {
            super.onPreExecute()
            dialog = AlertDialog.Builder(context)
                .setTitle("Downloading")
                .setMessage("Download a image")
                .show()
        }

        override fun doInBackground(vararg params: String?): Bitmap {
            Thread.sleep(4000)
            val link = params[0]
            val url = URL(link)
            val connection = url.openConnection()
            val inputStream = connection.getInputStream()
            return BitmapFactory.decodeStream(inputStream)
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            dialog.dismiss()
            binding.imgAvatar.setImageBitmap(result)
        }

    }

    fun downloadImage() {
        val link =
            "https://photo-cms-baonghean.zadn.vn/w607/Uploaded/2021/ftgbtgazsnzm/2020_07_14/ngoctrinhmuonsinhcon1_swej7996614_1472020.jpg"
        val url = URL(link)
        val connection = url.openConnection()
        val inputStream = connection.getInputStream()
        bitmap = BitmapFactory.decodeStream(inputStream)
        handler.sendEmptyMessage(1)
        val msg = Message()
        msg.what = 2
        msg.arg1 = 40
        msg.arg2 = 60
        msg.obj = bitmap
//        val bundle = Bundle()
//        //put something into bundle
//        msg.data = bundle
        handler.sendMessage(msg)
//        runOnUiThread {
//            binding.imgAvatar.setImageBitmap(bitmap)
//        }
//        binding.imgAvatar.post {
//            binding.imgAvatar.setImageBitmap(bitmap)
//        }
//        binding.imgAvatar.postDelayed({
//            binding.imgAvatar.setImageBitmap(bitmap)
//        }, 5000)
    }
}
package com.ddona.l2011service

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ddona.l2011service.util.Const

class ProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)
//        val values = ContentValues()
//        values.put(Const.COL_NAME, "Trung Doan")
//        values.put(Const.COL_AGE, 18)
//        values.put(Const.COL_CLASS, "Land2011")
//        contentResolver.insert(
//            Uri.parse("content://com.ddona.l2011service.provider.StudentProvider/student"),
//            values
//        )
//
//        val cursor = contentResolver.query(
//            Uri.parse("content://com.ddona.l2011service.provider.StudentProvider/student"),
//            null,
//            null,
//            null,
//            null
//        )
//        if (cursor!!.moveToFirst()) {
//            val idIndex = cursor.getColumnIndex("_id")
//            val nameIndex = cursor.getColumnIndex("_name")
//            val ageIndex = cursor.getColumnIndex("_age")
//            val classIndex = cursor.getColumnIndex("_class")
//            do {
//                val id = cursor.getInt(idIndex)
//                val name = cursor.getString(nameIndex)
//                val age = cursor.getInt(ageIndex)
//                val className = cursor.getString(classIndex)
//                Log.d("doanpt", "$id - $name - $age - $className")
//            } while (cursor.moveToNext())
//        }

        //        val values = ContentValues()
//        values.put("_name", "hieuxautrai")
//        values.put("_age", 16)
//        values.put("_class", "T3h")
//        contentResolver.update(
//            Uri.parse("content://com.ddona.l2011service.provider.StudentProvider/student/4"),
//            values,
//            null,
//            null
//        )

//        val count = contentResolver.delete(
//            Uri.parse("content://com.ddona.l2011service.provider.StudentProvider/student"),
//            null,
//            null
//        )
//        Log.d("doanpt", "delete $count rows")
    }
}
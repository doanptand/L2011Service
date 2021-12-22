package com.ddona.l2011service.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ddona.l2011service.util.Const

class StudentHelper(context: Context) : SQLiteOpenHelper(
    context, "student.db", null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Const.QR_CREATE_TB_STUDENT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
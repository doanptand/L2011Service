package com.ddona.l2011service.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.ddona.l2011service.db.StudentHelper
import com.ddona.l2011service.util.Const
import java.lang.IllegalArgumentException

class StudentProvider : ContentProvider() {

    private lateinit var studentHelper: StudentHelper

    companion object {
        private const val AUTHORITY = "com.ddona.l2011service.provider.StudentProvider"

        //content://com.ddona.l2011service.provider.StudentProvider/student
        private val STUDENT_CONTENT_URI = Uri.parse("content://$AUTHORITY/${Const.TB_STUDENT}")
        private const val CODE_ALL_STUDENT = 1
        private const val CODE_ONE_STUDENT = 2
        private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        //content://com.ddona.l2011service.provider.StudentProvider/student
        mUriMatcher.addURI(AUTHORITY, Const.TB_STUDENT, CODE_ALL_STUDENT)
        //content://com.ddona.l2011service.provider.StudentProvider/student/100
        mUriMatcher.addURI(AUTHORITY, "${Const.TB_STUDENT}/#", CODE_ONE_STUDENT)
        //content://com.ddona.l2011service.provider.StudentProvider/class/Land2013/student/100
        //mUriMatcher.addURI(AUTHORITY, "class/*/student/#", CODE_ONE_STUDENT)
    }

    override fun onCreate(): Boolean {
        studentHelper = StudentHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        if (mUriMatcher.match(uri) < CODE_ALL_STUDENT) {
            throw IllegalArgumentException("Unknown to handle uri $uri")
        }
        val query = SQLiteQueryBuilder() //SELECT * FROM
        query.tables = Const.TB_STUDENT //SELECT * FROM student
        //content://com.ddona.l2011service.provider.StudentProvider/student
        //null or [_id,_name,_age,_class]
        //"_id=?"
        //[1]
        //null
        //val cursor2 = query.query(studentHelper.writableDatabase, projection, selection, selectionArgs, null, null, sortOrder)


        if (mUriMatcher.match(uri) == CODE_ONE_STUDENT) {
            //content://com.ddona.l2011service.provider.StudentProvider/student/1000
            //null or [_id,_name,_age,_class]
            //null
            //null
            val id = uri.pathSegments[1]
            query.appendWhere("${Const.COL_ID}=$id") // SELECT * FROM STUDENT WHERE _id=1000
        }
        val db = studentHelper.readableDatabase
        val cursor = query.query(
            db,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        if (mUriMatcher.match(uri) != CODE_ALL_STUDENT) {
            throw IllegalArgumentException("Unknown to handle uri $uri")
        }
        //content://com.ddona.l2011service.provider.StudentProvider/student
        val db = studentHelper.writableDatabase
        val rowId = db.insert(Const.TB_STUDENT, null, values)
        if (rowId >= 0) {
            //must return content://com.ddona.l2011service.provider.StudentProvider/student/100
            val resultUri = ContentUris.withAppendedId(STUDENT_CONTENT_URI, rowId)
            context!!.contentResolver.notifyChange(resultUri, null)
            return resultUri
        }
        throw IllegalArgumentException("An error happened when inserting data into database")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = studentHelper.writableDatabase
        when (mUriMatcher.match(uri)) {
            CODE_ONE_STUDENT -> {
                //content://com.ddona.l2011service.provider.StudentProvider/student/4
                //null
                //null
                val id = uri.pathSegments[1]
                return db.delete(Const.TB_STUDENT, "${Const.COL_ID}=?", arrayOf(id))
                //content://com.ddona.l2011service.provider.StudentProvider/student/1
                //"_id=?"
                //"1"
                //return db.delete(Const.TB_STUDENT, selection, selectionArgs)
            }
            CODE_ALL_STUDENT -> {
                //content://com.ddona.l2011service.provider.StudentProvider/student
                //null
                //null

                //content://com.ddona.l2011service.provider.StudentProvider/student
                //"_id=?"
                //"1"
                return db.delete(Const.TB_STUDENT, selection, selectionArgs)
            }
            else -> throw IllegalArgumentException("Unknown uri to delete $uri")
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        if (mUriMatcher.match(uri) != CODE_ONE_STUDENT) {
            throw IllegalArgumentException("Unknown uri to update data: $uri")
        }
        //content://com.ddona.l2011service.provider.StudentProvider/student/100
        //values
        //null - "_id=?"
        //null - "1"
        val db = studentHelper.writableDatabase
        val count = db.update(Const.TB_STUDENT, values, selection, selectionArgs)

        if (count < 1) {
            throw IllegalArgumentException("Unable to update data for uri $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
}
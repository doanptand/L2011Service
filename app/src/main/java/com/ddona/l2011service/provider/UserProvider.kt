package com.ddona.l2011service.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ddona.l2011service.db.UserDao
import com.ddona.l2011service.db.entity.UserDatabase

class UserProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = "com.ddona.l2011service.provider.UserProvider"

        //content://com.ddona.l2011service.provider.StudentProvider/student
        private val STUDENT_CONTENT_URI = Uri.parse("content://$AUTHORITY/user")
        private const val CODE_ALL_USER = 1
        private const val CODE_ONE_USER = 2
        private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    private lateinit var userDao: UserDao

    override fun onCreate(): Boolean {
        userDao = UserDatabase.getInstance(context!!).getUserDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}
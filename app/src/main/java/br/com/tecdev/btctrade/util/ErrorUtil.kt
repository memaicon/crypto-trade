package br.com.tecdev.btctrade.util

import android.content.Context
import com.crashlytics.android.Crashlytics
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.exception.NetworkException
import java.lang.Exception

fun getErrorMessage(context: Context, ex: Exception): String {
    return if (ex is NetworkException) {
        ex.message!!
    } else {
        Crashlytics.logException(ex)
        context.getString(R.string.default_error_message)
    }
}
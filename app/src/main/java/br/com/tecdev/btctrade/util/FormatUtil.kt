package br.com.tecdev.btctrade.util

import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun formatNumber(n: Double): String {
    return try {
        val numberFormat = DecimalFormat("#,##0.00")
        numberFormat.format(n)
    } catch (ex: Exception) {
        n.toString()
    }
}

fun formatDateFromMillis(milliSeconds: Long): String {
    var formatDate: String
    try {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(milliSeconds * 1000)
        formatDate = formatter.format(date)
    } catch (ex: Exception) {
        formatDate = milliSeconds.toString()
    }
    return formatDate
}
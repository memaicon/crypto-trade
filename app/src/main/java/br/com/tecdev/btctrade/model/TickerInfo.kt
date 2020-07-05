package br.com.tecdev.btctrade.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type

@Parcelize
@Entity
class TickerInfo constructor(
    @PrimaryKey
    @SerializedName("high")
    @ColumnInfo(name = "high")
    var high: Double,

    @SerializedName("low")
@ColumnInfo(name = "low")
var low: Double,

    @SerializedName("vol")
    @ColumnInfo(name = "vol")
    var vol: Double,

    @SerializedName("last")
    @ColumnInfo(name = "last")
    var last: Double,

    @SerializedName("buy")
    @ColumnInfo(name = "buy")
    var buy: Double,

    @SerializedName("sell")
    @ColumnInfo(name = "sell")
    var sell: Double,

    @SerializedName("open")
    @ColumnInfo(name = "open")
    var open: Double,

    @SerializedName("date")
    @ColumnInfo(name = "date")
    var date: Long
) : Parcelable {
    constructor() : this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0)
}

class TickerInfoConverter {
    private val gson = Gson()

    @TypeConverter
    fun toTickerInfo(data: String): TickerInfo {
        val listType: Type =
            object : TypeToken<TickerInfo>() {}.type
        return gson.fromJson<TickerInfo>(data, listType)
    }

    @TypeConverter
    fun toString(ticker: TickerInfo): String {
        return gson.toJson(ticker)
    }
}
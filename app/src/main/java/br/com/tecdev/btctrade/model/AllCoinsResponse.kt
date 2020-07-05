package br.com.tecdev.btctrade.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
class AllCoinsResponse constructor(
    @PrimaryKey
    @SerializedName("coin")
    @ColumnInfo(name = "coin")
    val coin: String,

    @SerializedName("high")
    @ColumnInfo(name = "high")
    val high: Double,

    @SerializedName("buy")
    @ColumnInfo(name = "buy")
    var buy: Double,

    @SerializedName("sell")
    @ColumnInfo(name = "sell")
    var sell: Double
) : Parcelable
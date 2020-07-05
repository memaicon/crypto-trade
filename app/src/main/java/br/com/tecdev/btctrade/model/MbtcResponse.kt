package br.com.tecdev.btctrade.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
class MbtcResponse(
    @PrimaryKey
    @SerializedName("ticker")
    @ColumnInfo(name = "ticker")
    @TypeConverters(TickerInfoConverter::class)
    val ticker: TickerInfo
) : Parcelable

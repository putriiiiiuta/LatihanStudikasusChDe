package putriiiiiuta.androidlima.latihanstudikasuschde.lokal

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "judul") var judul: String?,
    @ColumnInfo(name = "waktu") var waktu: String,
    @ColumnInfo(name = "catatan") var catatan: String?
) : Parcelable
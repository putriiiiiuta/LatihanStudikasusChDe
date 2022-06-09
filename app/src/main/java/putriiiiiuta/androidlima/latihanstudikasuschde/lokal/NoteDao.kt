package putriiiiiuta.androidlima.latihanstudikasuschde.lokal

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNoteTaking() : List<Note>

    @Insert
    fun insertNoteTaking(note: Note) : Long

}
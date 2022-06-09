package putriiiiiuta.androidlima.latihanstudikasuschde.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import putriiiiiuta.androidlima.latihanstudikasuschde.R
import putriiiiiuta.androidlima.latihanstudikasuschde.lokal.Note


@SuppressLint("SetTextI18n")
class AdapterNote(private val listNote : List<Note>, private val OnClick : (Note) -> Unit) : RecyclerView.Adapter<AdapterNote.ViewHolder> () {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(viewItem)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.judul.text = "Judul : ${listNote[position].judul}"
        holder.itemView.catatan.text = "Catatan : ${listNote[position].catatan}"
        holder.itemView.time.text = "Waktu : ${listNote[position].waktu}"

        holder.itemView.cardNote.setOnClickListener {
            OnClick(listNote[position])
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }


}
package putriiiiiuta.androidlima.latihanstudikasuschde.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import putriiiiiuta.androidlima.latihanstudikasuschde.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import putriiiiiuta.androidlima.latihanstudikasuschde.adapter.AdapterNote
import putriiiiiuta.androidlima.latihanstudikasuschde.datastore.UserManager
import putriiiiiuta.androidlima.latihanstudikasuschde.lokal.Note
import putriiiiiuta.androidlima.latihanstudikasuschde.lokal.NoteDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@DelicateCoroutinesApi
@SuppressLint("NewApi", "SetTextI18n")
class HomeFragment : Fragment() {
    private var db : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = NoteDatabase.getInstance(requireContext())
        addData()
        getDataNote()
        logout()
        getDataStore()
    }

    private fun getDataStore(){
        val userManager = UserManager(requireContext())
        userManager.username.asLiveData().observe(viewLifecycleOwner){
            welcome_username.text = "Welcome, $it"
        }
    }

    private fun logout(){
        logout.setOnClickListener {
            val userManager = UserManager(requireContext())
            GlobalScope.launch {
                userManager.logout()
            }
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment)
            activity?.finish()
        }
    }

    private fun addData(){
        addData.setOnClickListener {
            val alertA = LayoutInflater.from(requireContext())
                .inflate(R.layout.custom_add, null, false)
            val alertB = AlertDialog.Builder(requireContext())
                .setView(alertA)
                .create()

            alertA.btn_input.setOnClickListener {
                GlobalScope.async {
                    val jdl = alertA.masukan_judul.text.toString()
                    val ctt = alertA.masukan_catatan.text.toString()

                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    val formatted = current.format(formatter)

                    val simpan = db?.noteDao()?.insertNoteTaking(Note(null, jdl, formatted, ctt))

                    requireActivity().runOnUiThread {
                        if (simpan != 0.toLong()){
                            Toast.makeText(requireContext(), "Berhasil Menambahkan", Toast.LENGTH_LONG).show()
                            alertB.dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Gagal Menambahkan", Toast.LENGTH_LONG).show()
                        }
                        activity?.recreate()
                    }
                }
            }
            alertB.show()
        }
    }



    private fun getDataNote() {
        rvNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch {
            val listD = db?.noteDao()?.getAllNoteTaking()

            activity?.runOnUiThread {
                listD.let { it ->
                    val adp = AdapterNote(it!!){
                        val detail = bundleOf("datadetail" to it)
                        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, detail)
                    }
                    rvNote.adapter = adp
                }
            }
        }
    }
}
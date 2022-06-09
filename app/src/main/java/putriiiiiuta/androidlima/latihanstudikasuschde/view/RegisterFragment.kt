package putriiiiiuta.androidlima.latihanstudikasuschde.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import putriiiiiuta.androidlima.latihanstudikasuschde.R
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register()
        goToLogin()
    }


    private fun register(){
        btn_regist.setOnClickListener {
            postApiRegister()
        }
    }

    private fun postApiRegister(){
        val username = masukan_username_register.text.toString()
        val name = masukan_name_register.text.toString()
        val password = masukan_password_register.text.toString()
        val konfirmasiPassword = masukan_konfirmasi_password_register.text.toString()

        ApiClient.instance.postDataUser(PostUserResponseItem(name, password, username))
            .enqueue(object : Callback<GetAllUserResponseItem> {
                override fun onResponse(
                    call: Call<GetAllUserResponseItem>,
                    response: Response<GetAllUserResponseItem>
                ) {
                    if (response.isSuccessful){
                        if (username.isEmpty()){
                            Toast.makeText(requireContext(), "Username Harus Diisi", Toast.LENGTH_SHORT).show()
                        } else if (name.isEmpty()){
                            Toast.makeText(requireContext(), "Nama Harus Diisi", Toast.LENGTH_SHORT).show()
                        } else if (password.isEmpty()){
                            Toast.makeText(requireContext(), "Password Harus Diisi", Toast.LENGTH_SHORT).show()
                        } else if (konfirmasiPassword.isEmpty()){
                            Toast.makeText(requireContext(), "Konfirmasi Harus Diisi", Toast.LENGTH_SHORT).show()
                        } else {
                            if (konfirmasiPassword != password){
                                Toast.makeText(requireContext(), "Konfirmasi Dan Password Harus Sama", Toast.LENGTH_SHORT).show()
                            } else {
                                Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
                                Toast.makeText(requireContext(), "Registrasi Sukses", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<GetAllUserResponseItem>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                }

            })
    }


    private fun goToLogin(){
        sudah_punya_akun.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
            activity?.finish()
        }
    }
}
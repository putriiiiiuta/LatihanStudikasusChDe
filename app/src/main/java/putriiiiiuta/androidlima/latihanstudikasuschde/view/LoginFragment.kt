package putriiiiiuta.androidlima.latihanstudikasuschde.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import putriiiiiuta.androidlima.latihanstudikasuschde.R
import android.widget.Toast
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import putriiiiiuta.androidlima.latihanstudikasuschde.datastore.UserManager
import putriiiiiuta.androidlima.latihanstudikasuschde.model.GetAllUserResponseItem
import putriiiiiuta.androidlima.latihanstudikasuschde.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class LoginFragment : Fragment() {
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login()
        goToRegister()
        check()
    }

    private fun check(){
        userManager = UserManager(requireContext())
        userManager.username.asLiveData().observe(viewLifecycleOwner){
            when {
                it != "" -> {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }


    private fun login(){
        btn_login.setOnClickListener {
            when {
                masukan_password.text.toString().isEmpty() -> {
                    Toast.makeText(requireContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
                }
                masukan_username.text.toString().isEmpty() -> {
                    Toast.makeText(requireContext(), "Username Salah", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Login Sukses", Toast.LENGTH_LONG).show()
                    getApiLogin()
                }
            }
        }
    }

    private fun getApiLogin(){
        userManager = UserManager(requireContext())
        val username = masukan_username.text.toString()
        val password = masukan_password.text.toString()

        ApiClient.instance.getDataUser(username, password)
            .enqueue(object : Callback<List<GetAllUserResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllUserResponseItem>>,
                    response: Response<List<GetAllUserResponseItem>>
                ) {
                    when {
                        response.isSuccessful -> {
                            when {
                                response.body()?.isEmpty() == true -> {
                                    Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_LONG).show()
                                }
                                else -> {
                                    when {
                                        response.body()?.size!! > 1 -> {
                                            Toast.makeText(requireContext(), "Data Salah", Toast.LENGTH_LONG).show()
                                        }
                                        username != response.body()!![0].username -> {
                                            Toast.makeText(requireContext(), "Username Salah", Toast.LENGTH_LONG).show()
                                        }
                                        password != response.body()!![0].password -> {
                                            Toast.makeText(requireContext(), "Password Salah", Toast.LENGTH_LONG).show()
                                        }
                                        else -> {
                                            GlobalScope.launch {
                                                userManager.saveData(username, password, "", "", "" ,"")
                                            }
                                            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                                            activity?.finish()
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()

                }

            })
    }

    private fun goToRegister(){
        belum_punya_akun.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}
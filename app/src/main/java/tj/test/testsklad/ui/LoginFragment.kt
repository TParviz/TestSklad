package tj.test.testsklad.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tj.test.testsklad.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        (activity as MainActivity).setActionBarTitle("Авторизация")

        addInputText()

        btnApply.setOnClickListener {
            saveUserInfo(
                login = etLoginInput.text.toString(),
                password = etPasswordInput.text.toString(),
                link = etLinkInput.text.toString()
            )
        }
    }

    private fun addInputText() {
        val sharedPreferences = context?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
        val login = sharedPreferences?.getString("login", "")
        binding.etLoginInput.setText(login)
        val password = sharedPreferences?.getString("password", "")
        binding.etPasswordInput.setText(password)
        val link = sharedPreferences?.getString("link", "")
        if (!link.isNullOrEmpty()) {
            binding.etLinkInput.setText(link)
        }
    }

    private fun saveUserInfo(login: String, password: String, link: String) {
        val sharedPreferences = context?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("login", login)
        editor?.putString("password", password)
        editor?.putString("link", link)
        editor?.apply()
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
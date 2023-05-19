package tj.test.testsklad.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import tj.test.testsklad.data.models.toUi
import tj.test.testsklad.databinding.FragmentPriemkaDetailsBinding
import tj.test.testsklad.ui.adapters.PriemkaDetailsAdapter

@AndroidEntryPoint
class PriemkaDetailsFragment : Fragment() {

    private var _binding: FragmentPriemkaDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val args: PriemkaDetailsFragmentArgs by navArgs()

    private val adapterPriemka = PriemkaDetailsAdapter(
        onViewClicked = { item ->
            findNavController().navigate(
                PriemkaDetailsFragmentDirections.actionPriemkaDetailsFragmentToGoodsDetailsFragment(
                    item
                )
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriemkaDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        savePriemka(args.priemka)
        viewModel.getPriemkaDetailsList(args.priemka)
    }

    private fun initView() = with(binding) {
        (activity as MainActivity).setActionBarTitle(args.priemka)
        rvPriemkaList.adapter = adapterPriemka

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            viewModel.getPriemkaDetailsList(args.priemka)
        }

    }

    private fun initObservers() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            priemkaDetailsList.collectLatest { list ->
                adapterPriemka.submitList(list.map { it.toUi() })
            }
        }

    }

    private fun savePriemka(code: String) {
        val sharedPreferences = context?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("priemka", code)
        editor?.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
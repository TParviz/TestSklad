package tj.test.testsklad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import tj.test.testsklad.data.models.toUi
import tj.test.testsklad.databinding.FragmentHomeBinding
import tj.test.testsklad.ui.adapters.PriemkaAdapter

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val adapterPriemka = PriemkaAdapter(
        onViewClicked = { item ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPriemkaDetailsFragment(item.numberCode)
            )
        },
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        (activity as MainActivity).setActionBarTitle("Приемки")
        btnPriemka.setOnClickListener {
            btnPriemka.isVisible = false
            viewModel.getPriemkaList()
        }
        rvPriemkaList.adapter = adapterPriemka
        btnPriemka.isVisible = !rvPriemkaList.isVisible
    }

    private fun initObservers() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            priemkaList.collectLatest { list ->
                binding.rvPriemkaList.isVisible = true
                adapterPriemka.submitList(list.map { it.toUi() })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
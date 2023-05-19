package tj.test.testsklad.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tj.test.testsklad.databinding.FragmentGoodsDetailsBinding
import tj.test.testsklad.ui.adapters.GoodsDetailsAdapter
import tj.test.testsklad.ui.models.PriemkaUi

@AndroidEntryPoint
class GoodsDetailsFragment : Fragment() {

    private var _binding: FragmentGoodsDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val args: GoodsDetailsFragmentArgs by navArgs()
    private val adapterPriemka = GoodsDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        (activity as MainActivity).setActionBarTitle("Товар")
        rvPriemkaList.adapter = adapterPriemka
        tvName.text = args.goodsDetails.nomenclature
        tvCode.text = "Код : ${args.goodsDetails.code}"
        tvSeriy.text = "Серия : ${args.goodsDetails.series}"
        tvPlan.text = "План : ${args.goodsDetails.qntPlan}"
        viewModel.setCurrentPriemkaDetails(args.goodsDetails)

        btnComplete.setOnClickListener {
            viewModel.sendBarcodes()
        }

        viewModel.getAllDb()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("com.xcheng.scanner.action.BARCODE_DECODING_BROADCAST")
        intentFilter.addAction("scan.rcv.message")
        registerReceiver(
            requireContext(),
            customBroadcastReceiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    private val customBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val typeBarcode = intent.getStringExtra("barcodeData")
            val barcode = intent.getStringExtra("EXTRA_BARCODE_DECODING_DATA")

            Toast.makeText(context, "Code: $typeBarcode", Toast.LENGTH_SHORT).show()
            viewModel.addBarcodeToList(barcode.toString())
        }
    }

    private fun initObservers() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            barcodesList.observe(viewLifecycleOwner) { list ->
                val newList: List<PriemkaUi> = list.map { PriemkaUi(numberCode = it) }
                adapterPriemka.submitList(newList)
                binding.tvFact.text = "Факт : ${newList.size}"

            }
            isResponseOk.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().popBackStack()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
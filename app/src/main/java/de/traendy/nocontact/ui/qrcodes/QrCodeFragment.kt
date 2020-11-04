package de.traendy.nocontact.ui.qrcodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.databinding.FragmentQrcodeListBinding

class QrCodeFragment : Fragment() {

    private val viewModel by viewModels<QrCodeFragmentViewModel>{
        val qrCodeRepository = QrCodeRepository(
            QrCodeDataSource(
                QrCodeDatabase.provideDatabase(requireContext()).qrCodeDao()
            )
        )
        QrCodeFragmentViewModelFactory(qrCodeRepository) }
    private lateinit var binding: FragmentQrcodeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrcodeListBinding.inflate(inflater, container, false)
        val qrCodeListAdapter = QrCodeRecyclerViewAdapter()
        binding.list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = qrCodeListAdapter
        }
        viewModel.qrCodes.observe(viewLifecycleOwner, {
            qrCodeListAdapter.addAndSubmitList(it.toList())
        })
        viewModel.loadQrCodes()
        return binding.root
    }
}
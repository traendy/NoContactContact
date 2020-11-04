package de.traendy.nocontact.ui.qrcodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.model.QrCode
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.R
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
        val qrCodeListAdapter = QrCodeRecyclerViewAdapter { qrCode -> showDeleteDialog(qrCode) }
        binding.list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = qrCodeListAdapter
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.list)
        viewModel.qrCodes.observe(viewLifecycleOwner, {
            qrCodeListAdapter.addAndSubmitList(it.toList())
        })
        viewModel.loadQrCodes()
        return binding.root
    }

    private fun showDeleteDialog(qrCode: QrCode) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(getString(R.string.delete_qr_code) + " ${qrCode.title}")
                .setPositiveButton(R.string.delete) { _, _ -> viewModel.deleteCode(qrCode) }
                .setNegativeButton(R.string.cancel) { dialogInterface, _ -> dialogInterface.dismiss() }
                .show()
        }
    }
}
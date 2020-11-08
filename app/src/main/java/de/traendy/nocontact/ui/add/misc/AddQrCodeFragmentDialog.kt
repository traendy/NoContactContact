package de.traendy.nocontact.ui.add.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.databinding.AddQrCodeFragmentBinding
import de.traendy.nocontact.ui.qrcodes.QrCodeFragmentViewModelFactory

open class AddQrCodeFragmentDialog: DialogFragment() {

    private lateinit var _addQrCodeDialog: AddQrCodeDialog

    private val viewModel: AddQrCodeViewModel by viewModels{
        val qrCodeRepository = QrCodeRepository(
            QrCodeDataSource(
                QrCodeDatabase.provideDatabase(requireContext()).qrCodeDao()
            )
        )
        QrCodeFragmentViewModelFactory(qrCodeRepository)
    }
    private lateinit var binding: AddQrCodeFragmentBinding

    companion object {
        fun getInstance(addQrCodeFragment: AddQrCodeDialog): AddQrCodeFragmentDialog {
            return AddQrCodeFragmentDialog().apply { _addQrCodeDialog = addQrCodeFragment }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = _addQrCodeDialog.inflate(inflater, container, this, viewModel)
        return binding.root
    }
}
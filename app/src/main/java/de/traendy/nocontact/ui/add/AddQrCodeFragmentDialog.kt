package de.traendy.nocontact.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import de.traendy.nocontact.databinding.AddQrCodeFragmentBinding

open class AddQrCodeFragmentDialog: DialogFragment() {

    private lateinit var _addQrCodeDialog: AddQrCodeDialog
    private val viewModel: AddQrCodeViewModel by viewModels()
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
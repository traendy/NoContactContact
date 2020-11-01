package de.traendy.nocontact.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import de.traendy.nocontact.databinding.AddQrCodeFragmentBinding

class AddQrCodeDialog {

    fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?,
        dialog: DialogFragment,
        viewModel: AddQrCodeViewModel
    ): AddQrCodeFragmentBinding {
        val binding = AddQrCodeFragmentBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener {
            viewModel.setName(binding.nameInputLayout.editText?.text.toString())
            viewModel.setContent(binding.contentInputLayout.editText?.text.toString())
            dialog.dismiss()
        }
        return binding
    }

}
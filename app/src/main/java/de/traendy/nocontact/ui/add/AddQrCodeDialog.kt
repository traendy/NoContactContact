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
            val name = binding.nameInputLayout.editText?.text.toString()
            val content = binding.contentInputLayout.editText?.text.toString()
            viewModel.setName(name)
            viewModel.setContent(content)
            viewModel.saveQrCode(name, content)
            dialog.dismiss()
        }
        return binding
    }

}
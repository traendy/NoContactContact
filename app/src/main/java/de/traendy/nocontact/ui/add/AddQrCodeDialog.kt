package de.traendy.nocontact.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import de.traendy.nocontact.R
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
            var error = false
            if (name.trim().isEmpty()) {
                binding.nameInputLayout.error =
                    binding.root.context.getString(R.string.name_to_short_or_missing)
                error = true
            }
            if (content.trim().isEmpty()) {
                binding.contentInputLayout.error =
                    binding.root.context.getString(R.string.content_to_short_or_missing)
                error = true
            }
            if (!error) {
                viewModel.saveQrCode(name, content)
                dialog.dismiss()
            }
        }
        return binding
    }

}
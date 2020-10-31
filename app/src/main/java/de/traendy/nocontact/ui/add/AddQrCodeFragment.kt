package de.traendy.nocontact.ui.add

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.traendy.nocontact.databinding.AddQrCodeFragmentBinding

class AddQrCodeFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = AddQrCodeFragment()
    }

    private lateinit var viewModel: AddQrCodeViewModel
    private lateinit var binding: AddQrCodeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddQrCodeFragmentBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener {
            viewModel.setName(binding.nameInputLayout.editText?.text.toString())
            viewModel.setContent(binding.contentInputLayout.editText?.text.toString())
            dismiss()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddQrCodeViewModel::class.java)

    }

}
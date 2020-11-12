package de.traendy.nocontact.ui.add.mail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.MainActivity
import de.traendy.nocontact.R
import de.traendy.nocontact.databinding.MailFragmentBinding
import de.traendy.nocontact.ui.qrcodes.QrCodeFragmentViewModelFactory
import kotlinx.coroutines.FlowPreview

class MailFragment : Fragment() {

    private val viewModel: MailViewModel by viewModels {
        val qrCodeRepository = QrCodeRepository(
                QrCodeDataSource(
                        QrCodeDatabase.provideDatabase(requireContext()).qrCodeDao()
                )
        )
        QrCodeFragmentViewModelFactory(qrCodeRepository)
    }
    private lateinit var binding: MailFragmentBinding

    @FlowPreview
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = MailFragmentBinding.inflate(inflater, container, false)
        val size = binding.root.context.resources.getDimensionPixelSize(R.dimen.barcode_image_size)
        binding.apply {
            createQrCodeButton.setOnClickListener { addQrCode() }
            emailInputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.upDateEMail(text) }
            subjectTextInputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.upDateSubject(text) }
            bodyTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.upDateBody(text) }
            titleTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.updateTitle(text) }
        }
        viewModel.apply {
            preview.observe(viewLifecycleOwner, { qrCodeBitmap ->
                binding.preview.setImageBitmap(qrCodeBitmap)
            })
            eMail.observe(viewLifecycleOwner, {
                viewModel.updateQrCodePreview(size)
            })
            subject.observe(viewLifecycleOwner, {
                viewModel.updateQrCodePreview(size)
            })
            body.observe(viewLifecycleOwner, {
                viewModel.updateQrCodePreview(size)
            })
//            title.observe(viewLifecycleOwner, {
//                binding.titleTextinputLayout.editText?.setText(it)
//                binding.titleTextinputLayout.editText?.post { binding.titleTextinputLayout.editText?.setSelection(it.length) }
//            })
        }
        return binding.root
    }

    private fun addQrCode() {
        binding.apply {
            var error = false
            if (titleTextinputLayout.editText?.text.isNullOrBlank()) {
                titleTextinputLayout.error = getString(R.string.mandatory_info_error)
                error = true
            }
            if (emailInputLayout.editText?.text.isNullOrBlank()) {
                emailInputLayout.error = getString(R.string.mandatory_info_error)
                error = true
            }
            if (!error) {
                val title = titleTextinputLayout.editText?.text
                viewModel.onClick(title.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideAddButton(true)
    }

}
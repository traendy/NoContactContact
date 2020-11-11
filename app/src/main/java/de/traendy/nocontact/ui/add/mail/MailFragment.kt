package de.traendy.nocontact.ui.add.mail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.MainActivity
import de.traendy.nocontact.R
import de.traendy.nocontact.databinding.MailFragmentBinding
import de.traendy.nocontact.ui.qrcodes.QrCodeFragmentViewModelFactory

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MailFragmentBinding.inflate(inflater, container, false)
        binding.createQrCodeButton.setOnClickListener { addQrCode() }
        val size = binding.root.context.resources.getDimensionPixelSize(R.dimen.barcode_image_size)
        binding.emailInputLayout.editText?.doOnTextChanged { text, start, before, count -> viewModel.updateQrCodePreview(size, email = text.toString()) }
        binding.subjectTextInputLayout.editText?.doOnTextChanged { text, start, before, count -> viewModel.updateQrCodePreview(size, subject = text.toString()) }
        binding.bodyTextinputLayout.editText?.doOnTextChanged { text, start, before, count -> viewModel.updateQrCodePreview(size, body = text.toString()) }
        viewModel.preview.observe(viewLifecycleOwner, Observer {
            binding.preview.setImageBitmap(it)
        })
        return binding.root
    }

    private fun addQrCode() {
        var error = false
        if (binding.titleTextinputLayout.editText?.text.isNullOrBlank()) {
            binding.titleTextinputLayout.error = getString(R.string.mandatory_info_error)
            error = true
        }
        if (binding.emailInputLayout.editText?.text.isNullOrBlank()) {
            binding.emailInputLayout.error = getString(R.string.mandatory_info_error)
            error = true
        }
        if (!error) {
            val title = binding.titleTextinputLayout.editText?.text
            val eMail = binding.emailInputLayout.editText?.text
            val subject = binding.subjectTextInputLayout.editText?.text
            val body = binding.bodyTextinputLayout.editText?.text
            viewModel.onClick(title, eMail, subject, body)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideAddButton(true)
    }

}
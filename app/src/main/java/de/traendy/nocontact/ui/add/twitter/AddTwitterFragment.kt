package de.traendy.nocontact.ui.add.twitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.R
import de.traendy.nocontact.databinding.AddTwitterFragmentBinding
import de.traendy.nocontact.ui.add.misc.AddQrCodeViewModel
import de.traendy.nocontact.ui.qrcodes.QrCodeFragmentViewModelFactory
import java.util.regex.Pattern

class AddTwitterFragment : DialogFragment() {

    private val viewModel: AddQrCodeViewModel by viewModels {
        val qrCodeRepository = QrCodeRepository(
            QrCodeDataSource(
                QrCodeDatabase.provideDatabase(requireContext()).qrCodeDao()
            )
        )
        QrCodeFragmentViewModelFactory(qrCodeRepository)
    }
    private lateinit var binding: AddTwitterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTwitterFragmentBinding.inflate(inflater, container, false)
        binding.createQrCodeButton.setOnClickListener {
            val name = binding.titleTextinputLayout.editText?.text.toString()
            val content = binding.twitterInputLayout.editText?.text.toString()
            viewModel.setName(name)
            viewModel.setContent(content)
            var error = false
            if (name.trim().isEmpty()) {
                binding.titleTextinputLayout.error =
                    binding.root.context.getString(R.string.mandatory_info_error)
                error = true
            }
            if (content.trim().isEmpty()) {
                binding.twitterInputLayout.error =
                    binding.root.context.getString(R.string.mandatory_info_error)
                error = true
            }
            // src: https://stackoverflow.com/questions/8650007/regular-expression-for-twitter-username
            val twitterPattern = Pattern.compile("^@?(\\w){1,15}\$")
            if (!twitterPattern.matcher(content.trim()).matches()) {
                binding.twitterInputLayout.error =
                    binding.root.context.getString(R.string.twitter_handle_wrong)
                error = true
            }
            if (!error) {
                viewModel.saveQrCode(name, removeAtAndAddUrl(content))
                dialog?.dismiss()
            }
        }
        return binding.root
    }

    private fun removeAtAndAddUrl(content: String): String {
        val temp = content.trim().replace("@", "")
        return "twitter.com/$temp"
    }

}
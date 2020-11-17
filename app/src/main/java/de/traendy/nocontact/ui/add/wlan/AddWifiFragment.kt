package de.traendy.nocontact.ui.add.wlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.MainActivity
import de.traendy.nocontact.R
import de.traendy.nocontact.databinding.AddWlanFragmentBinding
import de.traendy.nocontact.qrcode.WifiAuthType
import de.traendy.nocontact.ui.qrcodes.QrCodeFragmentViewModelFactory

class AddWifiFragment : Fragment() {

    private val viewModel: AddWifiViewModel by viewModels {
        val qrCodeRepository = QrCodeRepository(
                QrCodeDataSource(
                        QrCodeDatabase.provideDatabase(requireContext()).qrCodeDao()
                )
        )
        QrCodeFragmentViewModelFactory(qrCodeRepository)
    }
    private lateinit var binding: AddWlanFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = AddWlanFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            createQrCodeButton.setOnClickListener { addQrCode() }
            authTypeGroup.setOnCheckedChangeListener { _, checkedId -> authTypeChanged(checkedId) }
            titleTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.setTitle(text.toString()) }
            ssidTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.setSsid(text.toString()) }
            passwordTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.setPassword(text.toString()) }
            phase2MethodTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.setPhase2Method(text.toString()) }
            identitiyTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.setIdentity(text.toString()) }
            anonymousTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.setAnonymousIdentity(text.toString()) }
            eapMethodTextinputLayout.editText?.doOnTextChanged { text, _, _, _ -> viewModel.setEapMethod(text.toString()) }
            ssidSwitch.setOnCheckedChangeListener { _, isChecked -> viewModel.setSsidHidden(isChecked) }
        }
        viewModel.apply {
            titleError.observe(viewLifecycleOwner, { if (it) binding.titleTextinputLayout.error = getString(R.string.mandatory_info_error) else binding.titleTextinputLayout.error = null })
            passwordError.observe(viewLifecycleOwner, { if (it) binding.passwordTextinputLayout.error = getString(R.string.mandatory_info_error) else binding.passwordTextinputLayout.error = null })
            anonymousIdentityError.observe(viewLifecycleOwner, { if (it) binding.anonymousTextinputLayout.error = getString(R.string.mandatory_info_error) else binding.anonymousTextinputLayout.error = null })
            eapMethodError.observe(viewLifecycleOwner, { if (it) binding.eapMethodTextinputLayout.error = getString(R.string.mandatory_info_error) else binding.eapMethodTextinputLayout.error = null })
            identityError.observe(viewLifecycleOwner, { if (it) binding.identitiyTextinputLayout.error = getString(R.string.mandatory_info_error) else binding.identitiyTextinputLayout.error = null })
            ssidError.observe(viewLifecycleOwner, { if (it) binding.ssidTextinputLayout.error = getString(R.string.mandatory_info_error) else binding.ssidTextinputLayout.error = null })
            phase2MethodError.observe(viewLifecycleOwner, { if (it) binding.phase2MethodTextinputLayout.error = getString(R.string.mandatory_info_error) else binding.phase2MethodTextinputLayout.error = null })
            authTypeError.observe(viewLifecycleOwner, { if (it) binding.authTypeError.text = getString(R.string.mandatory_info_error) else binding.authTypeError.text = "" })
            qrCodeCreated.observe(viewLifecycleOwner, { it?.let { goBackInStack(it) } })
        }
        return binding.root
    }

    private fun goBackInStack(qrCodeCreated: Boolean) {
        viewModel.resetQrCodeCreated()
        if (qrCodeCreated) {
            findNavController().popBackStack()
        }
    }

    private fun authTypeChanged(checkedId: Int) {
        when (checkedId) {
            R.id.wep -> {
                setWpaEapVisible(View.GONE)
                setPasswordViewVisibility(View.VISIBLE)
                viewModel.setAuthType(WifiAuthType.WEP)
            }
            R.id.wpa -> {
                setWpaEapVisible(View.GONE)
                setPasswordViewVisibility(View.VISIBLE)
                viewModel.setAuthType(WifiAuthType.WPA)
            }
            R.id.wpaeap -> {
                setWpaEapVisible(View.VISIBLE)
                setPasswordViewVisibility(View.VISIBLE)
                viewModel.setAuthType(WifiAuthType.WPA2_EAP)
            }
            R.id.nopass -> {
                setWpaEapVisible(View.GONE)
                setPasswordViewVisibility(View.GONE)
                viewModel.setAuthType(WifiAuthType.NoPass)
            }
            else -> {
            }
        }
    }

    private fun setPasswordViewVisibility(visibility: Int) {
        binding.passwordTextinputLayout.visibility = visibility
    }

    private fun setWpaEapVisible(visibility: Int) {
        binding.apply {
            phase2MethodTextinputLayout.visibility = visibility
            identitiyTextinputLayout.visibility = visibility
            anonymousTextinputLayout.visibility = visibility
            eapMethodTextinputLayout.visibility = visibility
        }
    }

    private fun addQrCode() {
        viewModel.addQrCode()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideAddButton(true)
    }
}
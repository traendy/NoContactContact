package de.traendy.nocontact.ui.add.wlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.traendy.database.database.QrCodeDatabase
import de.traendy.database.datasource.QrCodeDataSource
import de.traendy.database.repository.QrCodeRepository
import de.traendy.nocontact.MainActivity
import de.traendy.nocontact.R
import de.traendy.nocontact.databinding.AddWlanFragmentBinding
import de.traendy.nocontact.ui.qrcodes.QrCodeFragmentViewModelFactory

class AddWlanFragment : Fragment() {

    private val viewModel: AddWlanViewModel by viewModels {
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
            ssidSwitch.setOnCheckedChangeListener { buttonView, isChecked -> ssidSwitched(buttonView, isChecked) }
        }
        return binding.root
    }

    private fun ssidSwitched(buttonView: CompoundButton?, checked: Boolean) {

    }

    private fun authTypeChanged(checkedId: Int) {
        when (checkedId) {
            R.id.wep -> {
                setWpaEapVisible(View.GONE)
                setPasswordViewVisibility(View.VISIBLE)
            }
            R.id.wpa -> {
                setWpaEapVisible(View.GONE)
                setPasswordViewVisibility(View.VISIBLE)
            }
            R.id.wpaeap -> {
                setWpaEapVisible(View.VISIBLE)
                setPasswordViewVisibility(View.VISIBLE)
            }
            R.id.nopass -> {
                setWpaEapVisible(View.GONE)
                setPasswordViewVisibility(View.GONE)
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
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideAddButton(true)
    }
}
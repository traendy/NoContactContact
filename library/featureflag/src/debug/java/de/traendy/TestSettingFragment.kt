package de.traendy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.traendy.featureflag.R
import de.traendy.featureflag.databinding.FragmentTestsettingsBinding

class TestSettingsFragment : androidx.fragment.app.Fragment() {

    interface TestSettingsListener {
        fun onFeatureToggleClicked()
        fun onTestSettingClicked()
    }

    private lateinit var binding: FragmentTestsettingsBinding

    var testSettingListener: TestSettingsListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestsettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewTestsettingsFeaturetoggle.setOnClickListener { testSettingListener?.onFeatureToggleClicked() }
        binding.formattextviewTestsettingsTestsetting.setOnClickListener { testSettingListener?.onTestSettingClicked() }
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "Test Settings"
    }
}
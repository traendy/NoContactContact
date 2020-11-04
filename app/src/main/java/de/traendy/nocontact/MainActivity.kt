package de.traendy.nocontact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.traendy.featureflag.FeatureFlag
import de.traendy.featureflag.RuntimeBehavior
import de.traendy.nocontact.databinding.ActivityMainBinding
import de.traendy.nocontact.ui.add.AddQrCodeBottomSheetDialog
import de.traendy.nocontact.ui.add.AddQrCodeDialog
import de.traendy.nocontact.ui.add.AddQrCodeFragmentDialog
import de.traendy.nocontact.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addButton.setOnClickListener { openDialog() }
    }

    private fun openDialog() {
        if(RuntimeBehavior.isFeatureEnabled(FeatureFlag.BOTTOM_SHEET_ADD_DIALOG)) {
            val dialog = AddQrCodeBottomSheetDialog.getInstance(AddQrCodeDialog())
            dialog.show(supportFragmentManager, "dialog")
        }else{
            val dialogFragment = AddQrCodeFragmentDialog.getInstance(AddQrCodeDialog())
            dialogFragment.show(supportFragmentManager, "dialog")
        }

    }
}
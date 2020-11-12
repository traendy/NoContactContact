package de.traendy.nocontact

import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import de.traendy.featureflag.FeatureFlag
import de.traendy.featureflag.RuntimeBehavior
import de.traendy.nocontact.databinding.ActivityMainBinding
import de.traendy.nocontact.ui.add.misc.AddQrCodeBottomSheetDialog
import de.traendy.nocontact.ui.add.misc.AddQrCodeDialog
import de.traendy.nocontact.ui.add.misc.AddQrCodeFragmentDialog
import de.traendy.nocontact.ui.add.twitter.AddTwitterFragment
import de.traendy.nocontact.ui.qrcodes.QrCodeFragmentDirections

const val ROTATION_45 = 45f
const val ROTATION_0 = 0f

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rotation = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            addButton.setOnClickListener { revealAddButtons() }
            miscButton.setOnClickListener { openDialog() }
            mailButton.setOnClickListener { openMailFragment() }
            twitterButton.setOnClickListener { openTwitterDialog() }
        }
    }

    private fun openTwitterDialog() {
        val dialogFragment = AddTwitterFragment()
        dialogFragment.show(supportFragmentManager, "twitterDialog")
    }

    private fun openMailFragment() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(QrCodeFragmentDirections.actionQrCodeFragmentToMailFragment())
    }

    private fun revealAddButtons() {
        switchVisibilityMailButton()
        switchVisibilityTwitterButton()
        switchVisibility(binding.miscButton)
        rotation += ROTATION_45
        rotateAddButton(rotation)
    }

    private fun switchVisibility(view: View) {
        view.apply {
            visibility = if (visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    private fun switchVisibilityTwitterButton() {
        if (RuntimeBehavior.isFeatureEnabled(FeatureFlag.TWITTER_QR_CODE)) {
            switchVisibility(binding.twitterButton)
        }
    }

    private fun switchVisibilityMailButton() {
        if (RuntimeBehavior.isFeatureEnabled(FeatureFlag.MAIL_QR_CODE)) {
            switchVisibility(binding.mailButton)
        }
    }

    private fun rotateAddButton(rotation: Float) {
        binding.addButton.apply {
            scaleType = ImageView.ScaleType.MATRIX
            imageMatrix = Matrix().apply {
                postRotate(
                    rotation,
                    (drawable.bounds.width() / 2).toFloat(),
                    (drawable.bounds.height() / 2).toFloat()
                )
            }
        }
    }

    private fun openDialog() {
        if (RuntimeBehavior.isFeatureEnabled(FeatureFlag.BOTTOM_SHEET_ADD_DIALOG)) {
            val dialog = AddQrCodeBottomSheetDialog.getInstance(AddQrCodeDialog())
            dialog.show(supportFragmentManager, "dialog")
        } else {
            val dialogFragment = AddQrCodeFragmentDialog.getInstance(AddQrCodeDialog())
            dialogFragment.show(supportFragmentManager, "dialog")
        }
    }

    fun hideAddButton(hide: Boolean) {
        binding.apply {
            if (hide) {
                addButton.visibility = View.GONE
                mailButton.visibility = View.GONE
                miscButton.visibility = View.GONE
                twitterButton.visibility = View.GONE
            } else {
                addButton.visibility = View.VISIBLE
            }
        }
        rotation = ROTATION_0
        rotateAddButton(rotation)
    }
}
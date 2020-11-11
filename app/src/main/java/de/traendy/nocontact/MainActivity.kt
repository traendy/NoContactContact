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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rotation = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addButton.setOnClickListener { revealAddButtons() }
        binding.miscButton.setOnClickListener { openDialog() }
        binding.mailButton.setOnClickListener { openMailFragment() }
        binding.twitterButton.setOnClickListener { openTwitterDialog() }
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
        if (RuntimeBehavior.isFeatureEnabled(FeatureFlag.MAIL_QR_CODE)) {
            if (binding.mailButton.visibility == View.VISIBLE) {
                binding.mailButton.visibility = View.GONE
            } else {
                binding.mailButton.visibility = View.VISIBLE
            }
        }
        if (RuntimeBehavior.isFeatureEnabled(FeatureFlag.TWITTER_QR_CODE)) {
            if (binding.twitterButton.visibility == View.VISIBLE) {
                binding.twitterButton.visibility = View.GONE
            } else {
                binding.twitterButton.visibility = View.VISIBLE
            }
        }

        if (binding.miscButton.visibility == View.VISIBLE) {
            binding.miscButton.visibility = View.GONE
        } else {
            binding.miscButton.visibility = View.VISIBLE
        }
        rotation += 45f
        rotateAddButton(rotation)
    }

    private fun rotateAddButton(rotation: Float) {
        val matrix = Matrix()
        binding.addButton.scaleType = ImageView.ScaleType.MATRIX

        matrix.postRotate(
            rotation,
            (binding.addButton.drawable.bounds.width() / 2).toFloat(),
            (binding.addButton.drawable.bounds.height() / 2).toFloat()
        )
        binding.addButton.imageMatrix = matrix
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
        if (hide) {
            binding.addButton.visibility = View.GONE
            binding.mailButton.visibility = View.GONE
            binding.miscButton.visibility = View.GONE
            binding.twitterButton.visibility = View.GONE
        } else {
            binding.addButton.visibility = View.VISIBLE
        }
        rotation = 0f
        rotateAddButton(rotation)
    }
}
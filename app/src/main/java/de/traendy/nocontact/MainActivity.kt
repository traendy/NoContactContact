package de.traendy.nocontact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.traendy.nocontact.databinding.ActivityMainBinding
import de.traendy.nocontact.ui.add.AddQrCodeFragment
import de.traendy.nocontact.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.addButton.setOnClickListener { openDialog() }
    }

    private fun openDialog() {
        val dialog = AddQrCodeFragment()
        dialog.show(supportFragmentManager, "dialog")
    }
}
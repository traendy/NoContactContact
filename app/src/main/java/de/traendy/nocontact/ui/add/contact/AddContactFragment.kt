package de.traendy.nocontact.ui.add.contact

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.traendy.nocontact.MainActivity
import de.traendy.nocontact.databinding.AddContactFragmentBinding

const val REQUEST_SELECT_CONTACT = 1

class AddContactFragment : Fragment() {
    // https://developer.android.com/guide/components/intents-common#Contacts
    companion object {
        fun newInstance() = AddContactFragment()
    }

    private lateinit var contentResolver: ContentResolver

    private lateinit var viewModel: AddContactViewModel

    private lateinit var binding: AddContactFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = AddContactFragmentBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener { selectContact(requireActivity()) }
        return binding.root
    }

    private fun selectContact(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
        }
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val contactUri: Uri? = data?.data
            contactUri?.run {
                activity.apply {
                    contentResolver.query(contactUri, projection, null, null, null).use { cursor ->
                        // If the cursor returned is valid, get the phone number
                        cursor?.apply {
                            if (moveToFirst()) {
                                val numberIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                val number = getString(numberIndex)

                            }
                        }
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideAddButton(true)
    }
}

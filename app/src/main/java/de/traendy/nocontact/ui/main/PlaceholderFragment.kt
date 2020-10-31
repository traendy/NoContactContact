package de.traendy.nocontact.ui.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.traendy.nocontact.R

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setTitle(getString(arguments?.getInt(ARG_SECTION_TITLE)?:0))
            context?.let {
                setDrawable(ContextCompat.getDrawable(it,arguments?.getInt(ARG_SECTION_DRAWABLE) ?:0))
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        val imageView: ImageView = root.findViewById(R.id.imageView)
        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })
        pageViewModel.drawable.observe(this, Observer<Drawable?>{
            imageView.setImageDrawable(it)
        })
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_TITLE = "section_title"
        private const val ARG_SECTION_DRAWABLE = "section_drawable"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(titleRes: Int, drawableRes:Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_TITLE, titleRes)
                    putInt(ARG_SECTION_DRAWABLE, drawableRes)
                }
            }
        }
    }
}
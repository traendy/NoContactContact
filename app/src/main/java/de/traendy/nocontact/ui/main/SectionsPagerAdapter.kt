package de.traendy.nocontact.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import de.traendy.nocontact.R

private val TAB_TITLES = arrayOf(
    Pair(R.string.eos, R.drawable.eos),
    Pair(R.string.jobs, R.drawable.jobs),
    Pair(R.string.hod, R.drawable.hod),
    Pair(R.string.devOps, R.drawable.dev_ops),
    Pair(R.string.java, R.drawable.java),
    Pair(R.string.php, R.drawable.php),
    Pair(R.string.system_engineer, R.drawable.system_engineer),
    Pair(R.string.fullstack, R.drawable.fullstack),
    Pair(R.string.student, R.drawable.student),
    Pair(R.string.linkedIn, R.drawable.linked_in)
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(TAB_TITLES[position].first,
            TAB_TITLES[position].second)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position].first)
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}
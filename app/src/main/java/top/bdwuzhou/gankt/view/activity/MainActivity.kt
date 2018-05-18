package top.bdwuzhou.gankt.view.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.view.fragment.MainFragment
import top.bdwuzhou.gankt.view.fragment.OtherFragment
import top.bdwuzhou.gankt.view.fragment.callback.OnFragmentInteractionListener

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener{

    private lateinit var mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setupViewPager()
    }

    private fun setupNavigation() {
        mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    mVpContainer.setCurrentItem(0, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    mVpContainer.setCurrentItem(1, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    mVpContainer.setCurrentItem(2, true)
                    return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setupViewPager() {
        mVpContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                navigation.selectedItemId = when (position) {
                    0 -> R.id.navigation_home
                    1 -> R.id.navigation_dashboard
                    2 -> R.id.navigation_notifications
                    else -> R.id.navigation_home
                }
            }

        })
        mVpContainer.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int = 3

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> MainFragment.newInstance(2)
                    else -> OtherFragment.newInstance("", "")
                }
            }
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
    }
}

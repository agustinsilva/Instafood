package ar.com.instafood.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import ar.com.instafood.fragments.CheckFragment
import ar.com.instafood.fragments.MainFragment
import ar.com.instafood.fragments.MenuFragment
import ar.com.instafood.fragments.OrderFragment
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.toolbar.*
import android.R.attr.fragment



class MainActivity : AppCompatActivity() {

    private val mainFragment : MainFragment
    private var menuFragment : MenuFragment
    private val checkFragment : CheckFragment
    private val orderFragment : OrderFragment

    init {
        mainFragment = MainFragment()
        menuFragment = MenuFragment()
        checkFragment = CheckFragment()
        orderFragment = OrderFragment()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = supportFragmentManager.beginTransaction()

        if (item.itemId == R.id.navigation_menu) {
            menuFragment = MenuFragment()
        }
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        when (item.itemId){
            R.id.navigation_home -> transaction.replace(R.id.fragment_container, mainFragment)
            R.id.navigation_menu -> transaction.replace(R.id.fragment_container, menuFragment, menuFragment.tag).addToBackStack(null)
            R.id.navigation_check -> transaction.replace(R.id.fragment_container, checkFragment)
            R.id.navigation_order -> transaction.replace(R.id.fragment_container, orderFragment)
        }
        transaction.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        SetActionBar()
        initialise()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, mainFragment)

        transaction.commit()
    }

    private fun initialise() {
        //menuFragment.menuViewPager = findViewById(R.id.menuViewPager)

    }

    private fun SetActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Menu")
    }
}
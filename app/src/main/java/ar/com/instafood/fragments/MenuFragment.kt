package ar.com.instafood.fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ar.com.instafood.activities.R
import ar.com.instafood.adapters.MenuTabsAdapter
import ar.com.instafood.fragments.menuFragments.DishFragment
import ar.com.instafood.fragments.menuFragments.DrinkFragment
import ar.com.instafood.fragments.menuFragments.SnackFragment
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MenuFragment : Fragment() {
    var listFragment = arrayListOf<Fragment>()
    var listTitle = arrayListOf<String>()
    var menuViewPager: ViewPager? = null
    var menuTabLayout: TabLayout? = null
    var menuAdapter : MenuTabsAdapter? = null

    init {
        listFragment?.clear()
        listTitle?.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        initialise()
        prepareDataResource()

        var view = inflater.inflate(R.layout.fragment_menu, container, false)
        menuViewPager = view.findViewById(R.id.menuViewPager)
        menuTabLayout = view.findViewById(R.id.menuTabs)

        if(menuAdapter == null) {
            menuAdapter = MenuTabsAdapter(fragmentManager!!, listFragment, listTitle)
        }
        menuViewPager!!.adapter = menuAdapter

        menuTabLayout!!.setupWithViewPager(menuViewPager)



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.onDestroy()
    }

    private fun initialise() {
        activity?.toolbar?.setTitle("InstaFood - Menu")
        listTitle.clear()
        listFragment.clear()
    }

    private fun prepareDataResource() {
        addData(DishFragment(),"Platos Principales")
        addData(SnackFragment(),"Platos Secundarios")
        addData(DrinkFragment(),"Postres y Bebidas")
    }

    private fun addData(fragment : Fragment, title : String ){
        listFragment.add(fragment)
        listTitle.add(title)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(outState)
    }
}
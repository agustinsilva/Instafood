package ar.com.instafood.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ar.com.instafood.activities.R
import ar.com.instafood.adapters.CheckAdapter
import ar.com.instafood.adapters.DynamicCheckAdapter
import ar.com.instafood.application.SocketApplication
import ar.com.instafood.models.Check
import ar.com.instafood.models.getSampleCheck
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_check.view.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 *
 */
class CheckFragment : Fragment() {

    private var checks: ArrayList<Check>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_check, container, false)
        val app  = activity?.application as SocketApplication

        var array: ArrayList<Check> = arrayListOf()
        app.socket?.emit("getProducts");
        app.socket?.on("products", { args -> run {
            activity?.runOnUiThread(
                    {
                        val jsonElement = JsonParser().parse((args[0] as JSONObject).toString())
                        checks = arrayListOf(Gson().fromJson(jsonElement, Check::class.java))
                        var mDynamicListAdapter = DynamicCheckAdapter()
                        mDynamicListAdapter.setFirstList(checks!!)
                        view.cardList.setHasFixedSize(true)
                        view.cardList.layoutManager = LinearLayoutManager(context)
                        view.cardList.adapter = mDynamicListAdapter
                    }
            )

        }})
        // Initialize the list

        //mDynamicListAdapter.setSecondList(checks!!.get(1))

        return view
    }

    private fun initialize() {
    }


}

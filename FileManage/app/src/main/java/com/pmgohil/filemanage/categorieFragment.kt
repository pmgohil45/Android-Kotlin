package com.pmgohil.filemanage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class categorieFragment : Fragment() {

    lateinit var constrainLayoutPhoto: ConstraintLayout
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val s = Intent(this@MainActivity, syllabus::class.java)
        startActivity(s)*/

        val let = arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.categorie_fragment, container, false)

        constrainLayoutPhoto = view.findViewById(R.id.constraintLayoutPhoto)
        constrainLayoutPhoto.setOnClickListener() {
            activity?.let {
                val APA = Intent(it, allPhotoActivity::class.java)
                it.startActivity(APA)
            }
        }

        //this 2 line used for back code
        /*val btn = view.findViewById<Button>(R.id.button2)
        btn.setOnClickListener() {
            val fragment: Fragment? =
                requireActivity().supportFragmentManager.findFragmentByTag("fragment")
            requireActivity().supportFragmentManager.beginTransaction().remove(fragment!!).commit();
        }*/

        return view
    }

}
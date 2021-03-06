package com.hms.demo.clouddbmapdemo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup




class MapFragment : Fragment() {
    lateinit var listener:OnMapInteractionListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnMapInteractionListener){
            listener=context
        }
        else throw Exception("${context.javaClass.name} must implement MapFragment.OnMapInteractionListener")
    }

    public interface OnMapInteractionListener{
        fun onMapReady()
    }
}

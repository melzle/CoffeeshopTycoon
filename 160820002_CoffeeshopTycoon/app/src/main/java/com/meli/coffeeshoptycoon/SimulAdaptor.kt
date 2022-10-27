package com.meli.coffeeshoptycoon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_simul_layout.view.*

class SimulAdaptor (val context: Context): RecyclerView.Adapter<SimulAdaptor.SimulationViewHolder>() {
    class SimulationViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimulationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.activity_simul_layout, parent, false)
        return SimulationViewHolder(v)
    }

    override fun getItemCount(): Int {
        return Global.simulation.size
    }

    override fun onBindViewHolder(holder: SimulationViewHolder, position: Int) {
        with(Global.simulation[position]){
            holder.v.txtTime.text = time
            holder.v.txtCust.text = customer
        }
    }

}
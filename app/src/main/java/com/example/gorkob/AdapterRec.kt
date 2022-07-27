package com.example.gorkob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gorkob.databinding.ElRecBinding
import com.squareup.picasso.Picasso

class Adapter(val clickListener: ClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var ListInAdapter= ArrayList<mParty>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.el_rec,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position],clickListener)
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElRecBinding.bind(itemView)
        fun bind(party: mParty, clickListener: ClickListener){
            binding.elName.text=party.name
            binding.elData.text=party.data

            Picasso.get().load(party.link).fit().into(binding.elImage)

            itemView.setOnClickListener{

                clickListener.onClick(party)
            }

        }
    }
    fun loadListToAdapter(productList:ArrayList<mParty>){
        ListInAdapter= productList
        notifyDataSetChanged()
    }

    interface ClickListener{
        fun onClick(nomer: mParty){

        }
    }
    fun deleteItem(i:Int):String?{
        var id=ListInAdapter.get(i).id

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }

}
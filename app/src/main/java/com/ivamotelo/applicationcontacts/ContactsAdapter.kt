package com.ivamotelo.applicationcontacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter (val contactsList: ArrayList<Contacts>): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view, parent, false)
        return ViewHolder(view)
    }

    /**
     * Recebe cada item da posição atual e passa para  a função bindItem(contacts: Contacts)
     * que preenche os TextView com o nome e telefone do contato atual [position]
     */
    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.bindItem(contactsList[position])
    }

    override fun getItemCount(): Int {
       return contactsList.size
    }

    /**
     * Criação da View com os itens nome e telefone do contato, que irá dentro da recyclerView
     */
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(contacts: Contacts){
            val txtName = itemView.findViewById<TextView>(R.id.txt_contact_name)
            val txtPhone = itemView.findViewById<TextView>(R.id.txt_contact_phone)

            txtName.text = contacts.name
            txtPhone.text = contacts.phoneNumber


        }
    }
}
package com.caiosilva.myapplication.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.model.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contact_item_rv.view.*

class ContactsAdapter (private val contactsList: ArrayList<User>, private val applicationContext: Context)
    : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private val contacts = this.contactsList
    private val context = this.applicationContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.contact_item_rv, parent, false)

        view.setOnClickListener {
            Log.d("Caio", ViewHolder(view).name.text.toString())
        }

        return ViewHolder(view)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = contacts[position]
        holder.name.text = user.name
        holder.email.text = user.email
        if(user.photo != null) {
            val uri = Uri.parse(user.photo)
            Glide.with(context).load(uri).into(holder.photo)
        } else {
            holder.photo.setImageResource(R.drawable.default_avatar_png)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: CircleImageView = itemView.contact_list_profile_iv
        val name: TextView = itemView.contact_list_profile_tv
        val email: TextView = itemView.contact_list_email_tv
    }
}
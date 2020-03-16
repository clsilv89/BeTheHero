package com.caiosilva.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.adapter.ContactsAdapter
import com.caiosilva.myapplication.config.FirebaseConfig
import com.caiosilva.myapplication.helper.FirebaseUser
import com.caiosilva.myapplication.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

private lateinit var contactsAdapter: ContactsAdapter
private lateinit var contatos: ArrayList<User>
private lateinit var ref: DatabaseReference
private lateinit var valueEventListener: ValueEventListener
private lateinit var currentUser : com.google.firebase.auth.FirebaseUser

class ContactsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        currentUser = FirebaseUser().getCurrentUser()!!


        ref = FirebaseConfig().getFirebaseDatabase().child("users")

        contatos = ArrayList()

        contactsAdapter = ContactsAdapter(contatos, activity!!.applicationContext)

        val listaContatosrv = view?.findViewById<RecyclerView>(R.id.contact_list_rv)

        listaContatosrv?.adapter = contactsAdapter
        listaContatosrv?.layoutManager = LinearLayoutManager(activity?.applicationContext)
        listaContatosrv?.hasFixedSize()

        return view
    }

    override fun onStart() {
        super.onStart()
        getContacts()
    }

    override fun onStop() {
        super.onStop()
        ref.removeEventListener(valueEventListener)
    }

    private fun getContacts() {
        valueEventListener = ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for(i in p0.children) {
                    val user = i.getValue(User::class.java)
                    if(currentUser.uid != user?.id) {
                        contatos.add(user!!)
                    }
                }
                contactsAdapter.notifyDataSetChanged()
            }
        })
    }
}

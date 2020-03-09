package com.caiosilva.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.config.FirebaseConfig
import com.caiosilva.myapplication.fragments.ContactsFragment
import com.caiosilva.myapplication.fragments.MessagesFragment
import com.google.firebase.auth.FirebaseAuth
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class MainActivity : AppCompatActivity() {

    private lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.title = "WhatsApp"
        setSupportActionBar(toolbar)

        val fragmentPagerItemAdapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add("Mensagens", MessagesFragment::class.java)
                .add("Contatos", ContactsFragment::class.java)
                .create()
        )

        val viewPager: ViewPager = findViewById(R.id.viewpager)
        viewPager.adapter = fragmentPagerItemAdapter

        val viewPagerTab: SmartTabLayout = findViewById(R.id.view_pager_tab)
        viewPagerTab.setViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_logout -> logOut()
            R.id.menu_settings -> Log.d("Caio", "Configs")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        authentication = FirebaseConfig().getFirebaseAuth()

        try {
            authentication.signOut()
            finish()
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}

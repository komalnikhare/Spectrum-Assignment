package com.example.spectrumapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.restcountries.UI.viewmodels.ViewModelFactory
import com.example.spectrumapp.db.entities.CompanyEntity
import com.example.spectrumapp.viewmodels.CompanyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ArrayAdapter
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import com.example.spectrumapp.db.entities.MemberEntity
import kotlinx.android.synthetic.main.member_item.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), ItemClickListener {

    var companies = ArrayList<CompanyEntity>()
    var listAdapter: CompanyListAdapter? = null
    lateinit var viewModel: CompanyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Company List"

        val factory = ViewModelFactory(this.application,1)
        viewModel =  ViewModelProviders.of(this, factory).get(CompanyViewModel::class.java)

        viewModel.getCompanies()?.observe(this, Observer { companyList ->

            if (companyList != null) {
                companies = companyList as ArrayList<CompanyEntity>
                listAdapter?.setList(companies)
                listAdapter?.notifyDataSetChanged()
            }
        })

        setupRecyclerView()

        searchBar.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                viewModel.getCompanyByName("${searchBar.text.toString()}%")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    private fun setupRecyclerView() {

        if (listAdapter == null) {
            listAdapter = CompanyListAdapter(this, companies, this)
            companyRecyclerView.apply {
                adapter = listAdapter
            }

        } else {
            listAdapter?.notifyDataSetChanged()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.sort_switch -> showDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog(){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Sort List")

        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        arrayAdapter.add("Ascending")
        arrayAdapter.add("Descending")

        dialog.setNegativeButton("cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

        dialog.setAdapter(arrayAdapter,
            DialogInterface.OnClickListener { dialog, which ->
                val strName = arrayAdapter.getItem(which)
               if (strName=="Ascending")
                   viewModel.getCompanyByAscending()
                else viewModel.getCompanyByDescending()

                dialog.dismiss()
            })
        dialog.show()
    }

    override fun onItemClicked(which: Int, id: String, value: Boolean) {
       when(which){
           1 -> viewModel.companyFavorite(id, value)
           2 -> viewModel.companyFollow(id, value)
       }
    }

}

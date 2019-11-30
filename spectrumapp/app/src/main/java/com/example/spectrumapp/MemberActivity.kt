package com.example.spectrumapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.restcountries.UI.viewmodels.ViewModelFactory
import com.example.spectrumapp.db.entities.CompanyEntity
import com.example.spectrumapp.db.entities.MemberEntity
import com.example.spectrumapp.viewmodels.CompanyViewModel
import com.example.spectrumapp.viewmodels.MemberViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_member.*

class MemberActivity : AppCompatActivity(), ItemClickListener {

    var members = ArrayList<MemberEntity>()
    var listAdapter: MemberListAdapter? = null
    lateinit var viewModel: MemberViewModel
    var companyId: String? = null
    var isAll: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar!!.title = "Members"

        companyId = intent.extras.getString("companyId")
        val factory = ViewModelFactory(this.application, 2, companyId as String)
        viewModel = ViewModelProviders.of(this, factory).get(MemberViewModel::class.java)

        viewModel.getMembers()?.observe(this, Observer { memberList ->
            if (memberList != null) {
                members = memberList as ArrayList<MemberEntity>
                listAdapter?.setList(members)
                listAdapter?.notifyDataSetChanged()
            }
        })

        setupRecyclerView()

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isAll)
                    viewModel.getMemberByName("${searchBar.text.toString()}%")
                else
                    viewModel.getMemberByNameForCompany(
                        companyId!!,
                        "${searchBar.text.toString()}%"
                    )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        btnSort.setOnClickListener {
            showDialog()
        }

    }

    private fun setupRecyclerView() {

        if (listAdapter == null) {
            listAdapter = MemberListAdapter(this, members, this)
            recyclerView.apply {
                adapter = listAdapter
            }

        } else {
            listAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_member, menu)
        val sortSwitch = menu?.findItem(R.id.sort_switch)
        sortSwitch?.setActionView(R.layout.menu_item)
        val switch = sortSwitch?.actionView?.findViewById<Switch>(R.id.switchSort)
        switch?.setOnCheckedChangeListener { buttonView, isChecked ->
            isAll = isChecked
            if (isChecked)
                viewModel.getAllMembers()
            else viewModel.getMembersByCompany(companyId!!)
        }
        return true
    }


    override fun onItemClicked(which: Int, id: String, value: Boolean) {
        viewModel.memberFavorite(id, value)
    }


    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_view)

        val btnOk = dialog.findViewById<Button>(R.id.ok)
        val btnCancel = dialog.findViewById<Button>(R.id.cancel)

        val radioTop = dialog.findViewById<RadioGroup>(R.id.top)
        val radioBottom = dialog.findViewById<RadioGroup>(R.id.bottom)

        var byName = true
        var ascending = true

        radioTop.setOnCheckedChangeListener { group, checkedId ->
            byName = checkedId == R.id.name
        }

        radioBottom.setOnCheckedChangeListener { group, checkedId ->
            ascending = checkedId == R.id.ascending
        }
        btnOk.setOnClickListener {

            if (byName) {
                if (isAll) viewModel.getMemberByName(ascending)
                else viewModel.getMemberByName(companyId!!, ascending)

            } else {
                if (isAll) viewModel.getMemberByAge(ascending)
                else viewModel.getMemberByAge(companyId!!, ascending)
            }

            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}

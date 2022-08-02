package com.nusratillo.testtask.ui.user

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.nusratillo.testtask.R
import com.nusratillo.testtask.data.convertLongToDateString
import com.nusratillo.testtask.data.model.User
import com.nusratillo.testtask.databinding.ActivityUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private val viewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        observeData()
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_device, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.update_menu -> {
                viewModel.updateUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun observeData() {
        viewModel.user.observe(this, ::observeUser)
        viewModel.message.observe(this, ::showMessage)
        viewModel.fieldEmptyAction.observe(this, ::observeFieldsEmptyAction)
        viewModel.showDatePickerAction.observe(this, ::showDatePicker)
    }

    private fun observeUser(user: User) {
        with(binding) {
            firstNameEt.setText(user.firstName)
            lastNameEt.setText(user.lastName)
            birthDateTv.text = getString(
                R.string.birth_date,
                user.birthDate.convertLongToDateString()
            )
            cityEt.setText(user.address.city)
            postalCodeEt.setText(user.address.postalCode.toString())
            streetEt.setText(user.address.street)
            streetCodeEt.setText(user.address.streetCode)
            countryEt.setText(user.address.country)
        }
    }

    private fun initListeners() {
        with(binding) {
            firstNameEt.addTextChangedListener {
                viewModel.setField(FieldState(FieldType.FIRST_NAME, it.toString()))
            }
            lastNameEt.addTextChangedListener {
                viewModel.setField(FieldState(FieldType.LAST_NAME, it.toString()))
            }
            cityEt.addTextChangedListener {
                viewModel.setField(FieldState(FieldType.CITY, it.toString()))
            }
            postalCodeEt.addTextChangedListener {
                viewModel.setField(FieldState(FieldType.POSTAL_CODE, it.toString()))
            }
            streetEt.addTextChangedListener {
                viewModel.setField(FieldState(FieldType.STREET, it.toString()))
            }
            streetCodeEt.addTextChangedListener {
                viewModel.setField(FieldState(FieldType.STREET_CODE, it.toString()))
            }
            countryEt.addTextChangedListener {
                viewModel.setField(FieldState(FieldType.COUNTRY, it.toString()))
            }
            birthDateTv.setOnClickListener {
                viewModel.birthDateOnClick()
            }
        }
    }

    private fun observeFieldsEmptyAction(actions: List<FieldEmptyAction>) {
        actions.forEach { action ->
            val message = getString(action.messageId)
            when (action.type) {
                FieldType.FIRST_NAME -> binding.firstNameTil.error = message
                FieldType.LAST_NAME -> binding.lastNameTil.error = message
                FieldType.CITY -> binding.cityTil.error = message
                FieldType.POSTAL_CODE -> binding.postalCodeTil.error = message
                FieldType.STREET -> binding.streetTil.error = message
                FieldType.STREET_CODE -> binding.streetCodeTil.error = message
                FieldType.COUNTRY -> binding.countryTil.error = message
            }
        }
    }

    private fun showMessage(@StringRes message: Int) {
        Toast.makeText(this@UserActivity, getString(message), Toast.LENGTH_SHORT).show()
    }

    private fun showDatePicker(date: Triple<Int, Int, Int>) {
        DatePickerDialog(
            this@UserActivity,
            { _, year, month, day ->
                viewModel.birthDateOnSelect(Triple(year, month, day))
            },
            date.first, date.second, date.third
        ).show()
    }

    companion object {
        fun openUserActivity(context: Context) {
            val intent = Intent(context, UserActivity::class.java)
            context.startActivity(intent)
        }
    }
}
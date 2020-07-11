package com.prasetyanurangga.kamar.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prasetyanurangga.kamar.KamarApplication
import com.prasetyanurangga.kamar.R
import com.prasetyanurangga.kamar.adapter.UserAdapter
import com.prasetyanurangga.kamar.database.model.User
import com.prasetyanurangga.kamar.databinding.ActivityMainBinding
import com.prasetyanurangga.kamar.di.factory.UserViewModelFactory
import com.prasetyanurangga.kamar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>

    private var userAdapter: UserAdapter? = null

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDagger()
        createViewModel()
        setBinding()
        observerViewModel()
        bottomSheetBehavior = BottomSheetBehavior
            .from(bottom_sheet_layout)

        bottomSheetBehavior.peekHeight = 0

        Log.e("tinggi", bottomSheetBehavior.expandedOffset.toString())

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior
        .BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bg.visibility = View.VISIBLE
                bg.alpha = slideOffset

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                hideKeyboard()
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    hideBottomSheet()
                } else {
                    val interpolar: OvershootInterpolator = OvershootInterpolator(10.0F)

                    ViewCompat.animate(btn_add)
                        .rotation(45.0F)
                        .withLayer()
                        .setDuration(300)
                        .setInterpolator(interpolar)
                        .start()
                }
            }
        })

        //addSampleUsersToDatabase()
        onSaveClick()

        bg.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }


    }

    fun hideBottomSheet() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        bg.visibility = View.GONE
        txt_id.setText("")
        et_email.setText("")
        et_nama.setText("")
        val interpolar: OvershootInterpolator = OvershootInterpolator(10.0F)

        ViewCompat.animate(btn_add)
            .rotation(0.0F)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolar)
            .start()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun createViewModel() {
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    private fun injectDagger() {
        KamarApplication.instance.appComponent.inject(this)
    }

    private fun observerViewModel() {
        userViewModel.isLoading = true
        userViewModel.getAllUser.observe(this, Observer { list ->
            if (!isDestroyed) {
                Log.e("iniloh", list.toString());
                userViewModel.isLoading = false
                showList(list)
            }
        })
    }

    private fun showList(userList: List<User>) {
        if (userAdapter == null) {
            recycler_view_books.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            userAdapter = UserAdapter(
                userList,
                { user: User? -> userClickItem(user!!) },
                { user: User? -> btnEditClick(user!!) },
                { user: User? -> btnDeleteClick(user!!) })
            recycler_view_books.adapter = userAdapter
        } else {
            userAdapter?.updateUser(userList)
            userAdapter?.notifyDataSetChanged()
        }
    }

    private fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }


    private fun userClickItem(user: User) {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
            Log.e("onclick", user.name)
        }

    }

    private fun btnDeleteClick(user: User) {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
            userViewModel.deleteUser(user).observe(this, Observer { list ->
                if (!isDestroyed) {
                    Log.e("iniloh", list.toString());
                    userViewModel.isLoading = false
                    showList(list)
                }
            })
        }

    }

    private fun btnEditClick(user: User) {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
            et_email.setText(user.email)
            et_nama.setText(user.name)
            txt_id.setText(user.uid.toString())
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        }

    }

    fun onSaveClick() {
        btn_save.setOnClickListener {

            if (txt_id.text.isNullOrEmpty() && et_email.text!!.isNullOrBlank() && et_nama.text!!.isNotEmpty()) {
                val uid = (recycler_view_books.adapter?.itemCount) ?: 0
                val user = User(email = et_email.text.toString(), name = et_nama.text.toString(), uid = uid + 1)
                userViewModel.saveUser(user).observe(this, Observer { list ->
                    if (!isDestroyed) {
                        Log.e("iniloh", user.toString());
                        userViewModel.isLoading = false
                        showList(list)
                    }
                })
            } else if (et_email.text!!.isNotEmpty() && et_nama.text!!.isNotEmpty()) {
                val user = User(
                    email = et_email.text.toString(),
                    name = et_nama.text.toString(),
                    uid = txt_id.text.toString().toInt()
                )
                userViewModel.updateUser(user).observe(this, Observer { list ->
                    if (!isDestroyed) {
                        Log.e("iniloh", list.toString());
                        userViewModel.isLoading = false
                        showList(list)
                    }
                })
            }
            hideBottomSheet()
        }
        btn_add.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior
                    .STATE_COLLAPSED
            ) {
                bottomSheetBehavior.state = BottomSheetBehavior
                    .STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior
                    .STATE_COLLAPSED
            }
        }

    }


}

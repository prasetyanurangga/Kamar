package com.prasetyanurangga.kamar.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.kamar.KamarApplication
import com.prasetyanurangga.kamar.R
import com.prasetyanurangga.kamar.adapter.UserAdapter
import com.prasetyanurangga.kamar.database.model.User
import com.prasetyanurangga.kamar.di.factory.UserViewModelFactory
import com.prasetyanurangga.kamar.viewmodel.UserViewModel
import com.prasetyanurangga.kamar.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private var userAdapter : UserAdapter? = null

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDagger()
        createViewModel()
        setBinding()
        observerViewModel()
        //addSampleUsersToDatabase()
        onSaveClick()
    }

    private fun createViewModel()
    {
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    private fun injectDagger()
    {
        KamarApplication.instance.appComponent.inject(this)
    }

    private fun observerViewModel()
    {
        userViewModel.isLoading = true
        userViewModel.getAllUser.observe(this, Observer {
            list ->
                if (!isDestroyed)
                {
                    Log.e("iniloh",list.toString());
                    userViewModel.isLoading = false
                    showList(list)
                }
        })
    }

    private fun showList(userList: List<User>)
    {
        if(userAdapter == null)
        {
            recycler_view_books.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            userAdapter = UserAdapter(userList)
            recycler_view_books.adapter = userAdapter
        }
        else
        {
            userAdapter?.updateUser(userList)
            userAdapter?.notifyDataSetChanged()
        }
    }

    private fun setBinding()
    {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }


    private var simpleItemCallback: ItemTouchHelper.SimpleCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT)
            {
                if (viewHolder is UserAdapter.UserViewHolder)
                {
                    userViewModel.deleteUser(viewHolder.dataBinding.user as User).observe(this@MainActivity, Observer {
                            list ->

                            Log.e("iniloh",list.toString());
                            userViewModel.isLoading = false
                            showList(list)
                    })

                }
            }
        }
    }

    fun onSaveClick()
    {
        btn_save.setOnClickListener {
            val uid = (recycler_view_books.adapter?.itemCount) ?: 0
            val user = User(uid + 1, et_email.text.toString(), et_nama.text.toString())
            userViewModel.saveUser(user).observe(this, Observer {
                    list ->
                if (!isDestroyed)
                {
                    Log.e("iniloh",list.toString());
                    userViewModel.isLoading = false
                    showList(list)
                }
            })
        }

    }


}

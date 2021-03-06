package com.prasetyanurangga.kamar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.kamar.BR
import com.prasetyanurangga.kamar.R
import com.prasetyanurangga.kamar.database.model.User
import com.prasetyanurangga.kamar.databinding.ListItemUserBinding
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserAdapter(
    private var userList: List<User>?,
    private var listener: (User?) -> Unit,
    private var listenerEdit: (User?) -> Unit,
    private var listenerDelete: (User?) -> Unit
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        return UserViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_user, parent, false))
    }

    fun updateUser(newUser: List<User>)
    {
        userList = newUser
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.dataBinding.setVariable(BR.user, userList?.get(position))
        holder.itemView.btn_edit.setOnClickListener {
            listenerEdit(userList?.get(position))
        }
        holder.itemView.btn_delete.setOnClickListener {
            listenerDelete(userList?.get(position))
        }
        holder.itemView.setOnClickListener{
            listener(userList?.get(position))
        }

    }

    class UserViewHolder(binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        var dataBinding = binding
    }



}
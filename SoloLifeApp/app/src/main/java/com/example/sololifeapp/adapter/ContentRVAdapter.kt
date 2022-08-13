package com.example.sololifeapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sololifeapp.R
import com.example.sololifeapp.contentsList.ContentModel
import com.example.sololifeapp.contentsList.ContentShowActivity
import com.example.sololifeapp.util.FBAuth
import com.example.sololifeapp.util.FBRef

class ContentRVAdapter(private val item : ArrayList<ContentModel>, val context : Context, val keyList : ArrayList<String>) : RecyclerView.Adapter<ContentRVAdapter.ViewHolder>() {

    // 리사이클러 뷰 아이템 클릭 방법 1
    // RecyclerView Item Click Event
    /*
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null
     */

    // 전체 아이템을 가져와서 아이템 하나씩 하나의 레이아웃(content_rv_item)으로 만들어줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ContentRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return ViewHolder(v)
    }
    // 아이템들의 내용물들을 inner class ViewHolder 에서 하나, 하나씩 넣을 수 있게 연결을 해주는 역할
    override fun onBindViewHolder(holder : ContentRVAdapter.ViewHolder, position: Int) {
        // 리사이클러 뷰 아이템 클릭 방법 1
        /*
       if(itemClick != null) {
           holder.itemView.setOnClickListener { v ->
               itemClick?.onClick(v, position)

           }
       }
        */

        holder.bindItems(item[position], keyList[position])
    }

    // 전체 아이템의 개수
    override fun getItemCount() = item.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // activity_contents_list.xml 에 데이터를 하나, 하나 씩 넣어주는 것
        fun bindItems(item : ContentModel, key : String) {

            // 리사이클러 뷰 아이템 클릭 방법 2
            itemView.setOnClickListener {
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url",item.webUrl) // 아이템 클릭 시 ContentShowActivity 로 url 을 보냄

                itemView.context.startActivity(intent)
            }

            // itemView = content_rv_item
            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            bookmarkArea.setOnClickListener {
                Log.d("CurrentUser", FBAuth.getUid())
                Toast.makeText(context, key, Toast.LENGTH_SHORT).show()
                
                // bookmarkRef 에 getUid 를 넣고 getUid 안에 key 값을 넣고 key 값에 value 는 good 이다
                FBRef.bookmarkRef.child(FBAuth.getUid()).child(key).setValue("Good")
            }

            // item = ContentModel
            contentTitle.text = item.title
            Glide
                .with(context)
                .load(item.imageUrl) // load 에 있는 item.imageUrl 을 into 에 입력한 imageViewArea 에 집어 넣는 다는 의미
                .into(imageViewArea)

        }
    }
}
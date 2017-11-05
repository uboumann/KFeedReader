package br.com.kfeedreader

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {

    val listItems = arrayListOf<Item>()
    lateinit var listViewItem: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = LinearLayoutManager(this)

        listViewItem = findViewById(R.id.recyclerView) as RecyclerView
        listViewItem.layoutManager = layout

        adapter = ItemAdapter(listItems, this)
        listViewItem.adapter = adapter


        PkRSS.with(this).load("https://rss.tecmundo.com.br/feed").callback(this).async()
    }

    override fun onLoadFailed() {
    }

    override fun onPreload() {
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        listItems.clear()
        newArticles?.mapTo(listItems) {
            Item(it.title, it.author, it.date, it.source, it.enclosure.url)
        }

        //adapter = ItemAdapter(listItems, this)
        adapter.notifyDataSetChanged()
    }

    data class Item(val title: String, val autor: String, val data: Long, val link:Uri, val image: String)
}

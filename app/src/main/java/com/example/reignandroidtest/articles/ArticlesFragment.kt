package com.example.reignandroidtest.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.reignandroidtest.data.Article
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.reignandroidtest.R
import java.util.ArrayList

/**
 * Created by Esteban Antillanca on 2020-01-18.
 */
class ArticlesFragment : Fragment(), ArticlesContract.View {

    override lateinit var presenter: ArticlesContract.Presenter

    private val listAdapter = ArticlesAdapter(ArrayList(0))


    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val article1 = Article("asdasdfds","2","3","4","5")
        val article2 = Article("asfasdfsdfsad","2","3","4","5")
        val article3 = Article("asdfasdfasdf","2","3","4","5")

        val dataSet = listOf(article1,article2,article3)
        listAdapter.setData(dataSet)

        val root = inflater.inflate(R.layout.articles_list_frag, container, false)
        with(root){
            val listView = findViewById<RecyclerView>(R.id.articles_list)
            listView.layoutManager = LinearLayoutManager(context)
            listView.adapter = listAdapter

            findViewById<SwipeRefreshLayout>(R.id.refresh_layout).apply{
                setColorSchemeColors(
                    ContextCompat.getColor(requireContext(), R.color.primary),
                    ContextCompat.getColor(requireContext(), R.color.accent),
                    ContextCompat.getColor(requireContext(), R.color.primary_dark)
                )

                setOnRefreshListener {presenter.loadArticles(true)}
            }
        }
        return root

    }

    override fun setLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showArticles(articles: List<Article>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingArticlesError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoArticles() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showArticleRemoved(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showArticleWebView(article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        fun newInstance() = ArticlesFragment()
    }

    class ArticlesAdapter (var articles: List<Article>) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

        val viewBindHelper = ViewBinderHelper()

        override fun getItemCount(): Int {
            return articles.size
        }

        fun setData(articles: List<Article>){
            this.articles = articles
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val article: Article = articles[position]

            viewBindHelper.setOpenOnlyOne(true)
            viewBindHelper.bind(holder.swipeRevealLayout, article.id)

            holder.bind(article)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(inflater, parent)
        }

        class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.articles_list_item, parent, false)) {

            private var mTitle: TextView? = null
            private var mAuthor: TextView? = null
            private var mCreatedAt: TextView? = null
            var swipeRevealLayout: SwipeRevealLayout? = null

            init{
                mTitle = itemView.findViewById(R.id.item_text_desc)
                mAuthor = itemView.findViewById(R.id.item_author)
                mCreatedAt = itemView.findViewById(R.id.item_text_createdAt)
                swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout)

            }

            fun bind(item: Article) {
                mTitle?.text = item.title
                if (item.title == null || item.title == "") mTitle?.text = item.storyTitle
                mAuthor?.text = item.author
                mCreatedAt?.text = item.created

            }
        }

    }



}
package com.example.reignandroidtest.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.reignandroidtest.data.Article
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.reignandroidtest.R
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

/**
 * Created by Esteban Antillanca on 2020-01-18.
 */
class ArticlesFragment : Fragment(), ArticlesContract.View {

    override lateinit var presenter: ArticlesContract.Presenter

    private val listAdapter = ArticlesAdapter(ArrayList(0))

    lateinit var viewSpinner : ProgressBar

    lateinit var root : View

    lateinit var noArticles : RelativeLayout

    lateinit var swipeRefreshLayout : SwipeRefreshLayout


    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        root = inflater.inflate(R.layout.articles_list_frag, container, false)

        with(root){

            viewSpinner = findViewById(R.id.view_progress_bar)
            noArticles = findViewById(R.id.no_articles)
            val listView = findViewById<RecyclerView>(R.id.articles_list)
            listView.layoutManager = LinearLayoutManager(context)
            val dividerItemDec = DividerItemDecoration(listView.context, LinearLayoutManager(context).orientation)
            listView.addItemDecoration(dividerItemDec)
            listView.adapter = listAdapter

            swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.refresh_layout).apply{
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
        swipeRefreshLayout.isRefreshing = active
    }

    override fun showArticles(articles: List<Article>) {
        noArticles.isVisible = false
        swipeRefreshLayout.isVisible = true
        listAdapter.clear()
        listAdapter.articles =articles.toMutableList()
        listAdapter.notifyDataSetChanged()
    }

    override fun showLoadingArticlesError() {
        Snackbar.make(root, getString(R.string.network_error),Snackbar.LENGTH_LONG).show()
        showNoArticles()
    }

    override fun showNoArticles() {
        noArticles.isVisible = true
        swipeRefreshLayout.isVisible = false
        setLoadingIndicator(false)
    }

    override fun showArticleRemoved(position: Int) {
        listAdapter.deleteAt(position)
     //   listAdapter.viewBindHelper.closeLayout(listAdapter.getItem(position).id)
        listAdapter.notifyItemRemoved(position)
        listAdapter.notifyItemRangeChanged(position, listAdapter.articles.count());
    }

    override fun showArticleWebView(article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        fun newInstance() = ArticlesFragment()
    }

   inner class ArticlesAdapter (var articles: MutableList<Article>) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

       val viewBindHelper = ViewBinderHelper()

        override fun getItemCount(): Int {
            return articles.size
        }

        fun getItem(position: Int): Article {
           return articles.get(position)
       }

        fun setData(articles: MutableList<Article>){
            this.articles = articles
        }

       fun clear(){
           articles.clear()
       }

        fun deleteAt(position: Int){

            articles.removeAt(position)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val article: Article = articles[position]

            viewBindHelper.bind(holder.swipeRevealLayout, article.id)

            holder.bind(article, position)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(inflater, parent)
        }

       inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.articles_list_item, parent, false)) {

            private var mTitle: TextView? = null
            private var mAuthor: TextView? = null
            private var mCreatedAt: TextView? = null
            var swipeRevealLayout: SwipeRevealLayout? = null
            var delete : ImageView? = null

            init{
                mTitle = itemView.findViewById(R.id.item_text_desc)
                mAuthor = itemView.findViewById(R.id.item_author)
                mCreatedAt = itemView.findViewById(R.id.item_text_createdAt)
                swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout)
                delete = itemView.findViewById(R.id.delete_article)

            }

            fun bind(item: Article, position: Int) {
                mTitle?.text = item.title
                if (item.title == null || item.title == "") mTitle?.text = item.storyTitle
                mAuthor?.text = item.author
                mCreatedAt?.text = item.created
                delete?.setOnClickListener { v -> presenter.deleteArticle(item, position)
                }

            }
        }

    }



}
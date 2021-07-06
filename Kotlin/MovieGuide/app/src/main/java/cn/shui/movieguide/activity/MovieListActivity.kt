package cn.shui.movieguide.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.shui.movieguide.R
import cn.shui.movieguide.data.MovieContent
import cn.shui.movieguide.fragment.MovieDetailFragment
import cn.shui.movieguide.utils.HttpUtil
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_list.*
import kotlinx.android.synthetic.main.movie_list_content.view.*
import kotlinx.android.synthetic.main.activity_movie_detail.view.*;

class MovieListActivity : AppCompatActivity() {

    private var mTowPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (movie_detail_container != null) {
            mTowPane = true
        }
        setupRecyclerView(movie_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, MovieContent.MOVIES, mTowPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val mParentActivity: MovieListActivity,
        private val mValues: List<MovieContent.Movie>,
        private val mTwoPane: Boolean
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as MovieContent.Movie
                if (mTwoPane) {
                    val fragment = MovieDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(MovieDetailFragment.ARG_MOVIE_ID, item.id)
                        }
                    }
                    mParentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.movie_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                        putExtra(MovieDetailFragment.ARG_MOVIE_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            val item = mValues[position]
            holder.mIdView.text = item.id
            holder.mTitle.text = item.title
            holder.mMoviePosterImageView.setImageBitmap(HttpUtil.getBitmapFromUrl(item.posterPath))
            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.id_text
            val mTitle: TextView = mView.title
            val mMoviePosterImageView: ImageView = mView.movie_poster_image
        }
    }
}
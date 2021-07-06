package cn.shui.movieguide.data

import android.os.StrictMode
import cn.shui.movieguide.Constant
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray

/**
 * Created by shui on 7/5/21
 */
object MovieContent {
    val MOVIES: MutableList<Movie> = ArrayList()
    val MOVIE_MAP: MutableMap<String, Movie> = HashMap()

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        initMovieListData()
    }

    private fun initMovieListData() {
        val jsonstr = Constant.jsonData
        try {
            val obj = JSON.parse(jsonstr) as Map<*, *>
            val dataArray = obj.get("results") as JSONArray
            dataArray.forEachIndexed { index, it ->
                val title = (it as Map<*, *>).get("title") as String
                val overview = it.get("overview") as String
                val poster_path = it.get("poster_path") as String
                val vote_count = it.get("vote_count").toString()
                val vote_average = it.get("vote_average").toString()
                val release_date = it.get("release_date").toString()
                addMovie(
                    Movie(
                        id = index.toString(),
                        title = title,
                        overview = overview,
                        vote_count = vote_count,
                        vote_average = vote_average,
                        release_date = release_date,
                        posterPath = getPosterUrl(poster_path)
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun addMovie(movie: Movie) {
        MOVIES.add(movie)
        MOVIE_MAP.put(movie.id, movie)
    }

    fun getPosterUrl(posterPath: String): String {
        return "http://$posterPath"
    }

    data class Movie(
        val id: String,
        val title: String,
        val overview: String,
        val vote_count: String,
        val vote_average: String,
        val release_date: String,
        val posterPath: String
    ) {
        override fun toString(): String {
            return "Movie(id='$id', " +
                    "title='$title', " +
                    "overview='$overview', " +
                    "vote_count='$vote_count', " +
                    "vote_average='$vote_average' " +
                    "release_date='$release_date', " +
                    "posterPath='$posterPath'"
        }
    }
}
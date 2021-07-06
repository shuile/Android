package cn.shui.movieguide

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by shui on 7/5/21
 */
object Constant {
    val jsonData = JSONObject(
        mutableMapOf(
            "page" to 1,
            "total_results" to 10350,
            "total_pages" to 518,
            "results" to JSONArray(
                listOf(
                    JSONObject(
                        mutableMapOf(
                            "vote_count" to 28,
                            "id" to 138878,
                            "video" to false,
                            "vote_average" to 10,
                            "title" to "Fatal Mission",
                            "popularity" to 3.721883,
                            "poster_path" to "/unsiudguyewvc.jpg",
                            "genre_ids" to listOf(10752, 28, 12)
                        ) as Map<*, *>
                    ),
                    JSONObject(
                        mutableMapOf(
                            "vote_count" to 29,
                            "id" to 138879,
                            "video" to true,
                            "vote_average" to 11,
                            "title" to "Fatal Mission2",
                            "popularity" to 3.721884,
                            "poster_path" to "/unsiasfsdfhsaufihdsiufguyewvc.jpg",
                            "genre_ids" to listOf(10753, 29, 13)
                        ) as Map<*, *>
                    )
                )
            )
        ) as Map<*, *>
    ).toString()
}
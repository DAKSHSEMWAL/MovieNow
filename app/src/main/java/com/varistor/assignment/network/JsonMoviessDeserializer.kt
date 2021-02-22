package com.varistor.assignment.network

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.varistor.assignment.model.Movie
import com.varistor.assignment.model.Movies
import java.lang.reflect.Type

class JsonMoviessDeserializer : JsonDeserializer<Movies> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Movies {
        val movies = Movies()
        if (json.isJsonObject) {
            for ((key, value) in json.asJsonObject.entrySet()) {
                if (key == "status") {
                    Log.d("Test", "Primitive: " + key + " = " + value.asString)
                    movies.status = value.asString
                } else if (key == "data") {
                    Log.d("Test", "Object: key: $key = $value")
                    val jsonObject = value.asJsonArray
                    for (jsonElement in jsonObject) {
                        val db = Movie()
                        db.name = jsonElement.asJsonObject["name"].asString
                        db.image = jsonElement.asJsonObject["image"].asString
                        db.genre = jsonElement.asJsonObject["genre"].asString
                        db.url = jsonElement.asJsonObject["url"].asString
                        db.movie_year = jsonElement.asJsonObject["movie_year"].asString
                        movies.addBreed(db)
                    }
                }
            }
        }
        return movies
    }
}
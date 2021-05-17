package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Movies : IntIdTable("movie") {
    val title = varchar("title", 256)
    val description = text("description")
    val posterUrl = text("poster_url")
    val durationMinutes = integer("duration_minutes")
    val director = reference("director_id", Directors)
    val genre = reference("genre_id", Genres)
}

object MovieHasActors : Table("movie_has_actor") {
    val movie = reference("movie_id", Movies)
    val actor = reference("actor_id", Actors)
    override val primaryKey = PrimaryKey(movie, actor)
}

class Movie(id: EntityID<Int>) : IntEntity(id)  {
    companion object : IntEntityClass<Movie>(Movies)

    var title by Movies.title
    var description by Movies.description
    var posterUrl by Movies.posterUrl
    var durationMinutes by Movies.durationMinutes

    var director by Director referencedOn Movies.director
    var genre by Genre referencedOn Movies.genre

    var actors by Actor via MovieHasActors


    fun toModel() : MovieModel {
        return MovieModel(
            id = id.value,
            title = this.title,
            description = this.description,
            durationMinutes = this.durationMinutes,
            director = this.director.fullName,
            genre = this.genre.name,
            posterUrl = this.posterUrl,
            actors = this.actors.map { it.fullName }
        )
    }
}

data class MovieModel(
    val id: Int,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val director: String,
    val genre: String,
    val posterUrl: String,
    val actors: List<String>,
)
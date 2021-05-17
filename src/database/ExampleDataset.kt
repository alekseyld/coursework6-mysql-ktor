package com.alekseyld.database

import com.alekseyld.database.dao.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object ExampleDataset {

    private  val genresStrings = listOf(
        "Comedy",
        "Fantasy",
        "Crime",
        "Drama",
        "Music",
        "Adventure",
        "History",
        "Thriller",
        "Animation",
        "Family",
        "Mystery",
        "Biography",
        "Action",
        "Film-Noir",
        "Romance",
        "Sci-Fi",
        "War",
        "Western",
        "Horror",
        "Musical",
        "Sport"
    )


    private  fun generateCinemaHall(name: String) {

        transaction {
            val hall1 = CinemaHall.new {
                this.name = name
            }

            (0..15).forEach { number ->

                Seat.new {
                    this.cinemaHall = hall1
                    this.number = number
                    this.row = (number / 5).toInt() + 1

                    this.seatType = when (number) {
                        in 0..5 -> SeatType.findById(2)!!
                        in 6..10 -> SeatType.findById(3)!!
                        else -> SeatType.findById(1)!!
                    }
                }

            }
        }
    }

    private  fun createIfNededActor(fullName: String) : Actor {
        val finded = Actor.find { Actors.fullName eq fullName }

        return if (finded.empty()) {
            Actor.new { this.fullName = fullName }
        } else {
            finded.elementAt(0)
        }
    }

    private  fun createMovie(
        title: String,
        description: String,
        duration: Int,
        genre: String,
        director: String,
        posterUrl: String,
        actor: List<String>
    ) {

        val listActorsEntity = mutableListOf<Actor>()

        transaction {
            actor.forEach {
                listActorsEntity.add(createIfNededActor(it))
            }
        }

        val moview = transaction {

            Movie.new {
                this.title = title
                this.description = description
                this.durationMinutes = duration
                this.posterUrl = posterUrl
                this.genre = Genre.find { Genres.name eq genre }.elementAt(0)

                val findedDirector = Director.find { Directors.fullName eq director }

                if (findedDirector.empty()) {
                    this.director = Director.new {
                        this.fullName = director
                    }
                } else {
                    this.director = findedDirector.elementAt(0);
                }



            }
        }


        transaction {
            moview.actors = SizedCollection(listActorsEntity)
        }



    }

    fun createCinemaHalls() {
        generateCinemaHall("Зал №1");
        generateCinemaHall("Зал №2");
    }

    fun createGenres() {
        transaction {
            genresStrings.forEach {
                Genre.new {
                    this.name = it
                }
            }
        }
    }

    fun createExampleMovieList() {

        val exampleMovies = Gson().fromJson<List<ExampleMovie>>(
            File("./example_movies.json").readText(),
            object : TypeToken<List<ExampleMovie>>() {}.type
        )

        exampleMovies.forEach { movie ->
            createMovie(
                title = movie.title,
                description = movie.plot,
                duration = movie.runtime,
                genre = movie.genres[0],
                director = movie.director,
                posterUrl = movie.posterUrl,
                actor = movie.actors.split(",").map { it.trim() },
            );
        }
    }

    fun createExampleDataSet() {
//        createCinemaHalls()
        createGenres()
        createExampleMovieList()
    }
}

private data class ExampleMovie(
    val title: String,
    val runtime: Int, //duration
    val genres: List<String>,
    val director: String,
    val actors: String,
    val plot: String,
    val posterUrl: String
)

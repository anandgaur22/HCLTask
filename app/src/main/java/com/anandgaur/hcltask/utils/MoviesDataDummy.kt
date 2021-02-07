package com.anandgaur.hcltask.utils

import com.anandgaur.hcltask.data.local.entity.*

object MoviesDataDummy {

    // movies generate
    fun generateDummyMoviesWithGenres(movies: MoviesEntity, isMovies: Boolean): MoviesWithGenres {
        movies.isMovies = isMovies
        return MoviesWithGenres(movies, generateDummyGenres(movies.moviesId))
    }

    fun generateDummyMoviesWithDirector(
        movies: MoviesEntity,
        isMovies: Boolean
    ): MoviesWithDirector {
        movies.isMovies = isMovies
        return MoviesWithDirector(movies, generateDummyDirector(movies.moviesId))
    }


    fun generateDummyMovies(): List<MoviesEntity> {
        val movies = ArrayList<MoviesEntity>()
        val base_url = "https://image.tmdb.org/t/p/w500"

        movies.add(
            MoviesEntity(
                464052,
                "Wonder Woman 1984",
                "$base_url/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                "en",
                "English",
                "2020-12-16",
                6017.605,
                7.3,
                2172,
                200000000,
                85400000,
                "A new era of wonder begins.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )
        movies.add(
            MoviesEntity(
                529203,
                "The Croods: A New Age",
                "$base_url/tK1zy5BsCt1J4OzoDicXmr0UTFH.jpg",
                "After leaving their cave, the Croods encounter their biggest threat since leaving: another family called the Bettermans, who claim and show to be better and evolved. Grug grows suspicious of the Betterman parents, Phil and Hope,  as they secretly plan to break up his daughter Eep with her loving boyfriend Guy to ensure that their daughter Dawn has a loving and smart partner to protect her.",
                "en",
                "English",
                "2020-11-25",
                1937.566,
                8.1,
                420,
                65000000,
                35930000,
                "The future ain't what it used to be.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )


        return movies
    }

    fun generateDummyGenres(moviesId: Int): List<GenresEntity> {

        val modules = ArrayList<GenresEntity>()

        modules.add(
            GenresEntity(1, moviesId, "Adventure")
        )
        modules.add(
            GenresEntity(2, moviesId, "Fantasy")
        )
        modules.add(
            GenresEntity(3, moviesId, "Family")
        )
        modules.add(
            GenresEntity(4, moviesId, "Animation")
        )
        modules.add(
            GenresEntity(5, moviesId, "Sci-Fi & Fantasy")
        )
        modules.add(
            GenresEntity(6, moviesId, "Action & Adventure")
        )

        return modules
    }

    fun generateDummyDirector(moviesId: Int): List<DirectorEntity> {

        val modules = ArrayList<DirectorEntity>()

        modules.add(
            DirectorEntity(
                1,
                moviesId,
                "Patty Jenkins",
                "Director"
            )
        )
        modules.add(
            DirectorEntity(
                2,
                moviesId,
                "Joel Crawford",
                "Director"
            )
        )


        return modules
    }


    fun generateDummyCreatedBy(moviesId: Int): List<CreatedByEntity> {

        val modules = ArrayList<CreatedByEntity>()

        modules.add(
            CreatedByEntity(1, moviesId, "Hayden Schlossberg")
        )
        modules.add(
            CreatedByEntity(2, moviesId, "Michael Hirst")
        )
        modules.add(
            CreatedByEntity(3, moviesId, "Jon Favreau")
        )
        modules.add(
            CreatedByEntity(4, moviesId, "Jorge Guerricaechevarría")
        )

        return modules
    }

    //remote dummy
    fun generateRemoteDummyMovies(): List<MoviesEntity> {
        val movies = ArrayList<MoviesEntity>()
        val base_url = "https://image.tmdb.org/t/p/w500"

        movies.add(
            MoviesEntity(
                464052,
                "Wonder Woman 1984",
                "$base_url/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                "en",
                "English",
                "2020-12-16",
                6017.605,
                7.3,
                2172,
                200000000,
                85400000,
                "A new era of wonder begins.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )
        movies.add(
            MoviesEntity(
                529203,
                "The Croods: A New Age",
                "$base_url/tK1zy5BsCt1J4OzoDicXmr0UTFH.jpg",
                "After leaving their cave, the Croods encounter their biggest threat since leaving: another family called the Bettermans, who claim and show to be better and evolved. Grug grows suspicious of the Betterman parents, Phil and Hope,  as they secretly plan to break up his daughter Eep with her loving boyfriend Guy to ensure that their daughter Dawn has a loving and smart partner to protect her.",
                "en",
                "English",
                "2020-11-25",
                1937.566,
                8.1,
                420,
                65000000,
                35930000,
                "The future ain't what it used to be.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )


        return movies
    }


    fun generateRemoteDummyGenres(moviesId: Int): List<GenresEntity> {

        val modules = ArrayList<GenresEntity>()

        modules.add(
            GenresEntity(1, moviesId, "Adventure")
        )
        modules.add(
            GenresEntity(2, moviesId, "Fantasy")
        )
        modules.add(
            GenresEntity(3, moviesId, "Family")
        )
        modules.add(
            GenresEntity(4, moviesId, "Animation")
        )
        modules.add(
            GenresEntity(5, moviesId, "Sci-Fi & Fantasy")
        )
        modules.add(
            GenresEntity(6, moviesId, "Action & Adventure")
        )

        return modules
    }

    fun generateRemoteDummyDirector(moviesId: Int): List<DirectorEntity> {

        val modules = ArrayList<DirectorEntity>()

        modules.add(
            DirectorEntity(
                1,
                moviesId,
                "Patty Jenkins",
                "Director"
            )
        )
        modules.add(
            DirectorEntity(
                2,
                moviesId,
                "Joel Crawford",
                "Director"
            )
        )

        return modules
    }

    fun generateRemoteDummyCreatedBy(moviesId: Int): List<CreatedByEntity> {

        val modules = ArrayList<CreatedByEntity>()

        modules.add(
            CreatedByEntity(1, moviesId, "Hayden Schlossberg")
        )
        modules.add(
            CreatedByEntity(2, moviesId, "Michael Hirst")
        )
        modules.add(
            CreatedByEntity(3, moviesId, "Jon Favreau")
        )
        modules.add(
            CreatedByEntity(4, moviesId, "Jorge Guerricaechevarría")
        )

        return modules
    }

}
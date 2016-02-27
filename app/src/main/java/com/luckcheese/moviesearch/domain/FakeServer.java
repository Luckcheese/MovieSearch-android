package com.luckcheese.moviesearch.domain;

import com.google.gson.Gson;
import com.luckcheese.moviesearch.models.Movie;
import com.luckcheese.moviesearch.models.MovieSearchResult;
import com.luckcheese.moviesearch.models.SearchResult;

import java.util.List;

public class FakeServer {

    private static String searchResultJson = "{\"Search\":[{\"Title\":\"Finding Nemo\",\"Year\":\"2003\",\"imdbID\":\"tt0266543\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTY1MTg1NDAxOV5BMl5BanBnXkFtZTcwMjg1MDI5Nw@@._V1_SX300.jpg\"},{\"Title\":\"Little Nemo: Adventures in Slumberland\",\"Year\":\"1989\",\"imdbID\":\"tt0104740\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTM0MDQ5MDExOV5BMl5BanBnXkFtZTcwOTE3ODc3Mg@@._V1_SX300.jpg\"},{\"Title\":\"Captain Nemo and the Underwater City\",\"Year\":\"1969\",\"imdbID\":\"tt0065522\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTIyNjI0MzI1MV5BMl5BanBnXkFtZTcwNTM0MTQyMQ@@._V1_SX300.jpg\"},{\"Title\":\"The Amazing Captain Nemo\",\"Year\":\"1978\",\"imdbID\":\"tt0077156\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTc4NzExNjcwN15BMl5BanBnXkFtZTYwMTM1Mjg5._V1_SX300.jpg\"},{\"Title\":\"Nemo\",\"Year\":\"1984\",\"imdbID\":\"tt0087784\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTY2NzIwMTgwN15BMl5BanBnXkFtZTcwMjIyMzMzMQ@@._V1_SX300.jpg\"},{\"Title\":\"Captain Nemo\",\"Year\":\"1975\",\"imdbID\":\"tt0453375\",\"Type\":\"movie\",\"Poster\":\"N/A\"},{\"Title\":\"Finding Nemo\",\"Year\":\"2003\",\"imdbID\":\"tt0401422\",\"Type\":\"game\",\"Poster\":\"N/A\"},{\"Title\":\"Making 'Nemo'\",\"Year\":\"2003\",\"imdbID\":\"tt0387373\",\"Type\":\"movie\",\"Poster\":\"N/A\"},{\"Title\":\"Finding Nemo Submarine Voyage\",\"Year\":\"2007\",\"imdbID\":\"tt1319713\",\"Type\":\"movie\",\"Poster\":\"N/A\"},{\"Title\":\"Little Nemo: The Dream Master\",\"Year\":\"1990\",\"imdbID\":\"tt0206895\",\"Type\":\"game\",\"Poster\":\"N/A\"}],\"totalResults\":\"31\",\"Response\":\"True\"}\n";
    private static String detailedResultJson = "{\"Title\":\"Finding Nemo\",\"Year\":\"2003\",\"Rated\":\"G\",\"Released\":\"30 May 2003\",\"Runtime\":\"100 min\",\"Genre\":\"Animation, Adventure, Comedy\",\"Director\":\"Andrew Stanton, Lee Unkrich\",\"Writer\":\"Andrew Stanton (original story by), Andrew Stanton (screenplay), Bob Peterson (screenplay), David Reynolds (screenplay)\",\"Actors\":\"Albert Brooks, Ellen DeGeneres, Alexander Gould, Willem Dafoe\",\"Plot\":\"After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Won 1 Oscar. Another 44 wins & 56 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTY1MTg1NDAxOV5BMl5BanBnXkFtZTcwMjg1MDI5Nw@@._V1_SX300.jpg\",\"Metascore\":\"90\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"644,319\",\"imdbID\":\"tt0266543\",\"Type\":\"movie\",\"Response\":\"True\"}\n";

    public static List<MovieSearchResult> search() {
        return gson().fromJson(searchResultJson, SearchResult.class).getSearch();
    }

    public static Movie details(String itemId) {
        return gson().fromJson(detailedResultJson, Movie.class);
    }

    private static Gson gson() {
        return new Gson();
    }

}

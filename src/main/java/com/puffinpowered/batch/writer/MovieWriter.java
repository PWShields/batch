package com.puffinpowered.batch.writer;


import com.puffinpowered.batch.entity.Movie;
import com.puffinpowered.batch.repository.MovieRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieWriter implements ItemWriter<Movie> {

    MovieRepository movieRepository;

    public MovieWriter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void write(List<? extends Movie> movies) throws Exception {
        movieRepository.saveAll(movies);
    }
}

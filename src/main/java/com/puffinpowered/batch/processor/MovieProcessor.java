package com.puffinpowered.batch.processor;


import com.puffinpowered.batch.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MovieProcessor implements ItemProcessor<Movie, Movie> {

    @Override
    public Movie process(final Movie movie) throws Exception {
        log.info("{},{}", "Processed movie ",movie.getMovie());
        return movie;
    }
}

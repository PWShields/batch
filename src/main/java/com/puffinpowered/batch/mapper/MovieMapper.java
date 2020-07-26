package com.puffinpowered.batch.mapper;

import com.puffinpowered.batch.entity.Movie;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper  implements FieldSetMapper {

    @Override
    public Movie mapFieldSet(FieldSet fieldSet){
        final Movie movie = new Movie();
        movie.setId(fieldSet.readLong("id"));
        movie.setMovie(fieldSet.readString("movie"));
        movie.setStar(fieldSet.readString("star"));
        return movie;
    }
}

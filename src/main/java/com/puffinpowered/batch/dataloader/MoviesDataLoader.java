package com.puffinpowered.batch.dataloader;

import com.puffinpowered.batch.entity.Movie;
import com.puffinpowered.batch.listener.BatchCompletionListener;
import com.puffinpowered.batch.mapper.MovieMapper;
import com.puffinpowered.batch.processor.MovieProcessor;
import com.puffinpowered.batch.writer.MovieWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
public class MoviesDataLoader {

    JobBuilderFactory jobBuilderFactory;

    StepBuilderFactory stepBuilderFactory;

    MovieWriter movieWriter;

    MovieProcessor movieProcessor;

    @Value("${file.location.movies}")
    String fileLocation;

    public MoviesDataLoader(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MovieWriter movieWriter, MovieProcessor movieProcessor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.movieWriter = movieWriter;
        this.movieProcessor = movieProcessor;
    }

    @Bean
    public Job importMovie(BatchCompletionListener listener, Step importMovieStep){
        return jobBuilderFactory.get("importMovieJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importMovieStep)
                .end()
                .build();
    }

    @Bean
    public Step importMovieStep(MovieWriter writer) {
        return stepBuilderFactory.get("movie")
                .<Movie, Movie> chunk(4)
                .reader(movieReader())
                .processor(movieProcessor)
                .writer(movieWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<Movie> movieReader() {
        FlatFileItemReader<Movie> reader = new FlatFileItemReaderBuilder<Movie>()
                .name("movieReader")
                .resource(new ClassPathResource(fileLocation))
                .lineMapper(movieLineMapper())
                .build();
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public LineMapper<Movie> movieLineMapper() {
        final DefaultLineMapper<Movie> lineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames(new String[] {"id","star", "movie"});
        final MovieMapper fieldSetMapper = new MovieMapper();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }




}

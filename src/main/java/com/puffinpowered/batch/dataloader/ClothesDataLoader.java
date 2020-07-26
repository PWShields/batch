package com.puffinpowered.batch.dataloader;

import com.puffinpowered.batch.entity.ClothingItem;
import com.puffinpowered.batch.listener.BatchCompletionListener;
import com.puffinpowered.batch.mapper.ClothingItemMapper;
import com.puffinpowered.batch.writer.ClothesWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
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
public class ClothesDataLoader {

    JobBuilderFactory jobBuilderFactory;

    StepBuilderFactory stepBuilderFactory;

    ClothesWriter clothesWriter;



    @Value("${file.location.clothes}")
    String fileLocation;

    public ClothesDataLoader(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ClothesWriter clothesWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.clothesWriter = clothesWriter;
    }

    @Bean
    public Job importClothes(BatchCompletionListener listener, Step importClothesStep){
        return jobBuilderFactory.get("importClothesJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importClothesStep)
                .end()
                .build();
    }

    @Bean
    public Step importClothesStep() {
        return stepBuilderFactory.get("clothes")
                .<ClothingItem, ClothingItem> chunk(4)
                .reader(clothingReader())
                .writer(clothesWriter)
                .build();
    }

    @Bean
    public ItemReader<? extends ClothingItem> clothingReader() {
        FlatFileItemReader<ClothingItem> reader = new FlatFileItemReaderBuilder<ClothingItem>()
                .name("clothingReader")
                .resource(new ClassPathResource(fileLocation))
                .lineMapper(clothingLineMapper())
                .build();
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public LineMapper<ClothingItem> clothingLineMapper() {
        final DefaultLineMapper<ClothingItem> lineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames(new String[] {"id","item", "colour"});
        final ClothingItemMapper fieldSetMapper = new ClothingItemMapper();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

}

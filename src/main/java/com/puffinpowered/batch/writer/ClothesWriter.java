package com.puffinpowered.batch.writer;


import com.puffinpowered.batch.entity.ClothingItem;
import com.puffinpowered.batch.repository.ClothesRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClothesWriter implements ItemWriter<ClothingItem> {

    ClothesRepository clothesRepository;

    public ClothesWriter(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @Override
    public void write(List<? extends ClothingItem> clothes) throws Exception {
        clothesRepository.saveAll(clothes);
    }
}

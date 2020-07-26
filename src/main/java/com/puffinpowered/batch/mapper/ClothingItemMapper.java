package com.puffinpowered.batch.mapper;

import com.puffinpowered.batch.entity.ClothingItem;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class ClothingItemMapper implements FieldSetMapper {

    @Override
    public ClothingItem mapFieldSet(FieldSet fieldSet){
        final ClothingItem clothingItem = new ClothingItem();
        clothingItem.setId(fieldSet.readLong("id"));
        clothingItem.setItem(fieldSet.readString("item"));
        clothingItem.setColour(fieldSet.readString("colour"));
        return clothingItem;
    }
}

package com.puffinpowered.batch.repository;

import com.puffinpowered.batch.entity.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<ClothingItem, Long> {
}

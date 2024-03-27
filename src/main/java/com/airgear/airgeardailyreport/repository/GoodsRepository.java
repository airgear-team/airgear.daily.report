package com.airgear.airgeardailyreport.repository;


import com.airgear.airgeardailyreport.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Long countByCreatedAtBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    @Query("SELECT COUNT(goods) FROM Goods goods WHERE goods.category.id = :categoryId AND goods.createdAt >= :startDate AND goods.createdAt <= :endDate")
    Long countNewGoodsByCategoryInPeriod(@Param("categoryId") Integer categoryId, @Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);

    List<Goods> findAll();

}
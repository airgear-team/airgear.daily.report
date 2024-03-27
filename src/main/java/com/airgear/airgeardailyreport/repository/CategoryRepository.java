package com.airgear.airgeardailyreport.repository;

import com.airgear.airgeardailyreport.model.goods.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}

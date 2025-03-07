package com.mts.AviaParser.repository;

import com.mts.AviaParser.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:startPrice IS NULL OR p.price >= :startPrice) AND " +
            "(:endPrice IS NULL OR p.price <= :endPrice) AND " +
            "(:minimalRating IS NULL OR p.ratingValue >= :minimalRating) AND" +
            "(:seller IS NULL OR p.seller = :seller)")
    List<Product> findByFilters(String category, Integer startPrice, Integer endPrice, Double minimalRating, String seller);
}

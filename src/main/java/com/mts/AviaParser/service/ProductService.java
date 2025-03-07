package com.mts.AviaParser.service;

import com.mts.AviaParser.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findByFilters(String category, Integer startPrice, Integer endPrice, Double minimalRating, String seller);
}

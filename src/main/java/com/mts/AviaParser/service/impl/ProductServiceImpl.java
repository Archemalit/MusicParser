package com.mts.AviaParser.service.impl;

import com.mts.AviaParser.model.Product;
import com.mts.AviaParser.repository.ProductRepository;
import com.mts.AviaParser.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public List<Product> findByFilters(String category, Integer startPrice, Integer endPrice, Double minimalRating, String seller) {
        return productRepository.findByFilters(category, startPrice, endPrice, minimalRating, seller);
    }
}

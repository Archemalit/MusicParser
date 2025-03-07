package com.mts.AviaParser.service;

import com.mts.AviaParser.model.Category;
import com.mts.AviaParser.model.SubCategory;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    List<SubCategory> getSubCategories(String name);
}

package com.mts.AviaParser.service.impl;

import com.mts.AviaParser.exception.InvalidHttpStatusException;
import com.mts.AviaParser.exception.ParsingException;
import com.mts.AviaParser.model.Category;
import com.mts.AviaParser.model.SubCategory;
import com.mts.AviaParser.service.CategoryService;
import com.mts.AviaParser.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final RedisCacheService redisCacheService;

    @Value("${base.url.site}")
    private String urlCategories;

    @Override
    public List<Category> getCategories() {
        try {
            List<Category> categories = redisCacheService.get("catalog", List.class);
            if (categories == null) {
                categories = new ArrayList<>();
            } else {
                return categories;
            }

            Document document = Jsoup.connect(urlCategories + "/catalog").get();
            Elements elements = document.select("a.category");
            for (Element element : elements) {
                categories.add(new Category(
                        element.select("div.category__name").text(),
                        element.attr("href").substring(10)
                ));
            }

            redisCacheService.save("catalog", categories);
            return categories;
        } catch (IOException e) {
            throw new ParsingException(500, "Error while parsing categories: " + e.getMessage());
        }
    }

    @Override
    public List<SubCategory> getSubCategories(String name) {
        try {
            List<SubCategory> subCategories = redisCacheService.get(name, List.class);
            if (subCategories == null) {
                subCategories = new ArrayList<>();
            } else {
                return subCategories;
            }

            Connection connection = Jsoup.connect(urlCategories + "/category/" + name);
            int statusCode = connection.execute().statusCode();

            if (statusCode == 200) {
                Document document = connection.get();
                Elements elements = document.select("a.category");

                for (Element element : elements) {
                    SubCategory subCategory = new SubCategory()
                            .setName(element.select("div.category__name").text())
                            .setUrl(element.attr("href").substring(10));
                    String count = element.select("div.category__amount").text().split(" ")[0];
                    if (!count.isEmpty()) {
                        subCategory.setCount(Integer.parseInt(count));
                    }
                    subCategories.add(subCategory);
                }

                redisCacheService.save(name, subCategories);
                return subCategories;
            } else {
                throw new InvalidHttpStatusException(statusCode, "Failed to fetch subcategories");
            }
        } catch (IOException e) {
            throw new ParsingException(500, "Error while parsing categories: " + e.getMessage());
        }
    }
}

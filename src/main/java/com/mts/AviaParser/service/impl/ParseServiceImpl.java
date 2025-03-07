package com.mts.AviaParser.service.impl;

import com.mts.AviaParser.dto.ParseRequest;
import com.mts.AviaParser.exception.InvalidHttpStatusException;
import com.mts.AviaParser.exception.ParsingException;
import com.mts.AviaParser.model.Product;
import com.mts.AviaParser.repository.ProductRepository;
import com.mts.AviaParser.service.ParseService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ParseServiceImpl implements ParseService {
    private final ProductRepository productRepository;

    @Value("${base.url.site}")
    private String url;

    @Override
    public void parseProducts(String category, ParseRequest parseRequest) {
        try {
            Connection connection = Jsoup.connect(url + "/category/" + category)
                    .data("start_price", String.valueOf(parseRequest.getStartPrice()))
                    .data("end_price", String.valueOf(parseRequest.getEndPrice()));

            if (parseRequest.isOnlyFresh()) {
                connection = connection.data("sort", "stocks");
            }

            int statusCode = connection.execute().statusCode();

            if (statusCode == 200) {
                Document document = connection.get();
                Elements paginationItems = document.select(".pagination-container__item");
                if (!paginationItems.isEmpty()) {
                    int pages = Integer.parseInt(paginationItems.get(paginationItems.size() - 2).text());
                    for (int i = 1; i <= pages; i++) {
                        connection = connection.data("page", String.valueOf(i));
                        findProductsInArticle(connection);
                    }
                } else {
                    findProductsInArticle(connection);
                }
            } else {
                throw new InvalidHttpStatusException(statusCode, "Failed to fetch products");
            }
        } catch (IOException e) {
            throw new ParsingException(500, "Error while parsing products: " + e.getMessage());
        }
    }

    private void findProductsInArticle(Connection connection) throws IOException {
        Document document = connection.get();
        Elements elements = document.select("article.catalog-card");

        for (Element element : elements) {
            String shopId = element.select("a.catalog-card__link").attr("href").split("/")[2];
            int price = Integer.parseInt(element.select("div.catalog-card__price-block").select("meta[itemprop=price]").attr("content"));
            String seller = element.select("div.catalog-card__price-block").select("meta[itemprop=seller]").attr("content");
            String name = element.select("div.catalog-card__info").select("meta[itemprop=name]").attr("content");
            String type = element.select("div.catalog-card__info").select("meta[itemprop=category]").attr("content");
            Elements rate = element.select("div.catalog-card__rating").select("span");
            Double ratingValue = null;
            Integer ratingCount = null;
            if (!rate.text().isEmpty()) {
                String[] rateInfo = rate.text().split(" ");
                ratingValue = Double.parseDouble(rateInfo[0]);
                ratingCount = Integer.parseInt(rateInfo[1].substring(1, rateInfo[1].length() - 1));
            }

            Product product = new Product(shopId, name, seller, type, price, ratingValue, ratingCount);
            productRepository.save(product);
        }
    }
}

package com.mts.AviaParser.service;

import com.mts.AviaParser.dto.ParseRequest;

public interface ParseService {
    void parseProducts(String category, ParseRequest productParse);
}

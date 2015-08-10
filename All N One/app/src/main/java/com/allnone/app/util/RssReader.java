package com.allnone.app.util;

import com.allnone.app.data.RssItem;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssReader {

    private String rssUrl;

    public RssReader(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public List<RssItem> getItems() {
        RssParseHandler handler = new RssParseHandler();
        try {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = factory.newSAXParser();

        saxParser.parse(rssUrl, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handler.getItems();

    }

}
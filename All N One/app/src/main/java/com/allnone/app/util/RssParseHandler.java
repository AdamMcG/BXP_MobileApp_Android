package com.allnone.app.util;

import com.allnone.app.data.RssItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


public class RssParseHandler extends DefaultHandler {
    private List<RssItem> rssItems;


    private RssItem currentItem;

    private boolean parsingTitle;

    private boolean parsingContents;

    private StringBuffer currentTitleSb;

    private StringBuffer currentContentSb;

    public RssParseHandler() {
        rssItems = new ArrayList<RssItem>();
    }

    public List<RssItem> getItems() {
        return rssItems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("entry".equals(qName)) {
            currentItem = new RssItem();
        } else if ("title".equals(qName)) {
            parsingTitle = true;
            currentTitleSb = new StringBuffer();
        } else if ("summary".equals(qName)) {
            parsingContents = true;
            currentContentSb = new StringBuffer();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("entry".equals(qName)) {
            rssItems.add(currentItem);
            currentItem = null;
        } else if ("title".equals(qName)) {
            parsingTitle = false;

            if (currentItem != null)
                currentItem.setTitle(currentTitleSb.toString());
        } else if ("summary".equals(qName)) {
            parsingContents = false;
            if (currentItem != null)
                currentItem.setContent(currentContentSb.toString());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingTitle) {
            if (currentItem != null)
                currentTitleSb.append(new String(ch, start, length));
        } else if (parsingContents) {
            if (currentItem != null)
                currentContentSb.append(new String(ch, start, length));
        }
    }

}
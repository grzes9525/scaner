package com.scaner.service;

import com.scaner.model.Page;
import com.scaner.repository.PageRepository;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class PageServices {

    @Autowired
    private PageRepository pageRepository;

    @Transactional
    public void saveNextPageNumber(Document source) {

        Elements elementsPageNumber = source.getElementsByAttributeValueContaining("class", "pager-next");

        //System.out.println(elementsPageNumber.toString());
        Page page = new Page();
        String linkToNextPage = elementsPageNumber.first().getElementsByTag("a").attr("href");
        //System.out.println("sss: " + linkToNextPage);
        page.setPageNumber(Integer.valueOf(linkToNextPage.substring(linkToNextPage.indexOf("page=") + 5, linkToNextPage.length())));
        page.setId(1l);
        //System.out.println(page.getId() + " " + page.getPageNumber());
        System.out.println("Zapis strony "+page.getPageNumber()+" o godzinie "+new Date());

        pageRepository.save(page);
        //System.out.println("Numer strony: " + page.getPageNumber());
    }
}

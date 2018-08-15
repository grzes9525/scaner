package com.scaner.generation;

import com.scaner.model.Advert;
import com.scaner.model.Page;
import com.scaner.repository.AdvertRepository;
import com.scaner.repository.PageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GenrateAdvert {

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private PageRepository pageRepository;

    public List<Advert> generateAdverts(){
        List<Advert> adverts = new ArrayList<>();
        fillAdvertsList(adverts);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i=0;
        for(Advert advert:adverts){
            if(!advertRepository.existsById(advert.getDataItemId()) && advert.getLinkToAd()!=null && !advert.getLinkToAd().isEmpty()){
                getDetailAdvert(advert);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(i==0){
                break;
            }
            i++;
        }

        return adverts;
    }

    /**
     *
     * @param link do strony
     * @return zawartość żródłową strony
     * @throws IOException jeśli nie znajdzie strony z linka
     */
    public static String getWebSiteSourceContent(String link) throws IOException {
        URL example = new URL(link);
        BufferedReader in = new BufferedReader(new InputStreamReader(example.openStream()));

        StringBuilder websiteSource = new StringBuilder();
        String tmp = null;
        while ((tmp = in.readLine()) != null) {
            websiteSource.append(tmp);
        }
        in.close();

        return new String(websiteSource.toString().getBytes(),"UTF-8");
    }
    /**
     * numer strony
     */
    @Transactional
    void saveNextPageNumber(Document source){

        Elements elementsPageNumber = source.getElementsByAttributeValueContaining("class","pager-next");

        System.out.println(elementsPageNumber.toString());
        Page page = new Page();
        String linkToNextPage = elementsPageNumber.first().getElementsByTag("a").attr("href");
        System.out.println("sss: "+ linkToNextPage);
        page.setPageNumber(Integer.valueOf(linkToNextPage.substring(linkToNextPage.indexOf("page=")+5,linkToNextPage.length())));
        page.setId(1l);
        System.out.println(page.getId()+" "+page.getPageNumber());
        pageRepository.save(page);
        System.out.println("Numer strony: "+page.getPageNumber());
    }

    /**
     *
     * @return link do strony
     */
    public String generateLink(){
        Page page = new Page();
        page.setPageNumber(1);
        Integer pageNumber = pageRepository.findById(1l).orElse(page).getPageNumber();
        if(pageNumber==null){
            pageNumber=1;
        }
        return "https://www.otodom.pl/wynajem/mieszkanie/?search%5BCSRF" +
                "Token%5D=92c1086db99b36a692cedb34d46aaa22688d2c0e16058a0fd319ace3a96c6a37&CSRF" +
                "Token=92c1086db99b36a692cedb34d46aaa22688d2c0e16058a0fd319ace3a96c6a37&page=" +
                +pageNumber+
                "&search%5BCSRFToken%5D=92c1086db99b36a692cedb34d46aaa22688d2c0e16058a0fd319ace3a96c6a37&search%5B" +
                "description%5D=1&CSRFToken=92c1086db99b36a692cedb34d46aaa22688d2c0e16058a0fd319ace3a96c6a37#form";
    }

    /**
     *
     * @return zawartość źródłową strony
     */
    public List<Advert> fillAdvertsList(List<Advert> adverts){

        Document source = null;
        try {
            source = Jsoup.parse(GenrateAdvert.getWebSiteSourceContent(generateLink()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements contents = source.getElementsByTag("article");

        for (Element content : contents) {
            Advert advert = new Advert();


            advert.setDataItemId(content.attr("data-item-id"));

            String contentText = content.text();
            advert.setDescAdvert(contentText);

            Elements elementLinks = content.getElementsByTag("a");
            String linkToAdvert = elementLinks.attr("href");
            advert.setLinkToAd(linkToAdvert);
            adverts.add(advert);
        }

        saveNextPageNumber(source);

        return adverts;
    }

    /**
     * metoda tworzy ogłoszenia na podstawie szczegłowego ogłoszenia
     * @param advert obiekt do zwrócenia
     */
    public void getDetailAdvert(Advert advert){
        Document source = null;
        try {
            source = Jsoup.parse(GenrateAdvert.getWebSiteSourceContent(advert.getLinkToAd()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * typ ogłoszenia
         */
        Elements elementsAddress = source.getElementsByAttributeValueContaining("class","address-links");
        Elements elementsAddress1 = elementsAddress.first().getAllElements();
        advert.setKindOfTransaction(elementsAddress1.get(1).text());
        //System.out.println(elementsAddress1.get(1).text());

        /**
         * adres
         */
        Elements elementsAddress2 = source.getElementsByAttributeValueContaining("itemprop","address");
        Element elementAddress2 = elementsAddress2.first();
        String address = elementAddress2.text().replaceAll(", ",",");
        String[] addresses = address.split(",");
        System.out.println(advert.getLinkToAd());
        advert.setCity(addresses[0]);
        if(StringUtils.countOccurrencesOf(address,",")!=0){
            advert.setDistrict(addresses[1]);
            if(StringUtils.countOccurrencesOf(address,",")==1){
                advert.setStreet(addresses[1]);
            }else{
                advert.setStreet(addresses[2]);
            }
        }else{
            advert.setDistrict("Inaccurate_data");
            advert.setStreet("Inaccurate_data");
        }


        /**
         * tworznie pełnego adresu
         */
        if(advert.getStreet()=="Inaccurate_data" && advert.getDistrict()=="Inaccurate_data" ){
            advert.setAddress(advert.getCity());
        }else{
            advert.setAddress(advert.getCity()+", "+advert.getDistrict()+", "+advert.getStreet());
        }
        /*
         advert.setCity(elementsAddress1.get(2).text());
        //System.out.println(elementsAddress1.get(2).text());

        /**
         * Warunek:
         * 1 - jesli element 3 ma w nazwie ulice to znaczy ze nie podano dzielnicy tylko odrazu ulice
         * 2 - jesli element 4 ma zobacz na mapie to wstawiamy tak samo jak wyżej
         * 4 - jeżeli element 5 ma zobacz na mapie to wstawiamy oddzielnie ulice oraz dzielnice

        if(elementsAddress1.get(5).text().contains("Zobacz na mapie") && !elementsAddress1.get(4).text().contains("Zobacz na mapie")){
            advert.setDistrict(elementsAddress1.get(3).text());
            advert.setStreet(elementsAddress1.get(4).text());
        }else if(elementsAddress1.get(4).text().contains("Zobacz na mapie")){
            advert.setStreet(elementsAddress1.get(3).text());
            advert.setDistrict(elementsAddress1.get(3).text());
        }else if(!elementsAddress1.get(3).text().contains("street")){
            advert.setDistrict(elementsAddress1.get(3).text());
            advert.setStreet(elementsAddress1.get(3).text());
            //System.out.println(elementsAddress1.get(3).text());
        }
        if(advert.getDistrict()==advert.getStreet()){
            advert.setAddress(advert.getCity()+", "+advert.getDistrict());
        }else{
            advert.setAddress(advert.getCity()+", "+advert.getDistrict()+", "+advert.getStreet());
        }
   */
        /**
         * cena
         */
        Elements elementsPirce = source.getElementsByAttributeValueContaining("class","box-price-value no-estimates");
        Element elementPrice1 = elementsPirce.first();
        Pattern patternPrice = Pattern.compile("(.*[0-9] *[0-9])");
        Matcher matcher = patternPrice.matcher(elementPrice1.text());
        if (matcher.find())
        {
            advert.setPrice(Double.parseDouble(matcher.group(0).replaceAll("\\s+","").replaceAll(",",".")));
        }
        Pattern patternCurrency = Pattern.compile("((?:[a-z]ł[a-z0-9_]*))");
        Matcher matcher1= patternCurrency.matcher(elementPrice1.text());
        if(matcher1.find()){
            advert.setCurrency(matcher1.group(1).replaceAll("\\s+",""));
        }

        /**
         * okres płatności np. miesiecznie
         */
        Elements elementsPeriodOfPayment = source.getElementsByAttributeValueContaining("class","box-price-text");
        Element elementPeriodOfPayment = elementsPeriodOfPayment.first();
        //System.out.println(elementPeriodOfPayment.text());
        if(elementPeriodOfPayment.text().equals("/miesiąc")){
            advert.setPeriodOfPeyment("monthly");
        }

        /**
         * Data wygenerowania
         */

        advert.setGenerateAdvertDt(new Date());


    }






}

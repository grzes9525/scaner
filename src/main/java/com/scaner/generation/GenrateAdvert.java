package com.scaner.generation;

import com.scaner.model.Advert;
import com.scaner.model.Audit;
import com.scaner.model.Page;
import com.scaner.repository.AdvertRepository;
import com.scaner.repository.AuditRepository;
import com.scaner.repository.PageRepository;
import com.scaner.service.PageServices;
import com.scaner.statistics.StatisticGenerator;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GenrateAdvert {

    final static Logger logger = Logger.getLogger(GenrateAdvert.class);

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private StatisticGenerator statisticGenerator;

    @Autowired
    private PageServices pageServices;

    public void generateAdverts() {
        List<Advert> adverts = new ArrayList<>();
        fillAdvertsList(adverts);
        try {
            Thread.sleep(6*10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int i = 0;
        for (Advert advert : adverts) {
            System.out.println("Exist: " + advertRepository.existsById(advert.getDataItemId()));
            //System.out.println(advert.getLinkToAd());
            Audit audit = new Audit();
            audit.setDatItemId(advert.getDataItemId());
            audit.setLinkToAd(advert.getLinkToAd());

            //Y to obkiet  istaniał
            audit.setStatusExistingInd("Y");
            auditRepository.save(audit);
            if (!advertRepository.existsById(advert.getDataItemId()) && advert.getLinkToAd() != null && !advert.getLinkToAd().isEmpty()) {
                 //N to obkiet nie istaniał
                audit.setStatusExistingInd("N");
                auditRepository.save(audit);
                getDetailAdvert(advert);
                logger.info("Wysłane ogłoszenie: "+advert.toString());
                advertRepository.save(advert);
                System.out.println("-------------SAVED----------------");
                long start = new Date().getTime();
                int i =0;
                for(Advert advert1:advertRepository.findByCityAndLatitudeIsNotNull("Warszawa")){
                    System.out.println("advertRepository.findByCityAndLatitudeIsNotNull(\"Warszawa\"): "+advertRepository.findByCityAndLatitudeIsNotNull("Warszawa").size());
                    statisticGenerator.affordableAdvertInWarsaw(advert1);
                    i++;
                    //logger.info("Spraw ogłoszenie: "+advert1.toString());
                }
                System.out.println("Test pobierania findByCityAndLatitudeIsNotNull(\"Warszawa\"):"+ i);
                long end = new Date().getTime();
                System.out.println("Czas genrowania statystyki po dodaniu jednego ogłoszenia do bazy: "+(end-start)/(1000)+" s");
                try {
                    Thread.sleep(5*60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*
            if (i == 3) {
                break;
            }
            i++;
             */
        }
    }

    /**
     * @param link do strony
     * @return zawartość żródłową strony
     * @throws IOException jeśli nie znajdzie strony z linka
     */
    public static String getWebSiteSourceContent(String link) throws IOException {
        URL example = new URL(link);

        StringBuilder websiteSource = new StringBuilder();
        String tmp = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(example.openStream(), "UTF8"));
        while ((tmp = reader.readLine()) != null) {

            websiteSource.append(tmp);
        }
        reader.close();
        //System.out.println(DecodePolishChar.decodeChar(websiteSource.toString()));
        return websiteSource.toString();
    }



    /**
     * @return link do strony
     */
    public String generateLink() {
        Page page = new Page();
        page.setPageNumber(1);
        Integer pageNumber = pageRepository.findById(1l).orElse(page).getPageNumber();
        if (pageNumber == null) {
            pageNumber = 1;
        }
        return "https://www.otodom.pl/wynajem/mieszkanie/warszawa/?search%5BCSRFToken%5D=964e7396d87518900eb9b492e953e7aad884a7a7cba924d819c84379b1f38848&CSRFToken=964e7396d87518900eb9b492e953e7aad884a7a7cba924d819c84379b1f38848&page="+pageNumber+"&search%5Bdescription%5D=1&search%5Bdist%5D=0&search%5Bsubregion_id%5D=197&search%5Bcity_id%5D=26";
    }


    public List<Advert> fillAdvertsList(List<Advert> adverts) {

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


        pageServices.saveNextPageNumber(source);

        return adverts;
    }

    private Double getPrice(String content) {
        Pattern patternPrice = Pattern.compile("(.*[0-9] *[0-9])");
        Matcher matcher = patternPrice.matcher(content);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group(0).replaceAll("\\s+", "").replaceAll(",", "."));
        } else {
            return null;
        }


    }

    private String getCurrency(String content) {
        if (content.contains("zł")) {
            return "zł";
        } else if (content.contains("EUR")) {
            return "EUR";
        } else {
            return null;
        }
    }

    private Date getDate(String content) {
        String sDate = null;
        Pattern pattern = Pattern.compile("([0-9]*)");
        Matcher matcher = pattern.matcher(content);
        if (content.contains("Stycznia") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "01" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Lutego") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "02" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Marca") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "03" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Kwietnia") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "04" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Maja") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "05" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Czerwca") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "06" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Lipca") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "07" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Sierpnia") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "08" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Września") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "09" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Października") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "10" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Listopada") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "11" + "/" + content.substring(content.length() - 4, content.length());
        } else if (content.contains("Grudnia") && matcher.find()) {
            sDate = matcher.group(0) + "/" + "12" + "/" + content.substring(content.length() - 4, content.length());
        }

        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * metoda tworzy ogłoszenia na podstawie szczegłowego ogłoszenia
     *
     * @param advert obiekt do zwrócenia
     */
    public void getDetailAdvert(Advert advert) {
        Document source = null;
        try {
            source = Jsoup.parse(GenrateAdvert.getWebSiteSourceContent(advert.getLinkToAd()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * typ ogłoszenia
         */
        Elements elementsAddress = source.getElementsByAttributeValueContaining("class", "address-links");
        Elements elementsAddress1 = elementsAddress.first().getAllElements();
        advert.setKindOfTransaction(elementsAddress1.get(1).text());
        //System.out.println(elementsAddress1.get(1).text());

        /**
         * adres
         */
        Elements elementsAddress2 = source.getElementsByAttributeValueContaining("itemprop", "address");
        Element elementAddress2 = elementsAddress2.first();
        String address = elementAddress2.text().replaceAll(", ", ",");
        String[] addresses = address.split(",");
        //System.out.println("link: " + advert.getLinkToAd());
        advert.setCity(addresses[0]);
        if (StringUtils.countOccurrencesOf(address, ",") != 0) {
            advert.setDistrict(addresses[1]);
            if (StringUtils.countOccurrencesOf(address, ",") == 1) {
                advert.setStreet(addresses[1]);
            } else {
                advert.setStreet(addresses[2]);
            }
        } else {
            advert.setDistrict("Inaccurate_data");
            advert.setStreet("Inaccurate_data");
        }


        /**
         * tworznie pełnego adresu
         */
        if (advert.getStreet() == "Inaccurate_data" && advert.getDistrict() == "Inaccurate_data") {
            advert.setAddress(advert.getCity());
        } else {
            advert.setAddress(advert.getCity() + ", " + advert.getDistrict() + ", " + advert.getStreet());
        }


        /**
         * cena
         */
        Elements elementsPirce = source.getElementsByAttributeValueContaining("class", "box-price-value");
        Element elementPrice1 = elementsPirce.first();
        advert.setPrice(getPrice(elementPrice1.text()));
        advert.setCurrency(getCurrency(elementPrice1.text()));

        /**
         * okres płatności np. miesiecznie
         */
        Elements elementsPeriodOfPayment = source.getElementsByAttributeValueContaining("class", "box-price-text");
        Element elementPeriodOfPayment = elementsPeriodOfPayment.first();
        //System.out.println(elementPeriodOfPayment.text());
        if (elementPeriodOfPayment.text().equals("/miesiąc")) {
            advert.setPeriodOfPeyment("monthly");
        }

        /**
         * Data wygenerowania
         */
        advert.setGenerateAdvertDt(new Date());

        /**
         * area
         */
        Elements elementsArea = source.getElementsByAttributeValueContaining("class", "param_m");
        Element elementArea = elementsArea.first();
        //System.out.println(elementArea.text());
        Pattern patternArea = Pattern.compile("([0-9,]+)");
        Matcher matcherArea = patternArea.matcher(elementArea.text());
        if (matcherArea.find()) {
            //System.out.println("gruper: " + matcherArea.group(0));

            advert.setLivingArea(Double.parseDouble(matcherArea.group(0).replaceAll("\\s+", "").replaceAll(",", ".")));
            advert.setUnityArea(elementArea.text().substring(elementArea.text().length() - 3, elementArea.text().length()));

        }
        /**
         * rooms
         */
        Elements elementsRooms = source.getElementsByAttributeValueContaining("class", "section-offer-params");
        Element elementRooms = elementsRooms.first();
        //System.out.println(elementRooms.text());
        String content = elementRooms.text();

        String[] detailElementsArrayy = content.replaceAll("/", "").split(" ");
        List<String> detailElementList = Arrays.asList(detailElementsArrayy);
        if (detailElementList.contains("pokoi")) {
            advert.setNumberOfRooms(Integer.valueOf(detailElementList.get(detailElementList.indexOf("pokoi") + 1)));
        }
        if (content.contains("Piętro ")) {
            String accurateFloor = "";

            if (detailElementList.get(detailElementList.indexOf("Piętro") + 1).equals(">")) {
                accurateFloor = ">10";
            }else if(!detailElementList.get(detailElementList.indexOf("Piętro") + 1).equals("parter") && !detailElementList.get(detailElementList.indexOf("Piętro") + 1).equals("poddasze")){
                accurateFloor = detailElementList.get(detailElementList.indexOf("Piętro") + 1);
            }else if(detailElementList.get(detailElementList.indexOf("Piętro") + 1).equals("parter")){
                accurateFloor = "parter";
            }else if(detailElementList.get(detailElementList.indexOf("Piętro") + 1).equals("poddasze")){
                accurateFloor = "poddasze";
            }
            Integer allFloor;
            if(content.contains("(z")){
                allFloor = Integer.valueOf(detailElementList.get(detailElementList.indexOf("(z") + 1).replaceAll("\\)", ""));
            }else{
                allFloor=null;
            }
            advert.setAccurateFloor(accurateFloor);
            advert.setAllFloor(allFloor);
        }
        if (content.contains("Kaucja: ")) {
            advert.setDeposit(getPrice(detailElementList.get(detailElementList.indexOf("Kaucja:") + 1) + detailElementList.get(detailElementList.indexOf("Kaucja:") + 2)));
            advert.setDepositeCurency(getCurrency(detailElementList.get(detailElementList.indexOf("Kaucja:") + 3)));


        }
        if (content.contains("zabudowy: ")) {
            advert.setTypeOfDevelopment(detailElementList.get(detailElementList.indexOf("zabudowy:") + 1));
        }
        if (content.contains("Ogrzewanie: ")) {
            advert.setHeating(detailElementList.get(detailElementList.indexOf("Ogrzewanie:") + 1));
        }
        if (content.contains("Rok budowy: ")) {
            String sDate1 = detailElementList.get(detailElementList.indexOf("budowy:") + 1);
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("yyyy").parse(sDate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            advert.setDateBuild(date1);
        }
        if (content.contains("Stan wykończenia: ")) {
            advert.setConditionOfProperty(detailElementList.get(detailElementList.indexOf("wykończenia:") + 1) + " " + detailElementList.get(detailElementList.indexOf("wykończenia:") + 2));
        }
        if (content.contains("Okna: ")) {
            advert.setTypeOfWindow(detailElementList.get(detailElementList.indexOf("Okna:") + 1));
        }
        if (content.contains("Materiał budynku: ")) {
            advert.setTypeOfBuildingMaterial(detailElementList.get(detailElementList.indexOf("Materiał") + 2));
        }
        if (content.contains("Dostępne od: ")) {
            advert.setDateOfAvailability(getDate(detailElementList.get(detailElementList.indexOf("Dostępne") + 2) + detailElementList.get(detailElementList.indexOf("Dostępne") + 3) + detailElementList.get(detailElementList.indexOf("Dostępne") + 4)));
        }
        if (content.contains("Wynajmę również studentom: ")) {
            advert.setAlsoForStudent(detailElementList.get(detailElementList.indexOf("studentom:") + 1));
        }
        if (content.contains("Wyposażenie ")) {
            Pattern pattern = Pattern.compile("([A-Z])");
            int i = 1;
            StringBuilder sb = new StringBuilder();
            while (detailElementList.indexOf("Wyposażenie") + i != detailElementList.size()  && !pattern.matcher(detailElementList.get(detailElementList.indexOf("Wyposażenie") + i)).find()) {
                //System.out.println(detailElementList.get(detailElementList.indexOf("Wyposażenie") + i));
                if(detailElementList.get(detailElementList.indexOf("Wyposażenie") + i)!="/"){
                    sb.append("," + detailElementList.get(detailElementList.indexOf("Wyposażenie") + i));

                }

                i++;
            }
            advert.setEquipment(sb.toString());

        }
        if (content.contains("Zabezpieczenia ")) {
            Pattern pattern = Pattern.compile("([A-Z])");
            int i = 1;
            StringBuilder sb = new StringBuilder();
            while (detailElementList.indexOf("Zabezpieczenia") + i != detailElementList.size()  && !pattern.matcher(detailElementList.get(detailElementList.indexOf("Zabezpieczenia") + i)).find()) {
                //System.out.println(detailElementList.get(detailElementList.indexOf("Zabezpieczenia") + i));

                if(detailElementList.get(detailElementList.indexOf("Zabezpieczenia") + i)!="/"){
                    sb.append("," + detailElementList.get(detailElementList.indexOf("Zabezpieczenia") + i));

                }
                i++;
            }
            advert.setSecurity(sb.toString());

        }
        if (content.contains("Media ")) {
            Pattern pattern = Pattern.compile("([A-Z])");
            int i = 1;
            StringBuilder sb = new StringBuilder();
            while (detailElementList.indexOf("Media") + i != detailElementList.size()  && !pattern.matcher(detailElementList.get(detailElementList.indexOf("Media") + i)).find()) {

                //System.out.println(detailElementList.get(detailElementList.indexOf("Media") + i));
                if(detailElementList.get(detailElementList.indexOf("Media") + i)!="/"){
                    sb.append("," + detailElementList.get(detailElementList.indexOf("Media") + i));
                }
                i++;
            }
            advert.setUtilities(sb.toString());
        }
        if (content.contains("Informacje dodatkowe ")) {
            Pattern pattern = Pattern.compile("([A-Z])");
            int i = 1;
            //System.out.println("Rozmiar: " + detailElementList.size());
            //System.out.println("Dodatkowe: " + detailElementList.indexOf("dodatkowe"));
            StringBuilder sb = new StringBuilder();
            while (detailElementList.indexOf("dodatkowe") + i != detailElementList.size()  && !pattern.matcher(detailElementList.get(detailElementList.indexOf("dodatkowe") + i)).find()) {
                //System.out.println(detailElementList.get(detailElementList.indexOf("dodatkowe") + i));

                if(detailElementList.get(detailElementList.indexOf("dodatkowe") + i)!="/"){
                    sb.append("," + detailElementList.get(detailElementList.indexOf("dodatkowe") + i));
                }
                i++;
            }
            advert.setAdditionalInformation(sb.toString());
        }
        /**
         * latitiude longitiude
         */
        Elements elementsLat = source.getElementsByAttributeValueContaining("id", "mapContainer");
        Element elementLat = elementsLat.first();

        advert.setLatitude(Double.parseDouble(elementLat.attr("data-lat")));
        advert.setLongitude(Double.parseDouble(elementLat.attr("data-lon")));


    }


}

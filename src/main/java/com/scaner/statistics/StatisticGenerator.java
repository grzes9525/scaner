package com.scaner.statistics;

import com.scaner.model.Advert;
import com.scaner.model.AdvertHeighbour;
import com.scaner.repository.AdvertNeighbourRepository;
import com.scaner.repository.AdvertRepository;
import com.scaner.utils.GeoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Component
public class StatisticGenerator {

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private AdvertNeighbourRepository advertNeighbourRepository;


    public void affordableAdvertInWarsaw(Advert checkedAdvert){

        List<Advert> adverts = advertRepository.findByCityAndLatitudeIsNotNull("Warszawa");
        HashMap<Advert,Double> advertDoubleHashMap = new HashMap<>();

        //Streams
        Supplier<Stream<Advert>> advertStreams = ()-> adverts.stream().parallel();

        //NE
        List<Advert> NE_adverts = advertStreams.get().filter(advert -> advert.getLatitude()>checkedAdvert.getLatitude())
                                                .filter(advert -> advert.getLongitude()>checkedAdvert.getLongitude())
                                                .collect(Collectors.toList());
        //NW
        List<Advert> NW_adverts = advertStreams.get().filter(advert -> advert.getLatitude()>checkedAdvert.getLatitude())
                .filter(advert -> advert.getLongitude()<checkedAdvert.getLongitude())
                .collect(Collectors.toList());
        //SE
        List<Advert> SE_adverts = advertStreams.get().filter(advert -> advert.getLatitude()<checkedAdvert.getLatitude())
                .filter(advert -> advert.getLongitude()>checkedAdvert.getLongitude())
                .collect(Collectors.toList());
        //SW
        List<Advert> SW_adverts = advertStreams.get().filter(advert -> advert.getLatitude()<checkedAdvert.getLatitude())
                .filter(advert -> advert.getLongitude()<checkedAdvert.getLongitude())
                .collect(Collectors.toList());

        loadTheNearestAdvert(NE_adverts, advertDoubleHashMap, checkedAdvert, "NE");
        //System.out.println(advertDoubleHashMap.values());


        loadTheNearestAdvert(NW_adverts, advertDoubleHashMap, checkedAdvert, "NW");
        //System.out.println(advertDoubleHashMap.values());

        loadTheNearestAdvert(SE_adverts, advertDoubleHashMap, checkedAdvert, "SE");
        //System.out.println(advertDoubleHashMap.values());

        loadTheNearestAdvert(SW_adverts, advertDoubleHashMap, checkedAdvert, "SW");


        if(getAveragePriceByMeter(advertDoubleHashMap)>checkedAdvert.getPrice()/checkedAdvert.getLivingArea()){
            checkedAdvert.setAffordableInd("Y");
            advertRepository.save(checkedAdvert);
            //zrób najlepszą okazje
            System.out.println(advertDoubleHashMap.values());
            System.out.println(checkedAdvert.getLinkToAd());
            System.out.println("s:"+getAveragePriceByMeter(advertDoubleHashMap));
            System.out.println("cA:"+checkedAdvert.getPrice()/checkedAdvert.getLivingArea());

        }else{
            checkedAdvert.setAffordableInd("N");
            advertRepository.save(checkedAdvert);
        }
    }

    private Double getAveragePriceByMeter(Map<Advert,Double> adverts){
        Double sum=0.0;
        int counter=0;
        if(!adverts.isEmpty()){
            for(Map.Entry<Advert,Double> entry:adverts.entrySet()){
                sum=sum+entry.getKey().getPrice()/entry.getKey().getLivingArea();
                counter++;
            }
        }

        return sum/counter;
    }


    private void loadTheNearestAdvert(List<Advert> adverts, HashMap<Advert,Double> theNearestAdvert, Advert checkedAdvert, String direction){

        if(!adverts.isEmpty()){
            TreeMap<Advert,Double> map = new TreeMap<>();

            for(Advert advert:adverts){
                Double distance =GeoTools.distance(checkedAdvert.getLatitude(),advert.getLatitude(),checkedAdvert.getLongitude(),advert.getLongitude(),0,0);
                map.put(advert,distance);
            }
            //Comparator
            Stream<Map.Entry<Advert,Double>> sorted =
                    map.entrySet().stream()
                            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
            TreeMap<Advert,Double> sortedMap = sorted.collect(toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, TreeMap::new));

            theNearestAdvert.put(sortedMap.firstEntry().getKey(),sortedMap.firstEntry().getValue());


            if(advertNeighbourRepository.existingIndAdNeighbour(checkedAdvert.getDataItemId())>0){
                List<AdvertHeighbour> advertHeighbours = advertNeighbourRepository.findAdvertHeighbourByIdAdvert(checkedAdvert.getDataItemId());
                for(AdvertHeighbour advertHeighbour : advertHeighbours){
                    if(advertHeighbour.getNeighbourDirection().equals(direction)){
                        advertHeighbour.setLatitude(sortedMap.firstEntry().getKey().getLatitude());
                        advertHeighbour.setLongitude(sortedMap.firstEntry().getKey().getLongitude());
                        advertNeighbourRepository.save(advertHeighbour);
                    }
                }

            }else{
                AdvertHeighbour advertHeighbour = new AdvertHeighbour();
                advertHeighbour.setIdAdvert(checkedAdvert.getDataItemId());
                advertHeighbour.setNeighbourDirection(direction);
                advertHeighbour.setLatitude(sortedMap.firstEntry().getKey().getLatitude());
                advertHeighbour.setLongitude(sortedMap.firstEntry().getKey().getLongitude());
                //zrób update przy pomocy Spring data
                advertNeighbourRepository.save(advertHeighbour);
            }


        }

    }





}

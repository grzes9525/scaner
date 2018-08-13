package com.scaner.ScheduledTask;

import com.scaner.generation.GenrateAdvert;
import com.scaner.model.Advert;
import com.scaner.repository.AdvertRepository;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class RunScaner {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final ILoggerFactory log = LoggerFactory.getILoggerFactory();

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private GenrateAdvert genrateAdvert;

    @Scheduled(fixedRate = 6*60*1000)
    public void reportCurrentTime() {
        log.getLogger("run SCANER");

        for(Advert advert1: genrateAdvert.generateAdverts()){
            advertRepository.save(advert1);
        }
        System.out.println("-------------SAVED----------------");

    }
}

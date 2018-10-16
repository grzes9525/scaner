package com.scaner.scheduled_task;

import com.scaner.generation.GenrateAdvert;
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
    private GenrateAdvert genrateAdvert;


    @Scheduled(fixedRate = 100*60*1000)
    public void reportCurrentTime1() {
        log.getLogger("run SCANER");
        genrateAdvert.generateAdverts();
    }

    /*
    @Scheduled(cron = "0 0/30 7-22 * * ?")
    public void reportCurrentTime2() {
        log.getLogger("run SCANER");

        //generuje ogłoszenia z olx
        genrateAdvert.generateAdverts();

    }
/*
    @Scheduled(cron = "0 0/30 1-3 * * ?")
    public void reportCurrentTime3() {
        log.getLogger("run SCANER");

        genrateAdvert.generateAdverts();

    }


    @Scheduled(cron = "0 0/30 7-12 * * ?")
    public void reportCurrentTime4() {
        log.getLogger("run SCANER");

        genrateAdvert.generateAdverts();

    }
    /*
    @Scheduled(cron = "0 0/30 22-23 * * MON-FRI")
    public void reportCurrentTime5() {
        log.getLogger("run SCANER");

        genrateAdvert.generateAdverts();

    }
    */
}

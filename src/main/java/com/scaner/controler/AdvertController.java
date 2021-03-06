package com.scaner.controler;

import com.scaner.model.Advert;
import com.scaner.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdvertController {

    @Autowired
    private AdvertRepository advertRepository;

    @GetMapping(path = "/adverts",produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Advert> getAdverts(){
        return advertRepository.findTop1ByOrderByGenerateAdvertDtDesc();
    }



}

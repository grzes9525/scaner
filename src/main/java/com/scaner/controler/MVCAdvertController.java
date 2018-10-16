package com.scaner.controler;

import com.scaner.model.Advert;
import com.scaner.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MVCAdvertController {

    @Autowired
    private AdvertRepository advertRepository;


    @GetMapping(path = "/list")
    public String listAdvert(Model model) {
        List<Advert> adverts = advertRepository.findTop1ByOrderByGenerateAdvertDtDesc();
        model.addAttribute("advertList", adverts);
        return "list";
    }

    @GetMapping("/")
    public String home(Model model){
        return "home";
    }




}

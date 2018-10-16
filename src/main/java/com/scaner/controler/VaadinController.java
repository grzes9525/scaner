package com.scaner.controler;

import com.scaner.model.Advert;
import com.scaner.repository.AdvertRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringComponent
@UIScope
@Route(value = "vadin")
public class VaadinController extends VerticalLayout {

    private AdvertRepository advertRepository;
    private Grid<Advert> grid;
    private Grid<String> s;

    @Autowired
    public VaadinController(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
        this.grid = new Grid<>(Advert.class);
        this.s = new Grid<>(String.class);

        add(s,grid);

         //żeby alert o recconecie nie załosnił tabeli
        s.setHeight("100px");
        List<Advert> adverts = advertRepository.findAllByDataItemIdIsNotNull();
        System.out.println("Lista ogłszen: "+adverts.get(0));

        grid.setItems(adverts);
    }


}

package com.scaner.service;

import com.scaner.model.Advert;
import com.scaner.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdvertServices {

    @Autowired
    private AdvertRepository advertRepository;

    public List<Advert> findAllAdvert(int offset, int limit, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());

        PageRequest pageRequest = new PageRequest(page, limit, orders.isEmpty() ? null : new Sort(orders));
        List<Advert> items = advertRepository.findAll(pageRequest).getContent();
        return items.subList(offset%limit, items.size());
    }
}

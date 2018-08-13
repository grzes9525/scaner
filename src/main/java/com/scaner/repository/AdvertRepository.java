package com.scaner.repository;

import com.scaner.model.Advert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends CrudRepository<Advert,String>{



}

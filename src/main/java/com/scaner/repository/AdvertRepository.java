package com.scaner.repository;

import com.scaner.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,String> {

    List<Advert> findTop1ByOrderByGenerateAdvertDtDesc();

    List<Advert> findByCityAndLatitudeIsNotNull(String city);

    List<Advert> findAllByDataItemIdIsNotNull();


}

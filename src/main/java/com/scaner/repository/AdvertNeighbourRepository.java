package com.scaner.repository;


import com.scaner.model.AdvertHeighbour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertNeighbourRepository extends JpaRepository<AdvertHeighbour, Long> {

    //jesli zwróci 0 to nic nie ma a jesli 1 to cos jest
    @Query(value = "select count(e.id_advert) from advert_heighbour e where e.id_advert=?1", nativeQuery = true)
    int existingIndAdNeighbour(String idAdvert);

    List<AdvertHeighbour> findAdvertHeighbourByIdAdvert(String id);
}

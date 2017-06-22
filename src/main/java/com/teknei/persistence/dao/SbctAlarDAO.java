package com.teknei.persistence.dao;

import com.teknei.persistence.entities.SbctAlar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Amaro on 21/06/2017.
 */
public interface SbctAlarDAO extends JpaRepository<SbctAlar, Integer> {

    @Query("SELECT t FROM SbctAlar t WHERE UPPER(t.codDisp) LIKE UPPER(?1) OR UPPER(t.codSer) LIKE UPPER(?2) " +
            "AND t.pos = ?3 AND t.estatus = 1")
    List<SbctAlar> findCustomByCodAndPos(String codDisp, String codSer, Integer position);
}

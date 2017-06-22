package com.teknei.persistence.dao;

import com.teknei.persistence.entities.SbctAlar;
import com.teknei.persistence.entities.SbopEquiAlar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Amaro on 21/06/2017.
 */
public interface SbopEquiAlarDAO extends JpaRepository<SbopEquiAlar, Integer> {

    List<SbopEquiAlar> findByIdEquiAndIdEsta(Integer idEqui, Integer idEsta);

    List<SbopEquiAlar> findByIdEquiAndIdAlarAndIdEsta(Integer idEqui, SbctAlar idAlar, Integer idEsta);

}

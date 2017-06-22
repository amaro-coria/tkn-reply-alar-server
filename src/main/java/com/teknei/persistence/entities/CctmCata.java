package com.teknei.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Amaro on 21/06/2017.
 */

@Data
@EqualsAndHashCode(of = { "idCata" })
@Cacheable
@Entity
@Table(name = "cctm_cata")
public class CctmCata implements Serializable {


    @Id
    @SequenceGenerator(name = "CCTM_CATA_IDCATA_GENERATOR", sequenceName = "cctm_cata_id_cata_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CCTM_CATA_IDCATA_GENERATOR")
    @Column(name = "id_cata")
    private Integer idCata;
    @Column(name = "cod_cata")
    private String nameCata;
    @Column(name = "cod_cort")
    private String codCort;
    @Column(name = "cod_comp")
    private String codComp;
    @Column(name = "des_cort")
    private String desCort;
    @Column(name = "des_comp")
    private String desComp;
    @Column(name = "orde_cata")
    private Integer ordCat;
    @Column(name = "id_idio")
    private Integer idio;
    @Column(name = "id_ecat")
    private Integer estat;
    @Column(name = "id_tcat")
    private Integer tipo;
    @Column(name = "usr_crea")
    private String usrc;
    @Column(name = "fch_crea")
    private Timestamp fchc;
    @Column(name = "usr_modi")
    private String usrm;
    @Column(name = "fch_modi")
    private Timestamp fchm;

    public CctmCata(){
    }

    public CctmCata(int idCata) {
        this.idCata = idCata;
    }
}

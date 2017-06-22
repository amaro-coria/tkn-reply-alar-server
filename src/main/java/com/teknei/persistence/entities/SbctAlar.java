package com.teknei.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Amaro on 21/06/2017.
 */
@Entity
@Table(name = "sbct_alar")
@Data
public class SbctAlar implements Serializable {

    @Id
    @SequenceGenerator(name = "SBCT_ALAR_GENERATOR", sequenceName = "sitm.sbct_alar_id_alar_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SBCT_ALAR_GENERATOR")
    @Column(name = "id_alar")
    private Integer idAlar;
    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "id_tipo_alar") })
    private CctmCata idTieqTidi;
    @Column(name = "id_prio")
    private Integer idPrio;
    @Column(name = "des_alar")
    private String desAlar;
    @Column(name = "id_esta")
    private Integer estatus;
    @Column(name = "id_tipo")
    private Integer idTipo;
    @Column(name = "usr_crea")
    private String usrc;
    @Column(name = "fch_crea")
    private Timestamp fchc;
    @Column(name = "usr_modi")
    private String usrm;
    @Column(name = "fch_modi")
    private Timestamp fchm;
    @Column(name = "id_con_sis")
    private Integer idConSis;
    @Column(name = "pos")
    private Integer pos;
    @Column(name = "cod_disp")
    private String codDisp;
    @Column(name = "cod_ser")
    private String codSer;
    @Column(name = "no_rev")
    private Integer noRev;
    @Column(name = "id_tipo_cerr")
    private Integer idTipoCerr;
}

package com.teknei.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Amaro on 21/06/2017.
 */
@Entity
@Table(name = "sbop_equi_alar")
@Data
public class SbopEquiAlar implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3688077162037147362L;
    @Id
    @SequenceGenerator(name = "SBOP_EQUI_ALAR_CGENERATOR", sequenceName = "sitm.sbop_equi_alar_id_equi_alar_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SBOP_EQUI_ALAR_CGENERATOR")
    @Column(name = "id_equi_alar")
    private Integer idEquiAlar;
    @Column(name = "id_equi")
    private Integer idEqui;
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "id_alar")})
    private SbctAlar idAlar;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fch_ini")
    private Date fchIni;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fch_fin")
    private Date fchFin;
    @Column(name = "esta_equi")
    private String estaEqui;
    // @Column(name="ID_USR")
    // private Integer idUsr;
    @Column(name = "id_esta")
    private Integer idEsta;
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
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "id_esta_atn")})
    private CctmCata idEstaAtn;
    @Column(name = "id_emp")
    private Integer idEmp;
    @Column(name = "fch_atn")
    private Timestamp fchAtn;
    @Column(name = "id_tipo_comp")
    private Integer idTipoComp;

}

package com.teknei;

import com.teknei.persistence.dao.SbctAlarDAO;
import com.teknei.persistence.dao.SbopEquiAlarDAO;
import com.teknei.persistence.entities.CctmCata;
import com.teknei.persistence.entities.SbctAlar;
import com.teknei.persistence.entities.SbopEquiAlar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Amaro on 21/06/2017.
 */
@RestController
@RequestMapping(value = "/alarm")
public class AlarController {

    @Autowired
    private SbopEquiAlarDAO daoEquiAlar;
    @Autowired
    private SbctAlarDAO daoSbctAlar;
    private final int CATA_COD_CORT_ALAR_ATN_SOLVED = 732;
    private final int CATA_COD_CORT_ALAR_ATN_UNASSIGNED = 729;

    private static final Logger log = LoggerFactory.getLogger(AlarController.class);

    @RequestMapping(value = "/process/{idEqui}/{alarm}")
    public @ResponseBody
    String processAlarmText(@PathVariable Integer idEqui, @PathVariable String alarm) {
        log.info("Equi: {} , alarm: {}", idEqui, alarm);
        /*
        String[] alars = text.split("\\|");
        if (alars.length < 10) {
            processAlarTor2(alars, idEqui);
        } else {
            processAlarKVR2(alars, idEqui);

        }*/
        return "00";
    }

    /**
     * @param alars
     * @param idEqui
     */
    private void processAlarTor2(String[] alars, Integer idEqui) {
        String cardPresent = null;
        String samStatus = null;
        String adrStatus = null;
        String idMod = null;
        String bdStatus = null;
        String epoch = null;
        cardPresent = alars[0];
        samStatus = alars[1];
        adrStatus = alars[2];
        idMod = alars[3];
        if (alars.length == 6) {// New release
            bdStatus = alars[4];
            epoch = alars[5];
            epoch = epoch + "000";
        } else {
            bdStatus = "0000";
            epoch = alars[4];
            epoch = epoch + "000";
        }

        List<AlarDTO> alarList = new LinkedList<>();
        alarList.add(AlarDTO.buildAlarDTO(cardPresent, 0));
        alarList.add(AlarDTO.buildAlarDTO(samStatus, 1));
        alarList.add(AlarDTO.buildAlarDTO(adrStatus, 2));
        alarList.add(AlarDTO.buildAlarDTO(idMod, 3));
        alarList.add(AlarDTO.buildAlarDTO(bdStatus, 4));
        boolean isAlarClear = true;
        for (AlarDTO dto : alarList) {
            String s = dto.getAlar();
            s = s.trim();
            s = s.toUpperCase();
            if (s.equals("0000") || s.equalsIgnoreCase("230A")) {
                continue;
            } else {
                s = "0X" + s.toUpperCase();
                processAlarPresent2(idEqui, epoch, s, 772, dto.getPos());
                isAlarClear = false;
            }
        }
        if (isAlarClear) {
            List<SbopEquiAlar> existentAlarms = daoEquiAlar.findByIdEquiAndIdEsta(idEqui, 1);
            for (SbopEquiAlar a : existentAlarms) {
                a.setEstaEqui("C");
                a.setIdEsta(2);
                a.setUsrm("admin");
                a.setFchm(new Timestamp(Calendar.getInstance().getTime()
                        .getTime()));
                a.setFchAtn(a.getFchm());
                a.setIdEstaAtn(new CctmCata(CATA_COD_CORT_ALAR_ATN_SOLVED));
                daoEquiAlar.save(a);
            }
        }
    }

    /**
     * @param alars
     * @param idEqui
     */
    private void processAlarKVR2(String alars[], Integer idEqui) {
        String timestamp = alars[0];
        timestamp = timestamp.substring(2);
        Long tsLong = Long.parseLong(timestamp, 16);
        timestamp = String.valueOf(tsLong);
        timestamp = timestamp + "000";
        List<AlarDTO> alarList = new LinkedList<>();
        for (int i = 1; i < alars.length; i++) {
            alarList.add(AlarDTO.buildAlarDTO(alars[i], i));
        }
        boolean isAlarClear = true;
        for (AlarDTO dto : alarList) {
            String s = dto.getAlar();
            s = s.toUpperCase();
            s = s.trim();
            if (s.contains("0X0000")) {
                continue;
            } else {
                processAlarPresent2(idEqui, timestamp, s, 773, dto.getPos());
                isAlarClear = false;
            }
        }
        if (isAlarClear) {
            List<SbopEquiAlar> existentAlar = daoEquiAlar.findByIdEquiAndIdEsta(idEqui, 1);
            for (SbopEquiAlar a : existentAlar) {
                a.setEstaEqui("C");
                a.setIdEsta(2);
                a.setUsrm("admin");
                a.setIdEstaAtn(new CctmCata(CATA_COD_CORT_ALAR_ATN_SOLVED));
                a.setFchm(new Timestamp(Calendar.getInstance().getTime()
                        .getTime()));
                a.setFchAtn(a.getFchm());
                daoEquiAlar.save(a);
            }
        }
    }


    private static class AlarDTO implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private String alar;
        private Integer pos;

        public AlarDTO(String alar, Integer pos) {
            super();
            this.alar = alar;
            this.pos = pos;
        }

        public static AlarDTO buildAlarDTO(String alar, Integer pos) {
            return new AlarDTO(alar, pos);
        }

        /**
         * @return the alar
         */
        public String getAlar() {
            return alar;
        }

        /**
         * @return the pos
         */
        public Integer getPos() {
            return pos;
        }

    }

    private void processAlarPresent2(Integer idEqui, String timestamp,
                                     String nameAlar, Integer idTipoAlarDevice, Integer pos) {
        List<SbctAlar> listAlarCata = daoSbctAlar.findCustomByCodAndPos(nameAlar, nameAlar, pos);
        if (!CollectionUtils.isEmpty(listAlarCata)) {
            SbctAlar configAlarm = listAlarCata.get(0);
            List<SbopEquiAlar> listExistentAlarms = daoEquiAlar.findByIdEquiAndIdAlarAndIdEsta(idEqui, configAlarm, 1);
            if (listExistentAlarms == null || CollectionUtils.isEmpty(listExistentAlarms)) {
                SbopEquiAlar alarm = new SbopEquiAlar();
                if (configAlarm.getNoRev().equals(0)) {
                    alarm.setEstaEqui("M");
                } else {
                    alarm.setEstaEqui("1");
                }
                alarm.setIdEsta(1);
                alarm.setFchc(new Timestamp(Calendar.getInstance()
                        .getTime().getTime()));
                alarm.setFchIni(parseTS(timestamp));
                alarm.setFchFin(parseTS(timestamp));
                alarm.setIdEqui(idEqui);
                alarm.setIdAlar(configAlarm);
                alarm.setIdTipo(4);
                alarm.setUsrc("admin");
                alarm.setIdEstaAtn(new CctmCata(CATA_COD_CORT_ALAR_ATN_UNASSIGNED));
                daoEquiAlar.save(alarm);

            } else {
                SbopEquiAlar existentAlar = listExistentAlarms.get(0);
                String estaAlar = existentAlar.getEstaEqui();
                if (!estaAlar.equals("M")) {
                    Integer revisions = Integer.parseInt(existentAlar
                            .getEstaEqui());
                    ++revisions;
                    if (revisions >= configAlarm.getNoRev()) {
                        existentAlar.setEstaEqui("M");
                    } else {
                        existentAlar.setEstaEqui(String
                                .valueOf(revisions));
                    }
                }
                existentAlar.setUsrm("admin");
                existentAlar.setFchm(new Timestamp(Calendar
                        .getInstance().getTime().getTime()));
                daoEquiAlar.save(existentAlar);
            }
        }
    }

    /**
     * Parses timestamp from string
     *
     * @param timeString the timestamp in string
     * @return the timestamp as object
     */
    private Timestamp parseTS(String timeString) {
        if (timeString == null) {
            return null;
        }
        Long time = Long.parseLong(timeString);
        // time = time * 1000;
        Timestamp ts = new Timestamp(time);
        return ts;
    }

}

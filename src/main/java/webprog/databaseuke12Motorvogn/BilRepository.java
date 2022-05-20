package webprog.databaseuke12Motorvogn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.PublicKey;
import java.util.List;

@Repository
public class BilRepository {

    @Autowired
    private JdbcTemplate database;
    private Logger logger= LoggerFactory.getLogger(BilRepository.class);


    private boolean validerBil(Bil bil){ // bestemmer input-vilkår
        String regexpPersonnr = "[0-9]{11}";
        String regexpNavn ="[a-zæøåA-zæøå]{1,20}";
        String regexpAdresse = "[0-9a-zæøåA-ZÆØÅ. \\-]{2,40}";
        String regexpKjennetegn = "[a-zæøåA-zæøå0-9]{1,8}";
        //         regexp = regular expressions.

        boolean personnrOK = bil.getPersonnr().matches(regexpPersonnr);
        boolean navnOK = bil.getNavn().matches(regexpNavn);
        boolean adresseOK = bil.getAdresse().matches(regexpAdresse);
        boolean kjennetegnOK = bil.getKjennetegn().matches(regexpKjennetegn);

        if(personnrOK && navnOK && adresseOK && kjennetegnOK){
            return true;
        } else {
            return false;
        }
    }

    public boolean lagreBil(Bil innBil){ // endre type fra Void til Bolean
        String sql = "INSERT INTO BIL (personnr, navn, adresse, kjennetegn, bilmerke, biltype) VALUES (?, ?, ?, ?, ?, ?)";
        //database.update(sql, innBil.getPersonnr(), innBil.getNavn(), innBil.getAdresse(), innBil.getKjennetegn(),innBil.getBilmerke(), innBil.getBiltype());

        try{
            database.update(sql, innBil.getPersonnr(), innBil.getNavn(), innBil.getAdresse(),
                    innBil.getKjennetegn(), innBil.getBilmerke(), innBil.getBiltype());
            return true;
        } catch(Exception e){
            logger.error("Feil i lagreBil metode: " + e);
            return false;

        }
    }

    public List<Bil> hentUt(){
        String sql = "SELECT * FROM Bil ORDER BY navn ASC";
        List<Bil> alleBiler = database.query(sql, new BeanPropertyRowMapper<>(Bil.class)); // row- etter lengden i class=Bil
        return alleBiler;
    }
    public Bil hentEnBil(int id){
        String sql = "SELECT * FROM Bil WHERE id=?";
        List<Bil> enBil = database.query(sql, new BeanPropertyRowMapper(Bil.class), id);
        return enBil.get(0);   // sender allti første rad = 0 indeks.
    }

    public void endreEnBil(Bil innBil){
        String sql ="UPDATE Bil SET personnr=?, navn=?, adresse=?, kjennetegn=?, bilmerke=?, biltype=? WHERE id=?";
        database.update(sql, innBil.getPersonnr(), innBil.getNavn(), innBil.getAdresse(), innBil.getKjennetegn(),
                innBil.getBilmerke(), innBil.getBiltype());
    }

    public List<Modell> hentUtM() {
        String sql = "SELECT * FROM Modell";
        return database.query(sql,new BeanPropertyRowMapper(Modell.class));
    }


    public void slettAlleBiler(){  //    NB!      VOID  - returnerer ikke noe.
        String sql = "DELETE FROM Bil";
        database.update(sql);
    }
    public void slettenbil(int id){
        String sql = "DELETE FROM Bil WHERE  id=?";
        database.update(sql, id);
    }
}

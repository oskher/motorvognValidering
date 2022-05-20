package webprog.databaseuke12Motorvogn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class BilController {

    @Autowired
    private BilRepository rep;

    private Logger logger = LoggerFactory.getLogger(BilController.class); // nb! bilcontroller ikke bil

    @PostMapping("/registrer")
    public void registrer(Bil innRandomBil, HttpServletResponse response) throws IOException { // If validering=feil så skal ikke lagres:
        if(validerBilOK(innRandomBil)) {
            if (!rep.lagreBil(innRandomBil)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i database, prøv igjen senere");
            }
        }else{
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil, prøv igjen senere");
        }

        //rep.lagreBil(innRandomBil);
    }
    @GetMapping("/henteEnMotorvogn")
    public Bil hentEnBil(int id){
        return rep.hentEnBil(id);
    }

    @PostMapping("/endreEn")  // Lagrer verdier
    public void endre(Bil innBil, HttpServletResponse response) throws IOException {
        if (validerBilOK(innBil)) {
            rep.endreEnBil(innBil);
        }
    else{
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil, prøv igjen senere");
    }
    }

    @GetMapping("/printBiler")
    public List<Bil> printUt(){

        return rep.hentUt();
    }
    @GetMapping("/printMerke")
    public List <Modell> printUtM(){
        return rep.hentUtM();
    }


    @GetMapping("/slettAlle")
    public void slettAlleMetodeNavn(){
        rep.slettAlleBiler();
    }

    @GetMapping("/slettEnBil")
    public void slettEn(int id){
        rep.slettenbil(id);
    }

    private boolean validerBilOK(Bil bil){
        String regexpPersonnr = "[0-9]{11}";
        String regexpNavn ="[a-zA-ZæøåÆØÅ. \\-]{1,20}"; // intet mellomrom Noe som helst sted i RegExp
        String regexpAdresse = "[0-9a-zæøåA-ZÆØÅ. \\-]{2,40}";  //  Her skal \\ 2 backslasher brukes i java.
        String regexpKjennetegn = "[A-Z]{2}[0-9]{5}";
        String regexpMerke = "[0-9a-zA-ZæøåÆØÅ. \\-]{2,10}";

        boolean personnrOK = bil.getPersonnr().matches(regexpPersonnr);
        logger.error("1" + personnrOK); // Brukt for å se etter feil i logger. Må ikke ha på eksamen
        boolean navnOK = bil.getNavn().matches(regexpNavn);
        logger.error("2" + navnOK);
        boolean adresseOK = bil.getAdresse().matches(regexpAdresse);
        logger.error("3" + adresseOK);
        boolean kjennetegnOK = bil.getKjennetegn().matches(regexpKjennetegn);
        logger.error("4" + kjennetegnOK + bil.getKjennetegn());
        boolean merkeOK = bil.getBilmerke().matches(regexpMerke);
        logger.error("5" + merkeOK);

        if(navnOK && personnrOK && adresseOK && kjennetegnOK && merkeOK){
            return true;
        }
        // else: Må ikke skrive else for at det blir else
        logger.error("valideringsfeil");
        return false;

    }
}

function validerPersonNr(){
    const personNr =$("#personNr").val();
    const regexp = /^[0-9]{11}$/;
    const ok = regexp.test(personNr);  //  Ferdig-JS-metode som tester om det stemmer. gir: true, false i dette tilfell.

    if(!ok){
        $("#feilPersonNr").html("Personnummeret må bestå av 11 siffer");
        return false; // False pga den ikke er ok. (tross (!ok) fordi vi bestemmer false, true statement)
    } else{
        $("#feilPersonNr").html("");
        return true;
    }
}
function validerNavn(){
    const personNr =$("#navn").val();
    const regexp = /^[a-zA-ZæøåÆØÅ. \-]{1,20}$/;  // [a-zA-ZæøåÆØÅ] og
    const ok = regexp.test(personNr);

    if(!ok){
        $("#feilNavn").html("Navn må bestå av 2-20 bokstaver");
        return false;
    } else{
        $("#feilNavn").html("");
        return true;
    }
}

function validerAdresse(){
    const adresse = $("#adresse").val();
    const regexp = /^[a-zA-ZæøåÆØÅ0-9 \-]{2,40}$/;
    const ok = regexp.test(adresse);

    if (!ok){
        $("#feilAdresse").html("Adressen må bestå sv 2-40 tegn");
        return false;
    }else{
        $("#feilAdresse").html("");
        return true;
    }
}

function validerKjennetegn() {
    const kjennetegn = $("#kjennetegn").val();
    const regexp = /^[A-Z]{2}[0-9]{5}$/;
    const ok = regexp.test(kjennetegn);

    if (!ok) {
        $("#feilKjennetegn").html("Kjennetegn må ha 2 store bokstaver og 5 tall");
        return false;
    } else {
        $("#feilKjennetegn").html("");   // Tror.. - Må ha empty her pga span krever at noe skal skrives ut.
        return true;
    }
}
function validerMerke(){
    const merke = $("#merke").val();
    /*const regexp = /^[a-zA-Z]{1,10}$/;
    const ok = regexp.test(merke);*/

    if(merke === "Velg merke"){                         // Må sjekke at det er valgfri merke
        $("#feilMerke").html("Velg et merke!")
        return false;
    }else{
        $("#feilMerke").html("");
        return true;
    }
}




function ingenValideringsFeil(){
    return (validerPersonNr() && validerNavn() && validerAdresse() && validerKjennetegn() && validerMerke());
}
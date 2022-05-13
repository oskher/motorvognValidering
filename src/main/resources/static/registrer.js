$(function(){  // kjøres når dokumentet er ferdig lastet
    hentMerke(); // nedtrekksMetode
});




function lagre(){
    console.log("1")
    const Bil ={ // 1.lagre som attributter i objekt
        personnr : $("#personNr").val(),
        navn : $("#navn").val(),
        adresse : $("#adresse").val(),
        kjennetegn : $("#kjennetegn").val(),
        bilmerke : $("#valgtMerke").val(),
        biltype : $("#valgtType").val()
        /* Hvorfor trenger jeg en 3. konstruktør/en egen i JS?*/
    };

    if(ingenValideringsfeil()){ // valideringsfeil

        const url = "/registrer"; // Inge feil? -Lagre på server.
        $.post(url, Bil, function(){
            henteData();

        }).fail(function (jqXHR) {  //  Metode for feil i server-lagring
            const json = $.parseJSON(jqXHR.responseText);

            $("#feil").html(json.message);
        });

        window.location.href = "/";  // refresher/oppdaterer siden til å vise nyeste endringer på index.html
    }
}
function henteData(){
    let ut="<table class='table table-striped'>";
    ut += "<tr> " +
        "<th>Personnr:</th> " +
        "<th>Navn</th>" +
        "<th>Adresse</th>" +
        "<th>Kjennetegn</th>" +
        "<th>Bilmerke</th>" +
        "<th>Biltype</th>"+ "<th></th>"+
        "</tr>";

    $.get("/printBiler", function (dataFraBilRegister){ // parameter kan hete hva som helst, men kommer fra server.
        for(let bil of dataFraBilRegister) {
            ut += "<tr>" +
                "<td>" +bil.personnr + "</td>" +
                "<td>" + bil.navn + "</td>" +
                "<td>"+ bil.adresse + "</td>" +
                "<td>" + bil.kjennetegn + "</td>" +
                "<td>" + bil.bilmerke + "</td>" +
                "<td>" + bil.biltype + "</td>" +
                // "<td><a href='endre.html' class='btn btn-primary' >Endre</a></td>" +
                "<td><button class='btn primary' onclick='endreEn(" + bil.id + ")'>Endre</button></td>" +
                "<td><button class='btn btn-danger' onclick='slettEn(" + bil.id + ")'>Slett</button></td>" +
                "</tr>";
        }
        ut+="</table>"
        $("#div1").html(ut);
    });
}
function endreEn(id){  // Denne tar inn en id valgt fra registrer.html
    window.location.href = "/endre.html?" + id;   // id lagret her, må søkes etter med metode henteEn()
}

function hentMerke() {
    $.get( "/printMerke", function( biler ) {
        formaterBiler(biler);
    });
}
function formaterBiler(biler){
    let ut = "<select id='valgtMerke'>";

    let forrigeMerke = "";
    ut+="<option>Velg merke</option>";
    for (const bil of biler){
        if(bil.merke != forrigeMerke){ //  Her brukes merke fra POJO: Modell,  IKKE Bil. Modell skal vises
            ut+="<option value='" + bil.merke + "'>" +bil.merke + "</option>";
        }
        forrigeMerke = bil.merke;
    }
    ut+="</select>";
    $("#merke").html(ut);

    $("#merke").change(function (){  // Change  = onchange i JS
        let utT = "<select id='valgtType'>";
        const valgtMerke = $("#valgtMerke").val();
        $("#feilMerke").html("Feil input")
        for(const bil of biler ){
            if(bil.merke === valgtMerke){
                utT+="<option>"+bil.type+"</option>";
            }
        }
        utT +="</select>";
        $("#type").html(utT);
    });
}



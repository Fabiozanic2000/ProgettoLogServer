function ordina(unione) {
    for (var i = 0; i < unione.length-1; i++) {
        for (var j = i+1; j < unione.length; j++) {
            if (unione[i].data > unione[j].data) {
                const tmp = unione[i];
                unione[i] = unione[j];
                unione[j] = tmp;
            }
        }
    }
    return unione;
}


const costruisciLog = function (dati) {
    var tabella = "";
    const oggetto = JSON.parse(dati);


    var unione = [];
    oggetto.log.forEach(log => {
        unione.push({ "timestamp": log.timestamp, "paese": log.paese, "tipo": "buono", "data": log.data });
    });
    oggetto.err.forEach(err => {
        unione.push({ "timestamp": err.timestamp, "paese": err.paese, "tipo": "errore", "data": err.data });
    });

    unione = ordina(unione);

    for (var i = 0; i < unione.length; i++) {
        if (i == 0) //se è il primo
            tabella += "<p id='primo' className='" + unione[i].tipo + "'>";
        else if (i == unione.length - 1) // se è l'ultimo
            tabella += "<p id='ultimo' className='" + unione[i].tipo + "'>";
        else
            tabella += "<p className='" + unione[i].tipo + "'>";

        tabella += unione[i].timestamp + ", " + unione[i].paese;
        tabella += "</p>";
    }


    /*
    //inserisco i log
    for (var i = 0; i < oggetto.log.length; i++) { 
        if (i == 0) //se è il primo
            tabella += "<p id='primo' className='buono'>";
        else
            tabella += "<p className='buono'>";

        tabella += oggetto.log[i].timestamp + ", " + oggetto.log[i].paese;
        tabella += "</p>";
    }

    //inserisco gli errori
    for (var i = 0; i < oggetto.err.length; i++) { 
        if (i == oggetto.err.length-1) // se è l'ultimo
            tabella += "<p id='ultimo' className='errore'>";
        else
            tabella += "<p className='errore'>";

        tabella += oggetto.log[i].timestamp + ", " + oggetto.log[i].paese;
        tabella += "</p>";
    }
    */

    return tabella;
}

export default costruisciLog
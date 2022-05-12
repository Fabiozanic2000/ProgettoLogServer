const datiGrafici = function (risposta2) {
    const colori = ["#33cc33", "red", "blue", "yellow", "grey", "green", "white", "orange", "black"]; //colori dei grafici
    const buoni = risposta2.data.log.length; //quante comunicazione avvenute ci sono
    const errori = risposta2.data.err.length; //quante comunicazione fallite ci sono

    //grafico delle comunicazioni
    const datiGraficoComunicazioni = { 
        labels: ["Avvenute", "Errori"], //lista delle labels della torta
        datasets: [{
            label: "Comunicazioni",
            data: [buoni, errori], //dati che usa per creare il grafico
            backgroundColor: colori //colora le barre
        }],
    };

    //costrutto per il grafico dei posti
    //i try servono perchè non sempre ci sono i log o gli errori, quindi se uno fallisco provo l'altro e se fallisce anche il secondo allora non c'è niente
    var posti = [];
    try {
        posti.push({paese: risposta2.data.log[0].paese, numeri: 0}); //inizializzo l'oggetto dei posti con i relativi contatori
    } catch {
        try {
            posti.push({paese: risposta2.data.err[0].paese, numeri: 0});
        }
        catch {
            return;
        }
    }

    //scorro i log
    risposta2.data.log.forEach((dato) => {
        var trovato = false;
        for (var i = 0; i < posti.length; i++) { //se il paese è giò presente nell'array posti, incremento il contatore, altrimenti lo aggiungo
            if (posti[i].paese === dato.paese) {
                trovato = true;
                posti[i].numeri += 1;
                break;
            }
        }
        if (!trovato)
                posti.push({paese: dato.paese, numeri: 1});
    });

    //scorro gli errori
    risposta2.data.err.forEach((dato) => {
        var trovato = false;
        for (var i = 0; i < posti.length; i++) { //se il paese è giò presente nell'array posti, incremento il contatore, altrimenti lo aggiungo
            if (posti[i].paese === dato.paese) {
                trovato = true;
                posti[i].numeri += 1;
                break;
            }
        }
        if (!trovato)
                posti.push({paese: dato.paese, numeri: 1});
    });

    //costruisco il grafico dei posti
    const datiGraficoPosti = {
        labels: posti.map((posto) => posto.paese), //lista delle labels della torta
        datasets: [{
            label: "Posti",
            data: posti.map((posto) => posto.numeri), //dati che usa per creare il grafico
            backgroundColor: colori //colora le barre
        }],
    };

    return [datiGraficoComunicazioni, datiGraficoPosti];
}

export default datiGrafici;
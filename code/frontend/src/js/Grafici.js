import { useState } from "react";
import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto"; //questo serve per mostrare il grafico
import {useEffect} from 'react';
import "../css/Grafici.css";
import axios from 'axios';
axios.defaults.withCredentials = true;


const Grafici = () => {

    const [datiGraficoComunicazioni, setDatiGraficoComunicazioni] = useState({
        labels: ["Deafult"], //lista delle labels della torta
        datasets: [{
            label: "Comunicazioni",
            data: [1], //dati che usa per creare il grafico
            backgroundColor: ["black"] //colora le barre
        }],
    });

    //costruisco il grafico dei posti
    const [datiGraficoPosti, setDatiGraficoPosti] = useState({
        labels: ["Deafult"], //lista delle labels della torta
        datasets: [{
            label: "Posti",
            data: [1], //dati che usa per creare il grafico
            backgroundColor: ["black"] //colora le barre
        }],
    });

    //colora le label
    const labels = {
        plugins: {
            legend: {
                display: true,
                labels: {
                    color: "white",
                    font: {
                        size: 20
                    }
                }
            }
        }
    }

    useEffect(async () => { //una volta caricata la pagina
        const urlParams = new URLSearchParams(window.location.href); //oggetto che legge i parametri dell'url

        //ottengo i parametri
        var testo = urlParams.get('testo');
        var stato = urlParams.get('stato');
        var from = urlParams.get('from');
        var to = urlParams.get('to');
        var scegli = urlParams.get('scegli');

        //controllo che i campi siano diversi da null
        if (testo === null) testo = "";
        if (stato === null) stato = "";
        if (from === null) from = Math.round(new Date().getTime() / 1000);
        if (to === null) to = Math.round(new Date().getTime() / 1000);
        if (scegli === null) scegli = "";
            
        //faccio la richiesta al server
        const url2 = "http://localhost:9000/query";
        const corpo = {testo: testo, stato: stato, from: from, to: to, scegli: scegli, withCredentials: true};
        const risposta2 = await axios.post(url2, corpo);

        const colori = ["#33cc33", "red", "blue", "yellow", "grey", "green", "white", "orange", "black"]; //colori dei grafici
        const buoni = risposta2.data.log.length; //quante comunicazione avvenute ci sono
        const errori = risposta2.data.err.length; //quante comunicazione fallite ci sono

        //grafico delle comunicazioni
        setDatiGraficoComunicazioni({ 
            labels: ["Avvenute", "Errori"], //lista delle labels della torta
            datasets: [{
                label: "Comunicazioni",
                data: [buoni, errori], //dati che usa per creare il grafico
                backgroundColor: colori //colora le barre
            }],
        });

        //costrutto per il grafico dei posti
        var posti = [];
        posti.push({paese: risposta2.data.log[0].paese, numeri: 0}); //inizializzo l'oggetto dei posti con i relativi contatori

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
        setDatiGraficoPosti({
            labels: posti.map((posto) => posto.paese), //lista delle labels della torta
            datasets: [{
                label: "Posti",
                data: posti.map((posto) => posto.numeri), //dati che usa per creare il grafico
                backgroundColor: colori //colora le barre
            }],
        });
    }, []);
    
    return ( 
        <div className="grafici">
            <table id="tabellaGrafici">
                <tbody>
                    <tr>
                        <td className="cellaGrafici">
                            <Pie data={datiGraficoComunicazioni} className="torta" options = {labels}/>; {/* grafico delle comunicazioni */}
                        </td>
                        <td className="cellaGrafici">
                            <Pie data={datiGraficoPosti} className="torta" options = {labels}/>; {/* grafico delle comunicazioni */}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}
 
export default Grafici;
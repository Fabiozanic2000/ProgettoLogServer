import { useState } from "react";
import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto"; //questo serve per mostrare il grafico
import "../css/Grafici.css";

const Grafici = () => {
    //esempio semplificato dati dei log
    const dati = [{comunicazione: "buono", paese: "Italia", id: 2}, {comunicazione: "errore", paese: "Russia", id:1}, {comunicazione: "buono", paese: "Francia", id:2}, {comunicazione: "buono", paese: "Italia", id: 2}];

    // colori del grafico
    const colori = ["#33cc33", "red", "blue", "yellow", "grey", "green", "white", "orange", "black"]


    //costrutto per il grafico delle comunicazioni
    var buoni = 0;
    var errori = 0;
    dati.forEach((dato) => {
        if (dato.comunicazione === "buono")
            buoni += 1;
        else
            errori += 1;
    });

    //costruisco il grafico delle comunicazioni
    const [datiGraficoComunicazioni, setDatiGraficoComunicazioni] = useState({
        labels: ["Avvenute", "Errori"], //lista delle labels della torta
        datasets: [{
            label: "Comunicazioni",
            data: [buoni, errori], //dati che usa per creare il grafico
            backgroundColor: colori //colora le barre
        }],
    });

    //costrutto per il grafico dei posti
    var posti = [];
    posti.push({paese: dati[0].paese, numeri: 0});
    dati.forEach((dato) => {
        var trovato = false;
        for (var i = 0; i < posti.length; i++) {
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
    const [datiGraficoPosti, setDatiGraficoPosti] = useState({
        labels: posti.map((posto) => posto.paese), //lista delle labels della torta
        datasets: [{
            label: "Posti",
            data: posti.map((posto) => posto.numeri), //dati che usa per creare il grafico
            backgroundColor: colori //colora le barre
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
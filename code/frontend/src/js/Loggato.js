import PaginaNonTrovata from './PaginaNonTrovata';
import Home from './Home';
import NavbarLoggato from './NavbarLoggato';
import PagFiltra from './PagFiltra';
import Signup from './Signup';
import Elimina from './Elimina';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'; //servono per andare in diverse pagine
import {useEffect} from 'react';
import {useState} from 'react';
import axios from 'axios';
import datiGrafici from '../funzioni/datiGrafici';
axios.defaults.withCredentials = true;

const Loggato = () => {

    //costruisco il grafico delle comunicazioni di default
    const [datiGraficoComunicazioni, setDatiGraficoComunicazioni] = useState({
        labels: ["Deafult"], //lista delle labels della torta
        datasets: [{
            label: "Comunicazioni",
            data: [1], //dati che usa per creare il grafico
            backgroundColor: ["black"] //colora le barre
        }],
    });

    //costruisco il grafico dei posti di default
    const [datiGraficoPosti, setDatiGraficoPosti] = useState({
        labels: ["Deafult"], //lista delle labels della torta
        datasets: [{
            label: "Posti",
            data: [1], //dati che usa per creare il grafico
            backgroundColor: ["black"] //colora le barre
        }],
    });

    const [nome, setNome] = useState('');
    const [professione, setProfessione] = useState('');    

    useEffect(async () => { //una volta caricata la pagina
        const url = "http://localhost:9000/verifica"; //url al server java, controllo di essere loggato
        const risposta = await axios.post(url);
        if (!risposta.data.nome)      
            window.location.href = "http://localhost:3000";
        else {
            setNome(risposta.data.nome);
            setProfessione(risposta.data.professione);
        }

        //QUERY PER LEGGERE I LOG
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

        const url2 = "http://localhost:9000/query";
        const corpo = {testo: testo, stato: stato, from: from, to: to, scegli: scegli, withCredentials: true};

        const risposta2 = await axios.post(url2, corpo);

        var comunicazioni, posti; //creo i grafici e li passo al componente appropriato
        [comunicazioni, posti] = datiGrafici (risposta2);
        setDatiGraficoComunicazioni(comunicazioni);
        setDatiGraficoPosti(posti);

    }, []);
    
    return ( 
        <Router>
            <div className="loggato">
                <NavbarLoggato professione={professione}/>

                <Switch>

                    <Route exact path='/home'>
                        <Home nome={nome} datiGraficoComunicazioni={datiGraficoComunicazioni} datiGraficoPosti={datiGraficoPosti}/>
                    </Route>

                    <Route exact path='/filtra'>
                        <PagFiltra />
                    </Route>

                    <Route exact path='/registra'>
                        <Signup professione = "tecnico"/>
                    </Route>

                    <Route exact path='/elimina'>
                        <Elimina />
                    </Route>

                    <Route path='*'>  {/* una qualsiasi altra pagina*/}
                        <PaginaNonTrovata />
                    </Route>
                    
                </Switch>
            </div>
        </Router>
    );
}
 
export default Loggato;
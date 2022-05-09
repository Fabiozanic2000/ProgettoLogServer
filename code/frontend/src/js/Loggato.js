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
axios.defaults.withCredentials = true;

const Loggato = () => {

    const [nome, setNome] = useState('');
    const [professione, setProfessione] = useState('');

    useEffect(async () => { //una volta caricata la pagina
        const url = "http://localhost:9000/verifica"; //url al server java
        const risposta = await axios.post(url);
        if (!risposta.data.nome)
            window.location.href = "http://localhost:3000/";
        else {
            setNome(risposta.data.nome);
            setProfessione(risposta.data.professione);
        }
    });

    return ( 
        <Router>
            <div className="loggato">
                <NavbarLoggato professione={professione}/>

                <Switch>

                    <Route exact path='/home'>
                        <Home nome={nome}/>
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

                    <Route path=''>  {/ una qualsiasi altra pagina*/}
                        <PaginaNonTrovata />
                    </Route>

                </Switch>
            </div>
        </Router>
    );
}
 
export default Loggato;
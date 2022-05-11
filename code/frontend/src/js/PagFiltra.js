import Log from './Log';
import Spiegazione from './Spiegazione';
import Filtra from './Filtra';
import costruisciLog from '../funzioni/log';
import {useEffect, useState} from 'react';
import axios from 'axios';
axios.defaults.withCredentials = true;

const PagFiltra = (props) => {

    //dati da mandare alla pagina del filtro
    const [tabella, setTabella] = useState('');

    useEffect(async () => { //una volta caricata la pagina
        const url = "http://localhost:9000/verifica"; //url al server java
        const risposta = await axios.post(url);
        if (risposta.data.professione == "cliente")  // se sei un cliente non puoi accedere
            window.location.href = "http://localhost:3000/home";

        //alert(props.dati);
        //alert(costruisciLog(props.dati));
        setTabella(costruisciLog(props.dati));
        
    }, []);

    return ( 
        <div className="pagfiltra">
            <table>
                <tbody>
                    <tr>
                        <td className="tabellaLog">
                            <Log tabella={tabella}/>
                        </td>
                        <td className="tabellaLog">
                            <Spiegazione />
                        </td>
                    </tr>
                </tbody>
            </table>

            <Filtra />
        </div>
    );
}
 
export default PagFiltra;
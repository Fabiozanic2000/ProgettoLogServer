import Log from './Log';
import Spiegazione from './Spiegazione';
import Filtra from './Filtra';
import {useEffect} from 'react';
import axios from 'axios';
axios.defaults.withCredentials = true;

const PagFiltra = () => {

    useEffect(async () => { //una volta caricata la pagina
        const url = "http://localhost:9000/verifica"; //url al server java
        const risposta = await axios.post(url);
        if (risposta.data.professione == "cliente") { // se sei un cliente non puoi accedere

            window.location.href = "http://localhost:3000/home";
        }
    });

    return ( 
        <div className="pagfiltra">
            <table>
                <tbody>
                    <tr>
                        <td className="tabellaLog">
                            <Log />
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

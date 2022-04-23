import Filtra from './Filtra';
import Mappa from './Mappa';
import NavbarLoggato from './NavbarLoggato';
import '../css/tabella.css';
import '../css/Home.css';
import Log from './Log';
import Spiegazione from './Spiegazione';

const Home = () => {
    return ( 
        <div className="home">
            <NavbarLoggato />
            <h1>Benvenuto, Alex</h1>
            <table>
                <tr>
                    <td id='cellaFIltra'>
                        <Filtra />
                    </td>
                    <td>
                        <Mappa />
                    </td>
                </tr>
            </table>
            
            <table>
                <tr>
                    <td class="tabellaLog">
                        <Log />
                    </td>
                    <td class="tabellaLog">
                        <Spiegazione />
                    </td>
                    <td>
                        <Spiegazione />
                    </td>
                </tr>
            </table>
            
        </div>
     );
}
 
export default Home;
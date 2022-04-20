import Filtra from './Filtra';
import Mappa from './Mappa';
import '../css/tabella.css';
const Home = () => {
    return ( 
        <div className="home">
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
            
            
        </div>
     );
}
 
export default Home;
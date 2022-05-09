import Mappa from './Mappa';
import Grafici from './Grafici';
import '../css/tabella.css';
import '../css/Home.css';

const Home = (props) => {

    return ( 
        <div className="home">
            <h1>Benvenuto, {props.nome}</h1>

            <Mappa />


            <Grafici />

        </div>
    );
}
 
export default Home;
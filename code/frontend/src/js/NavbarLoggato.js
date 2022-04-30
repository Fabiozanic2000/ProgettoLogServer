import {Link} from 'react-router-dom'; //se usi link invece di <a href> non passi per il server
import '../css/NavbarLoggato.css';
import axios from 'axios';
axios.defaults.withCredentials = true;

const NavbarLoggato = () => {
    const clickBottone = async (e) =>
    {
        const url = 'http://localhost:9000/logout';
        const risposta = await axios.post(url);
        window.location.href = window.location.href.replace("/home", "/");
    }
    return ( 
        <nav className="navbar">
            <div className="divLink">
                <Link to = '/' onClick = {clickBottone} id='link'>Logout</Link>
            </div>
        </nav>
    );
}
 
export default NavbarLoggato;
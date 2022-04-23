import {Link} from 'react-router-dom'; //se usi link invece di <a href> non passi per il server
import '../css/NavbarLoggato.css';


const NavbarLoggato = () => {
    return ( 
        <nav className="navbar">
            <div className="divLink">
                <Link to='/' id='link'>Logout</Link>
            </div>
        </nav>
    );
}
 
export default NavbarLoggato;
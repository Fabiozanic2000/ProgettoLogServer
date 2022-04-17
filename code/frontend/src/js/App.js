import '../css/App.css';
import Login from './Login';
import Signup from './Signup';
import Navbar from './Navbar';
import PaginaNonTrovata from './PaginaNonTrovata';
import Home from './Home';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'; //servono per andare in diverse pagine

function App() {
  
  return (
    <Router>
      <div className="App">
        

        <Switch>
          <Route exact path='/'>
            <Navbar />
            <Login />
          </Route>

          <Route exact path='/signup'>
            <Navbar />
            <Signup />
          </Route>

          <Route exact path='/home'>
            <Home />
          </Route>

          <Route path='*'>  {/* una qualsiasi altra pagina*/}
              <PaginaNonTrovata />
          </Route>

          

        </Switch>
      
      </div>
    </Router>
      
  );
}

export default App;

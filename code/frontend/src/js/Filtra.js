import { useRef } from 'react';
import '../css/Form.css';
import '../css/Filtra.css';

const Filtra = () => {

    const testoInput = useRef();
    const statoInput = useRef();
    const dataInput = useRef();
    const benevoliInput = useRef();

    return ( 
        <div className="filtra">
            <h2>Filtra le comunicazioni</h2>
            <form>
                <input className='testo' type='text' id='testo' name='testo' placeholder='Testo' ref={testoInput}  /> <br /><br />
                <input className='testo' type='text' id='stato' name='stato' placeholder='Stato' ref={statoInput}  /> <br /><br />
                <input type="date" id="data" name="data" ref={dataInput}/> <br /> <br />
                <select name="benevoli" id="benevoli" ref={benevoliInput}>
                    <option value="">Scegli</option>
                    <option value="buono">Avvenute</option>
                    <option value="errore">Errore</option>
                </select> <br /> <br />
                <button id='bottoneFiltra' type='submit'> Invia</button>
            </form>
        </div>
     );
}
 
export default Filtra;
<div className="filtra">

</div>
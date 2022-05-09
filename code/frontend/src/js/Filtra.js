import '../css/Form.css';
import '../css/Filtra.css';
import { useRef } from 'react';

const Filtra = () => {

    const testoInput = useRef();
    const statoInput = useRef();
    const data1Input = useRef();
    const data2Input = useRef();
    const benevoliInput = useRef();

    const handleLoginForm = async (e) => {
        e.preventDefault(); //evita di ricaricare la pagina

        const testo = testoInput.current.value;
        const stato = statoInput.current.value;
        var data1 = data1Input.current.value;
        var data2 = data2Input.current.value;
        const benevoli = benevoliInput.current.value;

        if (data1 !== "" && data2 !== "") { //controllo che la data1 sia minore della data2
            if (data1 > data2) {
                const tmp = data1;
                data1 = data2;
                data2 = tmp;
            }
        }
        else if (data1 !== "") //se una delle due è vuota, allora metto quella null uguale all'altra
            data2 = data1;
        else
            data1 = data2;
        
        const data = new Date(data1); // creo l'oggetto datae stampo i secondi passati dal 1970
        alert(data.getTime() / 1000);
    }

    return ( 
        <div className="filtra">
            <h2>Filtra le comunicazioni</h2>
            <form onSubmit = {handleLoginForm}>
                <input className='testo' type='text' id='testo' name='testo' placeholder='Testo' ref={testoInput}  /> <br /><br />
                <input className='testo' type='text' id='stato' name='stato' placeholder='Stato' ref={statoInput}  /> <br /><br />
                <input type="date" id="data1" name="data1" ref={data1Input} min="1970-01-01"/> <br /> <br />
                <input type="date" id="data2" name="data2" ref={data2Input} /> <br /> <br />
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
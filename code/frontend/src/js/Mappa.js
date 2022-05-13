import { MapContainer, TileLayer, useMap, Marker, Popup } from 'react-leaflet';
import {useEffect, useState} from 'react';
import '../css/Mappa.css';
import costruisciMarker from '../funzioni/marker';
import axios from 'axios';
axios.defaults.withCredentials = true;

const Mappa = (props) => {

    const [marker, setMarker] = useState([]);

    useEffect(async () => { //una volta caricata la pagina
        //QUERY PER LEGGERE I LOG
        const urlParams = new URLSearchParams(window.location.href); //oggetto che legge i parametri dell'url

        //ottengo i parametri
        var testo = urlParams.get('testo');
        var stato = urlParams.get('stato');
        var from = urlParams.get('from');
        var to = urlParams.get('to');
        var scegli = urlParams.get('scegli');


        //controllo che i campi siano diversi da null
        if (testo === null) testo = "";
        if (stato === null) stato = "";
        if (from === null) from = Math.round(new Date().getTime() / 1000) - 90000;
        if (to === null) to = Math.round(new Date().getTime() / 1000) + 90000;
        if (scegli === null) scegli = "";

        const url2 = "http://localhost:9000/query";
        const corpo = {testo: testo, stato: stato, from: from, to: to, scegli: scegli, withCredentials: true};

        const risposta2 = await axios.post(url2, corpo);


        setMarker(costruisciMarker(risposta2.data));

    }, []);
    

    return (

        <div className="mappa">
            
            <MapContainer center={[11.505, -0.09]} zoom={1} scrollWheelZoom={false} className="map" id="negro">
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                
                {marker.map(singolo => (
                    <Marker position={singolo.posizione} >
                        <Popup>
                            {singolo.paese}
                        </Popup>
                    </Marker>
                ))}
            </MapContainer>
        </div>
    );
}

export default Mappa;
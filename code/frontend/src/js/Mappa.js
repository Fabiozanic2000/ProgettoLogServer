import { MapContainer, TileLayer, useMap, Marker, Popup } from 'react-leaflet';
import {useEffect, useState} from 'react';
import '../css/Mappa.css';
import parse from 'html-react-parser';
//import oggetto from '../funzioni/capitali';
import costruisciMarker from '../funzioni/marker';
const Mappa = (props) => {

    /*
    const [marker, setMarker] = useState('');
    useEffect(async () => { //una volta caricata la pagina
        //alert(costruisciMarker("shish"));
        //alert(props.dati);
        const oggetto = JSON.parse(props.dati);
        setMarker(costruisciMarker(oggetto)); //costruisco i marker
    }, []);
    */

    return (

        <div className="mappa">
            <MapContainer center={[11.505, -0.09]} zoom={1} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                {parse(props.marker)}
                {/*
                <Marker position={[51.505, -0.09] >
                    <Popup>
                        Inghilterra
                    </Popup>
                </Marker>

                <Marker position={[55.755826, 37.6173] >
                    <Popup>
                        Russia
                    </Popup>
                </Marker>
                */}
                

            </MapContainer>
        </div>
    );
}

export default Mappa;
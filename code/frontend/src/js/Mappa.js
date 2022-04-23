
import { MapContainer, TileLayer, useMap, Marker, Popup } from 'react-leaflet';
import '../css/Mappa.css';

//liberire per fare l'icona diversa
import * as L from "leaflet";
import { useState } from "react";

const Mappa = () => {

    // questo codice serve per dare un'icona diversa al marker
    const LeafIcon = L.Icon.extend({
        options: {}
      });
    
      const blueIcon = new LeafIcon({
          iconUrl:
            "https://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|abcdef&chf=a,s,ee00FFFF"
        }),
        greenIcon = new LeafIcon({
          iconUrl:
            "https://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|2ecc71&chf=a,s,ee00FFFF"
        });
    
      const [icona, setIcon] = useState(blueIcon);





    return (

        <div className="mappa">
            <MapContainer center={[11.505, -0.09]} zoom={1} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                <Marker position={[51.505, -0.09] /* puntatore inghilterra */}>
                    <Popup class="buono">
                        Inghilterra
                    </Popup>
                </Marker>

                <Marker position={[60.505, 55.09] /* puntatore russia */} icon={icona}>
                    <Popup>
                        Russia
                    </Popup>
                </Marker>

            </MapContainer>
        </div>
    );
}

export default Mappa;
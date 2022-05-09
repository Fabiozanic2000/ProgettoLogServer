import { MapContainer, TileLayer, useMap, Marker, Popup } from 'react-leaflet';
import '../css/Mappa.css';

const Mappa = () => {

    return (

        <div className="mappa">
            <MapContainer center={[11.505, -0.09]} zoom={1} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright%22%3EOpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                <Marker position={[51.505, -0.09] /* puntatore inghilterra /}>
                    <Popup class="buono">
                        Inghilterra
                    </Popup>
                </Marker>

                <Marker position={[60.505, 55.09] / puntatore russia */}>
                    <Popup>
                        Russia
                    </Popup>
                </Marker>

            </MapContainer>
        </div>
    );
}

export default Mappa;

import { MapContainer, TileLayer, useMap } from 'react-leaflet';
import '../css/Mappa.css';

const Mappa = () => {
    //alert(a);

    return ( 
        
        <div className="mappa">
            <MapContainer center={[51.505, -0.09]} zoom={13} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                
            </MapContainer>
        </div>
     );
}

export default Mappa;
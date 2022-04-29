import "../css/Log.css"

const Log = () => {
    return ( 
        <div className="log">
            <p className="errore" id="primo"> 10/04/2022 10:53, Russia</p>
            <p className="buono"> 12/04/2022 15:13, Inghilterra</p>
            <p className="buono"> 13/04/2022 05:34, Francia</p>
            <p className="errore"> 14/04/2022 12:49, Stati Uniti</p>
            <p className="buono"> 15/04/2022 08:06, Cina</p>
            <p className="errore"> 15/04/2022 11:37, Cina</p>
            <p className="buono"> 15/04/2022 22:51, Cina</p>
            <p className="buono"> 17/04/2022 17:45, Italia</p>
            <p className="errore"> 22/04/2022 12:18, Portogallo</p>
            <p className="errore"> 25/04/2022 20:59, Canada</p>
            <p className="buono"> 29/04/2022 19:53, Italia</p>
            <p className="buono"> 01/05/2022 23:42, Italia</p>
            <p className="buono"> 17/05/2022 17:45, Italia</p>
            <p className="errore"> 22/05/2022 12:18, Portogallo</p>
            <p className="errore"> 25/05/2022 20:59, Canada</p>
            <p className="buono"> 29/05/2022 19:53, Italia</p>
            <p className="buono"> 01/06/2022 23:42, Italia</p>
            <p className="errore" id="ultimo"> 02/06/2022 00:01, Italia</p>
        </div>
    );
}
 
export default Log;
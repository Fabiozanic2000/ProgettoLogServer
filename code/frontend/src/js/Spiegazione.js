const Spiegazione = () => {
    return ( 
        <div className="log">
            <p class="errore">
                Questo pacchetto viene dalla Russia. In questo riquadro ci andranno
                tutte le informazioni prese dal file di log relativo al pacchetto cliccato.
                Quando se ne clicca un altro, il riquadro viene automaticamente cambiato e 
                mostra le informzioni del nuovo pacchetto appena selezionato. 
                Se il colore è rosso vuol dire che la comunicazione non ha avuto successo,
                altrimenti se è verde vuol dire che è riuscita.<br />
                Il riquadro è scrollabile perchè potrebbe essere molto lungo e di conseguenza
                è meglio che sia cosi. Poi eventualmente verra modificato in seguito all'occorrenza
                in base alle esigenze e alla struttura dei log.<br />
                Inoltre questo è un testo place holder per far capire come risulterà il riquadro alla fine delle operazioni.
            </p>
        </div>
    );
}
 
export default Spiegazione;
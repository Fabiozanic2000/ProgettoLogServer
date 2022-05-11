import "../css/Log.css";
const parse = require('html-react-parser');

const Log = (props) => {
    return ( 
        <div className="log">
            {parse(props.tabella)}
        </div>
    );
}
 
export default Log;
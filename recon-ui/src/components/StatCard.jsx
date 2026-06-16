import {
    useContext
} from "react";

import {
    BreakContext
} from "../context/BreakContext";

function BreakStatCard() {

    // const { state } =
    //     useContext(BreakContext);
const { state, dispatch } =    useContext(BreakContext);
console.log(     "Context Count:",    state.openBreakCount
);
    return (
        <h3>
            Open Breaks :
            {state.openBreakCount}
        </h3>
    );
}

export default BreakStatCard;
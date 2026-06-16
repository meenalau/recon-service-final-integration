import useBreaks from "../hooks/useBreaks";
import BreakTable from "../components/BreakTable";
import { useState } from "react";
import ResolveModal from "../components/ResolveModal";
import { useContext, useEffect } from "react";
import { BreakContext } from "../context/BreakContext";

function BreaksPage() {

    const [selectedBreak, setSelectedBreak] = useState(null);
    //const { dispatch } =     useContext(BreakContext);
        const { state, dispatch } =        useContext(BreakContext);

    console.log("Context State:", state);

    // Show only OPEN breaks
    const breaks = useBreaks("OPEN");
    console.log("BreaksPage:", breaks);
useEffect(() => {

    const openCount =
        breaks.filter(
            b => b.status !== "RESOLVED"
        ).length;

    console.log(
        "Open Count Calculated:",
        openCount
    );

    dispatch({
        type: "SET_COUNT",
        payload: openCount
    });

}, [breaks]);
    return (
        <div>
            <h2>Breaks Page</h2>
{/* 
            <p>Total Breaks: {breaks.length}</p> */}
            <p>
    Open Breaks:
    {state.openBreakCount}
</p>

            <BreakTable
                breaks={breaks}
                onResolve={setSelectedBreak}
            />

            {/* before resolve functioning 

            {selectedBreak && (
                <div>
                    Selected Break:
                    {selectedBreak.breakId}
                </div>
            )} */}

            {selectedBreak && (
                <ResolveModal
                    breakData={selectedBreak}
                    onClose={() =>
                        setSelectedBreak(null)
                    }
                />
            )}
        </div>
    );
}

export default BreaksPage;
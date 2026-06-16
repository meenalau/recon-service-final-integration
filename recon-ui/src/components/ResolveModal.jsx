import { useContext } from "react";
import { resolveBreak } from "../services/breakService";
import { BreakContext } from "../context/BreakContext";

function ResolveModal({
    breakData,
    onClose
}) {

    const { dispatch } =
        useContext(BreakContext);

    const handleResolve = async () => {

        try {

            await resolveBreak(
                breakData.breakId
            );

            dispatch({
                type: "DECREMENT_COUNT"
            });

            alert(
                `Break ${breakData.breakId} resolved successfully`
            );

            onClose();

        } catch (error) {

            console.error(
                "Resolve Error:",
                error
            );

            alert(
                "Failed to resolve break"
            );
        }
    };

    return (
        <div
            style={{
                position: "fixed",
                top: "20%",
                left: "35%",
                background: "white",
                border: "1px solid black",
                padding: "20px",
                zIndex: 1000,
                minWidth: "400px",
                boxShadow:
                    "0 4px 8px rgba(0,0,0,0.3)"
            }}
        >
            <h3>Resolve Break</h3>

            <hr />

            <p>
                <strong>Break ID:</strong>{" "}
                {breakData.breakId}
            </p>

            <p>
                <strong>Trade ID:</strong>{" "}
                {breakData.tradeId}
            </p>

            <p>
                <strong>Break Type:</strong>{" "}
                {breakData.breakType}
            </p>

            <p>
                <strong>Status:</strong>{" "}
                {breakData.status}
            </p>

            <p>
                <strong>Currency:</strong>{" "}
                {breakData.currency}
            </p>

            <textarea
                rows="4"
                cols="40"
                placeholder="Enter resolution note"
            />

            <br />
            <br />

            <button
                onClick={onClose}
            >
                Cancel
            </button>

            {" "}

            <button
                onClick={handleResolve}
            >
                Confirm Resolve
            </button>

        </div>
    );
}

export default ResolveModal;
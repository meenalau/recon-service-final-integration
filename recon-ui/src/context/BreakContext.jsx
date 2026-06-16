import { createContext, useReducer } from "react";

export const BreakContext =
    createContext();

const initialState = {
    openBreakCount: 0
};

function breakReducer(
    state,
    action
) {

    switch (action.type) {

        case "SET_COUNT":
            return {
                ...state,
                openBreakCount:
                    action.payload
            };

        case "DECREMENT_COUNT":
            return {
                ...state,
                openBreakCount:
                    state.openBreakCount - 1
            };

        default:
            return state;
    }
}

export function BreakProvider({
    children
}) {

    const [state, dispatch] =
        useReducer(
            breakReducer,
            initialState
        );

    return (
        <BreakContext.Provider
            value={{
                state,
                dispatch
            }}
        >
            {children}
        </BreakContext.Provider>
    );
}
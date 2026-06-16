import { useState, useEffect } from "react";

function useBreaks(status) {

    const [breaks, setBreaks] = useState([]);
    

    useEffect(() => {

        fetch("http://localhost:8080/api/v1/recon/breaks", {
            headers: {
                Authorization: "Basic " + btoa("ops:ops123")
            }
        })
            .then(response => response.json())
            .then(data => {

                console.log("All Breaks:", data);

                setBreaks(data);

            })
            .catch(error =>
                console.error("Error:", error)
            );

    }, [status]);

    return breaks;
}

export default useBreaks;
import React from "react";
import ResultBox from "./ResultBox";

const ResultsContainer = ({AllFlats}) =>{

const rcColumns = {
    display: "grid",

    //flexDirection: "row wrap",
    
    gridTemplateColumns: "repeat(5, 1fr)",

    

    //gridTemplateRows: `repeat(${AllFlats.length / 3}, 1fr)`

}

function genResultBox(results){
    
    return(
    <ResultBox
    flatDetails={results}
    />
    )
}
    return(
        <div style={rcColumns}
        class="ResultsContainer">
        {AllFlats.map(genResultBox)}  
        </div>
    );

}

export default ResultsContainer
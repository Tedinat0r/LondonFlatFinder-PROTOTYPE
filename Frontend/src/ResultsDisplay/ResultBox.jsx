import React, { memo } from "react";

const ResultBox = memo(function ResultBox({flatDetails, column}){
    
    const gridColumn = {
        
    }  
    
    function genDivs(string){
        
        return(
            
            <div>
                {string}
            </div>

        )
    }
    
    return(
    <div>Results...</div>,
    <div 
    class="FlatResultsBox">
    {flatDetails.map(genDivs)}
    </div>    
)
})
export default ResultBox;
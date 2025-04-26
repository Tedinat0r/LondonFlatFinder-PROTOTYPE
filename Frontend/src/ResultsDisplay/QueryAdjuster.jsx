import React, { useEffect } from "react";

const QueryAdjuster = ({initalQueryRef, currentState, getCallback}) => {

    var pcm = initalQueryRef[1];

    useEffect(() => {
        if(initalQueryRef != currentState){
            getCallback;
        }
    })

return(
<form>
    <input type="text" value={initalQueryRef[0]} name="qA"/>
    <input type="range" name="qA" value={Number(pcm)} min={Number(initalQueryRef[1]) / 3} max={Number(initalQueryRef[1]) * 3}></input>
    <input type="number" step="1" name="qA"></input>
    <input type="radio" name="qA"></input>
</form>)


}
export default QueryAdjuster
import React, { useCallback } from "react";

function FormBox ({pcSetter, pcmSetter, rnSetter, furnishedCallback}){

    return(
        <form>
            <label for="PostCode">Post Code</label>
            <br></br>
            <input type="text" id="PostCode" name="PostCode" 
            onChange={(e) => pcSetter(e.target.value)}
            />
            <br></br>
            <label for="PCM">Monthly Rent</label>
            <br></br>
            <input type="text" id="PCM" name="PCM"
            onChange={(e) => pcmSetter(e.target.value)}
            />
            <br></br>
            <label for="Rooms">Room Number</label><br></br>
            <input type="text" id="Rooms" name="Rooms"
            onChange={(e) => rnSetter(e.target.value) }
            />
            <button type ="button" input="radio"
            onClick={furnishedCallback}/>
        </form>
    )

}
export default FormBox 
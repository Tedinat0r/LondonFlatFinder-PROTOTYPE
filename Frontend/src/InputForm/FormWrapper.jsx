import React from "react";
import FormBox from "./FormBox";

function FormWrapper({submitCriteria, pcmSetter, pcSetter, rnSetter, furnishedCallback}){
    return(
        <div id="FlatDetailsForm">
            <FormBox
            pcmSetter={pcmSetter}
            pcSetter={pcSetter}
            rnSetter={rnSetter}
            furnishedCallback={furnishedCallback}
            />
            <button type="button" onClick={submitCriteria}
            />
        </div>


    )


} export default FormWrapper
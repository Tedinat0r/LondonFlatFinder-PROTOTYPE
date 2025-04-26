import {useState } from 'react'
import { useMemo } from 'react';
import { useRef } from 'react';
import FormWrapper from './InputForm/FormWrapper'
import ResultBox from './ResultsDisplay/ResultBox';
import QueryAdjuster from './ResultsDisplay/QueryAdjuster'
import './App.css'
import ResultsContainer from './ResultsDisplay/ResultsContainer';

function App() {
  const [getPC, setPC] = useState("N16");
  const [getPCM, setPCM] = useState("1100");
  const [getRN, setRN] = useState("1");
  const [getFurnished, setFurnished] = useState("Furnished");
  const [getPage, setPage] = useState("home");
  const [getResults, setResults] = useState([[]]);
  let QueryDetails = useRef([]);
  let ResultsCache = useRef([[]]);
 
  const formDetails = ["Test, test, test"]
  
  
  function furnishedCallback(){
    if (setFurnished != "Furnished"){
      setFurnished("Furnished")
    }else{
      setFurnished("Unfurnished")
    }
  }


  function changePage(){
    switch(getPage){
      case "home":
        return(
          <div>
        <FormWrapper
        pcSetter={setPC}
        pcmSetter={setPCM}
        rnSetter={setRN}
        furnishedCallback={furnishedCallback}
        submitCriteria={submitForm}
        />
        </div>
        )
      case "results":  
        return(
          <ResultsContainer
          AllFlats={getResults}
          
          />
        )
      case "loading":
        return(
          <div>Loading...</div>
        )
      }
  }
  
  async function formTermsPOST(terms){
    try{
      await fetch(
        'http://localhost:8080/api/search',
        {method: 'POST',
        headers:{'Accept': 'application/json',
                    "Content-type": "application/json"},
        body: JSON.stringify(
          terms
        ) 
        }).then(setPage("loading"))
        
    }catch(error){
      console.log(error)
    }
  } 

  /*function nodifyFlats(array){
    return (
    
    array.map(element => 
    ({id: element[0], index: array.indexOf(element),
    prev_id: array.indexOf(element) != 0 ? array[array.indexOf(element) - 1][0] : null, next_id:
    array.indexOf(element) != array.length - 1 ? array[array.indexOf(element) + 1][0] : null   
      })
    )
  )}*/


  async function resultsGET(){
    try{
      await fetch('http://localhost:8080/api/fetchresults',
        {method: 'GET',
        headers:{'Accept': 'application/json',
          "Content-type": "application/json"}
        }
      )
      .then(response => response.json())
      .then(json => setResults(json))
      .then(setPage("results"))
      
      console.log(ResultsCache.current)
    }
    catch(error){
      console.error(error);
    }
  }
  function delayGet(){setTimeout(resultsGET, 10000)};

  function submitForm(){
    var terms = [getPC, getPCM, getRN, getFurnished];
    var termsCheck = terms.filter((term) => term != "" ? true : false);
    console.log(terms)
    console.log(termsCheck + "this is the check")
    
    if (terms.length != termsCheck.length){
        alert('Please check you have filled in all the fields');

    }else{
      QueryDetails.current = terms;
      formTermsPOST(terms);
      console.log(QueryDetails.current + "Checking useRef")
    }
    
    QueryDetails.current = terms;
    setPage("loading");
    delayGet();
  } 


  
  return (
    <div>
        {changePage()}
        </div>
  )
}

export default App

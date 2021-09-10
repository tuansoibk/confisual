import { Grid } from "@material-ui/core";
import './Visualiser.css'
import React, { useState } from "react";
import { VisualisationAPI } from "../api/VisualisationAPI";
import Fab from '@material-ui/core/Fab';
import DiagramTabs from './DiagramTabs';

export default function Visualiser() {
  const [diagrams, setDiagrams] = useState([]);
  const [error, setError] = useState(undefined);
  const [input, setInput] = useState('');
  
  function visualise() {
    clear();
    VisualisationAPI.visualise(input)
      .then(json => {
        if (json.diagrams) {
          setDiagrams(Object.entries(json.diagrams));
        }
        else if (json.errors && json.errors.length > 0) {
          setError(json.errors.join('\n'));
        }
        else {
          setError('Visualisation failed: response is empty');
        }
      })
      .catch(err => {
        setError(err.message);
      });
  }
  
  function clear() {
    setDiagrams([]);
    setError(undefined);
  }
  
  return (
    <Grid container direction="column" spacing={2}>
      <Grid container item direction="column" alignContent="center">
        <label>XML Configuration:</label>
        <textarea
          id="input"
          onChange={(e) => setInput(e.target.value)}
        >
        </textarea>
      </Grid>
      
      <Grid item>
        <Fab
          color="primary"
          aria-label="visualise"
          variant="extended"
          onClick={() => visualise()}
        >
          Visualise
        </Fab>
        <Fab
          color="secondary"
          aria-label="clear"
          variant="extended"
          onClick={() => clear()}
        >
          Clear
        </Fab>
      </Grid>
      
      <Grid
        container item
        direction="column"
        alignContent="center">
        {error !== undefined && <div>Error: {error}</div>}
        {diagrams.length > 0 &&
          <DiagramTabs diagrams={diagrams}/>
        }
      </Grid>
    </Grid>
  );
}

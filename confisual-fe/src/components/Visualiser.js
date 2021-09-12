import { Grid } from "@material-ui/core";
import './Visualiser.css'
import React, { useState } from "react";
import { VisualisationAPI } from "../api/VisualisationAPI";
import Fab from '@material-ui/core/Fab';
import DiagramTabs from './DiagramTabs';
import PropTypes from 'prop-types';

export default function Visualiser(props) {
  const { domain } = props;
  const [diagrams, setDiagrams] = useState(() => {
    const diagramsValue = localStorage.getItem(`${domain}.diagrams`);
    
    return diagramsValue !== null
      ? Object.entries(JSON.parse(diagramsValue))
      : [];
  });
  const [error, setError] = useState(undefined);
  const [input, setInput] = useState(localStorage.getItem(`${domain}.input`) ?? '');
  
  function visualise() {
    clear();
    VisualisationAPI.visualise(domain, input)
      .then(json => {
        if (json.diagrams) {
          setDiagrams(Object.entries(json.diagrams));
          localStorage.setItem(`${domain}.diagrams`, JSON.stringify(json.diagrams));
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
    localStorage.removeItem(`${domain}.diagrams`);
  }
  
  function updateInput(value) {
    setInput(value);
    localStorage.setItem(`${domain}.input`, value);
  }
  
  return (
    <Grid container direction="column" spacing={2}>
      <Grid container item direction="column" alignContent="center">
        <label>XML Configuration:</label>
        <textarea
          id="input"
          onChange={(e) => updateInput(e.target.value)}
          value={input}
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

Visualiser.propTypes = {
  domain: PropTypes.string.isRequired,
};

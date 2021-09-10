import { Grid } from "@material-ui/core";
import './Visualiser.css'
import { useState } from "react";
import { VisualisationAPI } from "../api/VisualisationAPI";
import Fab from '@material-ui/core/Fab';

export default function Visualiser() {
  const [diagrams, setDiagrams] = useState([]);
  const [error, setError] = useState(undefined);
  const [input, setInput] = useState('');
  
  function visualise() {
    clear();
    VisualisationAPI.visualise(input)
      .then(json => {
        if (json.diagrams && json.diagrams.length > 0) {
          setDiagrams(json.diagrams);
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
      <Grid item>
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
      
      <Grid item>
        {error !== undefined && <div>Error: {error}</div>}
        {diagrams.length > 0 &&
        <div data-testid="diagrams">
          {
            diagrams.map((diagram) => (
              <img alt="visualisation diagram" src={"data:image/png;base64," + diagram}/>
            ))
          }
        </div>
        }
      </Grid>
    </Grid>
  );
}

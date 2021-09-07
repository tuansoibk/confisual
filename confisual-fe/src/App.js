import React from 'react';
import './App.css';
import { Visualization } from "./features/visualization/Visualization";
import { Grid } from "@material-ui/core";

function App() {
  return (
    <div className="App">
      <Grid
          container
          justify = "center"
      >
        <Visualization/>
      </Grid>
    </div>
  );
}

export default App;

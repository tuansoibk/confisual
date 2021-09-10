import './App.css';
import Visualiser from "./components/Visualiser";
import { Grid } from "@material-ui/core";
import React from 'react';

function App() {
  return (
    <div className="App">
      <Grid
        container
        justifyContent="center"
      >
        <Visualiser/>
      </Grid>
    </div>
  );
}

export default App;

import './App.css';
import Visualiser from "./components/Visualiser";
import { Grid, Typography } from "@material-ui/core";
import React from 'react';

function App() {
  return (
    <div className="App">
      <Grid
        container
        justifyContent="center"
        direction="column"
      >
        <Typography
          variant="h4"
        >
          Confisual Web App
        </Typography>
        <Visualiser/>
      </Grid>
    </div>
  );
}

export default App;

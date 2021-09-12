import './App.css';
import Visualiser from "./components/Visualiser";
import { Grid, Typography } from "@material-ui/core";
import React, { useState } from 'react';
import Drawer from '@material-ui/core/Drawer';
import Toolbar from '@material-ui/core/Toolbar';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { Domains } from './Constants';

function App() {
  const [currentDomain, setCurrentDomain] = useState(Domains.nevisAuth);
  
  function selectDomain(domain) {
    setCurrentDomain(domain);
  }
  
  return (
    <div className="App">
      <Grid container>
        <Grid
          item>
          <Drawer
            sx={{
              width: 240,
              flexShrink: 0,
              '& .MuiDrawer-paper': {
                width: 240,
                boxSizing: 'border-box',
              },
            }}
            variant="permanent"
            anchor="left"
          >
            <Toolbar />
            <Divider />
            <List>
              {Object.entries(Domains).map(([domain]) => (
                <ListItem
                  button
                  key={domain}
                  onClick={() => selectDomain(domain)}
                  color="primary"
                  style={{ backgroundColor: domain === currentDomain ? "pink" : "white" }}
                >
                  <ListItemText primary={domain} />
                </ListItem>
              ))}
            </List>
          </Drawer>
        </Grid>
        <Grid
          item
          container
          justifyContent="center"
          direction="column"
        >
          <Typography
            variant="h4"
          >
            Confisual Web App
          </Typography>
          {currentDomain === Domains.nevisAuth && <Visualiser domain={currentDomain} />}
          {currentDomain === Domains.nevisProxy && <Visualiser domain={currentDomain} />}
        </Grid>
      </Grid>
    </div>
  );
}

export default App;

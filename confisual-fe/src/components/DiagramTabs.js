import Tabs from '@material-ui/core/Tabs';
import React from 'react';
import Tab from '@material-ui/core/Tab';
import Box from '@material-ui/core/Box';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Typography from '@material-ui/core/Typography';
import PropTypes from 'prop-types';
import { TransformComponent, TransformWrapper } from "react-zoom-pan-pinch";
import { Grid } from "@material-ui/core";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function DiagramTabs(props) {
  const { diagrams } = props;
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const handleSearchChange = (event, newValue) => {
    const domain = diagrams.findIndex(diagram => diagram[0] === newValue);
    setValue(domain);
  };

  return (
    <Box sx={{ width: '80%' }}>
      <Grid container alignContent="center" spacing={3}>
        <Grid item xs={8}>
          <Tabs
              classes={{root: { justifyContent: "center" }, scroller: { flexGrow: "0" }}}
              value={value}
              onChange={handleChange}
              aria-label="domain diagram tabs"
              variant={"scrollable"}
          >
          {
              diagrams.map(([domain, diagram], idx) => (
                  <Tab
                      key={idx}
                      label={`Domain ${domain}`}
                      {...a11yProps(idx)}
                  />
              ))
          }
          </Tabs>
        </Grid>
        <Grid item xs={4}>
          <Autocomplete
              id="combo-box-search-domain"
              options={diagrams.map(([domain, _]) => domain)}
              renderInput={(params) => <TextField {...params} label="Domain" />}
              onChange={handleSearchChange}
          />
        </Grid>
        <Grid item xs={12}>
          {
            diagrams.map(([_, diagram], idx) => (
              <TabPanel
                value={value}
                index={idx}
              >
                <Box sx={{ border: '1px solid grey' }}>
                  <TransformWrapper
                    centerOnInit
                    minScale={0.2}>
                    <TransformComponent wrapperStyle={{margin: "auto", maxWidth: "100%"}}>
                        <img
                          alt="domain diagram"
                          src={"data:image/png;base64," + diagram}
                        />
                    </TransformComponent>
                  </TransformWrapper>
                </Box>
              </TabPanel>
            ))
          }
        </Grid>
      </Grid>
    </Box>
  );
}

DiagramTabs.propTypes = {
  diagrams: PropTypes.arrayOf(PropTypes.object).isRequired
}

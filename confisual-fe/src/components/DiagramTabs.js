import Tabs from '@material-ui/core/Tabs';
import React from 'react';
import Tab from '@material-ui/core/Tab';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import PropTypes from 'prop-types';

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

  return (
    <Box sx={{ width: '98%' }}>
      <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
        <Tabs
          value={value}
          aria-label="basic tabs example"
          centered
          onChange={handleChange}
        >
          {
            diagrams.map(([domain, diagram], idx) => (
              <Tab
                label={`Domain ${domain}`}
                {...a11yProps(idx)}
              />
            ))
          }
        </Tabs>
      </Box>
      {
        diagrams.map(([_, diagram], idx) => (
          <TabPanel
            value={value}
            index={idx}
          >
            <Box sx={{ border: '1px solid grey' }}>
              <img
                alt="visualisation diagram"
                src={"data:image/png;base64," + diagram}
              />
            </Box>
          </TabPanel>
        ))
      }
    </Box>
  );
}

DiagramTabs.propTypes = {
  diagrams: PropTypes.arrayOf(PropTypes.object).isRequired
}

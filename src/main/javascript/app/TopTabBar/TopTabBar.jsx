import React from 'react'
import {makeStyles, AppBar, Tabs, Tab, Typography, Box, Button} from "@material-ui/core";
import ClientDetailsPanel from "../ClientDetailsPanel";
import UserAdminPanel from "../UserAdminPanel";
import {ExitToApp} from '@material-ui/icons';

const useStyle = makeStyles( theme => ({
    logout: {
        position: 'absolute',
        right: 0,
        margin: '6px 9px'
    }
}))

const a11yProps = (index) => {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

const TabPanel = (props) => {
    const { children, value, index, ...other } = props;

    return <Typography
        component="div"
        role="tabpanel"
        hidden={value !== index}
        id={`simple-tabpanel-${index}`}
        aria-labelledby={`simple-tab-${index}`}
        {...other}
    >
        <Box p={3}>{children}</Box>
    </Typography>

}

const logout = () => {
    window.location.href = '/logout';
}

function TopToolBar () {
    const classes = useStyle();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    }

    return  <>
        <AppBar position="static">
            <Tabs
                value={value}
                onChange={handleChange}
                aria-label="nav tabs"
            >
                <Tab label="Client" {...a11yProps(0)} />
                <Tab label="User" {...a11yProps(1)} />
                <Button className={classes.logout}
                        variant="outlined"
                        onClick={logout}
                >
                    <ExitToApp/>Logout
                </Button>
            </Tabs>
        </AppBar>
        <TabPanel value={value} index={0}>
            <ClientDetailsPanel/>
        </TabPanel>
        <TabPanel value={value} index={1}>
            <UserAdminPanel/>
        </TabPanel>
    </>
}

export default TopToolBar;

import React from 'react'
import {makeStyles, Grid, Paper} from "@material-ui/core";
import ClientDetailsList from "./ClientDetailsList";
import ClientDetailsMaster from "./ClientDetailsMaster";

//custom hooks
const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
        padding: '40px'
    },
    item: {
        flexGrow: 1
    },
    paper: {
        padding: theme.spacing(1),
        top: 0,
        color: theme.palette.text.primary
    }
}))

function ClientDetailsPanel() {
    const classes = useStyles();

    return <Grid container justify="center" className={classes.root} spacing={1}>

        <Grid item className={classes.item} xs={6}>
            <Paper className={classes.paper}>
                <ClientDetailsList />
            </Paper>
        </Grid>

        <Grid item className={classes.item} xs={6}>
            <ClientDetailsMaster/>
        </Grid>

    </Grid>
}

export default ClientDetailsPanel;

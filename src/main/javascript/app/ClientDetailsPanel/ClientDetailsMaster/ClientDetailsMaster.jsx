import React, {useEffect, useState} from 'react';
import {makeStyles, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, ExpansionPanelActions, TextField, Typography} from '@material-ui/core';
import {ExpandMore, Edit} from '@material-ui/icons';
import * as _ from 'lodash';
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import {connect} from "react-redux";
import Button from "@material-ui/core/Button";
import Refetcher from "../../App.refetcher";

const useStyle = makeStyles( theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: 200
    },
    singleTextField: {
        flexWrap: 'wrap',
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: 640
    },
    icon: {
        width: '0.7em',
        height: '0.7em',
        marginRight: theme.spacing(1)
    }
}));

const ClientDetailsMaster = ({cid}) => {
    const classes = useStyle();
    const [clientDetails, updateClientDetails] = useState({});
    const [isExisting, setExisting] = useState(false);
    const [deltaChange, updateDeltaChange] = useState({});

    const fetchData = () => {
        if(!_.isNil(cid)) {
            fetch("/client-details/" + cid)
                .then( resp => resp.json() )
                .then(resp => {
                    if(resp.success){
                        updateClientDetails(resp.data);
                        setExisting(true);
                    }
                })
                .catch(err => console.error(err));
        }
    };

    useEffect(() => {
        fetchData();
    }, cid);

    const doAddNew = () => {
        console.debug("save updated record");
        setExisting(false);
        updateClientDetails(null);
        updateDeltaChange({});
    };

    const doSave = () => {
        if(isExisting) { //update record
            console.debug("save updated record", deltaChange);
            fetch("/client-details/" + cid, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(deltaChange)
            }).then(resp => resp.json())
                .then(resp => console.log(resp.msg))
                .catch(err => console.log(err));
        } else {  //add new record
            console.debug("save new record");
            fetch("/client-details", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(clientDetails)
            }).then(resp => resp.json())
                .then(resp => { if(resp.success) Refetcher.runAll(); } )
                .catch(err => console.log(err));
        }

    };

    const doDelete = () => {
        console.debug("delete existing record");
        fetch("/client-details/" + cid, {
            method: 'DELETE'
        })
            .then( resp => resp.json() )
            .then(resp => {
                if(resp.success) {
                    setExisting(false);
                    updateClientDetails(null);
                    Refetcher.runAll();
                }
            })
            .catch(err => console.error(err));
    };

    const handleChange = (target, value) => {
        console.debug("update ", target, " = ", value);
        updateClientDetails({
            ...clientDetails,
            [target]: value
        });
        updateDeltaChange({
            ...deltaChange,
            [target]: value
        })
    };

    return (<ExpansionPanel defaultExpanded={true}>
        <ExpansionPanelSummary
            expandIcon={<ExpandMore />}
            aria-controls="panel1a-content"
            id="panel1a-header"
        >
            <Typography><Edit className={classes.icon}/>add/edit client details</Typography>
        </ExpansionPanelSummary>
        <ExpansionPanelDetails className={classes.container}>
            <TextField
                required
                label="Client ID"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.clientId) ? clientDetails.clientId : ''}
                onChange={event => handleChange('clientId', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                required
                label="Client Secret"
                type="password"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.clientSecret) ? clientDetails.clientSecret : ''}
                onChange={event => handleChange('clientSecret', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                label="Resource IDs"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.resourceIds) ? clientDetails.resourceIds : ''}
                onChange={event => handleChange('resourceIds', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                label="Scope"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.scope) ? clientDetails.scope : ''}
                onChange={event => handleChange('scope', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                required
                label="Authorized Grant Types"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.authorizedGrantTypes) ? clientDetails.authorizedGrantTypes : ''}
                onChange={event => handleChange('authorizedGrantTypes', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                label="Authorities"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.authorities) ? clientDetails.authorities : ''}
                onChange={event => handleChange('authorities', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                label="Access Token Validity"
                type="number"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.accessTokenValidity) ? clientDetails.accessTokenValidity : ''}
                onChange={event => handleChange('accessTokenValidity', parseInt(event.currentTarget.value))}
                margin="normal"
            />
            <TextField
                label="Refresh Token Validity"
                type="number"
                className={classes.textField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.refreshTokenValidity) ? clientDetails.refreshTokenValidity : ''}
                onChange={event => handleChange('refreshTokenValidity', parseInt(event.currentTarget.value))}
                margin="normal"
            />
            <FormControlLabel
                control={
                    <Checkbox
                        checked={!_.isNil(clientDetails) && !_.isNil(clientDetails.autoApprove) ? clientDetails.autoApprove : false}
                        onChange={event => handleChange('autoApprove', event.currentTarget.checked)}
                        value="autoApprove"
                    />
                }
                label="Autoapprove"
            />
            <TextField
                label="Web Server Redirect Uri"
                className={classes.singleTextField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.webServerRedirectUri) ? clientDetails.webServerRedirectUri : ''}
                onChange={event => handleChange('webServerRedirectUri', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                label="Additional Information"
                className={classes.singleTextField}
                value={!_.isNil(clientDetails) && !_.isNil(clientDetails.additionalInformation) ? clientDetails.additionalInformation : ''}
                onChange={event => handleChange('additionalInformation', event.currentTarget.value)}
                margin="normal"
            />
        </ExpansionPanelDetails>
        <ExpansionPanelActions>
            <Button disabled={!isExisting}
                    size="small"
                    onClick={() => doAddNew()}
            >
                Add New</Button>
            <Button disabled={!isExisting}
                    size="small"
                    onClick={() => doDelete()}
            >
                Delete</Button>
            <Button size="small"
                    onClick={() => doSave()}
            >
                Save</Button>
        </ExpansionPanelActions>
    </ExpansionPanel>)
};

const mapStateToProps = state => {
    return {
        cid: state.clientDetails.clientDetailsId
    }
};

export default connect(
    mapStateToProps,
    null
)(ClientDetailsMaster);

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

const UserMaster = ({username}) => {
    const classes = useStyle();
    const [user, updateUser] = useState({});
    const [isExisting, setExisting] = useState(false);
    const [deltaChange, updateDeltaChange] = useState({});

    const fetchData = () => {
        if(!_.isNil(username)) {
            fetch("/users/" + username)
                .then( resp => resp.json() )
                .then(resp => {
                    if(resp.success){
                        updateUser(resp.data);
                        setExisting(true);
                    }
                })
                .catch(err => console.error(err));
        }
    };

    useEffect(() => {
        fetchData();
    }, username);

    const doAddNew = () => {
        console.debug("save updated record");
        setExisting(false);
        updateUser(null);
        updateDeltaChange({});
    };

    const doSave = () => {
        if(isExisting) { //update record
            console.debug("save updated record", deltaChange);
            fetch("/users/" + username, {
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
            fetch("/users", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            }).then(resp => resp.json())
                .then(resp => { if(resp.success) Refetcher.runAll(); } )
                .catch(err => console.log(err));
        }
    };

    const doDelete = () => {
        console.debug("delete existing record");
        fetch("/users/" + cid, {
            method: 'DELETE'
        })
            .then( resp => resp.json() )
            .then(resp => {
                if(resp.success) {
                    setExisting(false);
                    updateUser(null);
                    Refetcher.runAll();
                }
            })
            .catch(err => console.error(err));
    };

    const handleChange = (target, value) => {
        console.debug("update ", target, " = ", value);
        updateUser({
            ...user,
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
            <Typography><Edit className={classes.icon}/>add/edit user</Typography>
        </ExpansionPanelSummary>
        <ExpansionPanelDetails className={classes.container}>
            <TextField
                required
                label="Username"
                className={classes.textField}
                value={!_.isNil(user) && !_.isNil(user.username) ? user.username : ''}
                onChange={event => handleChange('username', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                required
                label="Password"
                type="password"
                className={classes.textField}
                value={!_.isNil(user) && !_.isNil(user.password) ? user.password : ''}
                onChange={event => handleChange('password', event.currentTarget.value)}
                margin="normal"
            />
            <TextField
                label="Email"
                className={classes.singleTextField}
                value={!_.isNil(user) && !_.isNil(user.email) ? user.email : ''}
                onChange={event => handleChange('email', event.currentTarget.value)}
                margin="normal"
            />
            <FormControlLabel
                control={
                    <Checkbox
                        checked={!_.isNil(user) && !_.isNil(user.active) ? user.active : false}
                        onChange={event => handleChange('active', event.currentTarget.checked)}
                        value="active"
                    />
                }
                label="Active"
            />
        </ExpansionPanelDetails>
        <ExpansionPanelActions>
            <Button size="small"
                    onClick={() => doAddNew()}
            >Add New</Button>
            <Button disabled={!isExisting}
                    size="small"
                    onClick={() => doDelete()}
            >Delete</Button>
            <Button size="small"
                    onClick={() => doSave()}
            >Save</Button>
        </ExpansionPanelActions>
    </ExpansionPanel>)
};

const mapStateToProps = state => {
    return {
        username: state.user.username
    }
};

export default connect(
    mapStateToProps,
    null
)(UserMaster);

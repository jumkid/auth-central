import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {makeStyles, List, ListItem, ListItemText} from '@material-ui/core';
import {changeUsername} from "../../App.redux-actions";
import Refetcher from "../../App.refetcher";

const style = makeStyles(theme => ({

   listItem: {
       cursor: 'pointer'
   }

}));

const ListItems = ({usernames, cid, showUser}) => {
    return usernames != null ? usernames.map(id => <ListItem key={id}
                                   button
                                   selected={cid === id}
                                   onClick={() => showUser(id)}
    >
            <ListItemText
                primary={id}
            />
        </ListItem>) : <ListItem><ListItemText primary={"NO DATA"}/></ListItem>

};

const UserList = ({username, changeUsername}) => {
    const [usernameList, setUsernameList] = useState([]);
    const fetchData = () => {
        fetch("/users/field/username", {
            method: 'GET',
            headers: {
                'Authorization': 'Basic',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            // body: JSON.stringify({var1: 1, var2: 2})
        }).then( resp => resp.json() )
            .then(resp => { if(resp.success) setUsernameList(resp.data); })
            .catch(err => console.log(err));
    };

    useEffect(() => {
        fetchData();
    }, []);

    Refetcher.add("UserList", fetchData);

    const showUser = (username) => {
        console.log("edit user", username);
        changeUsername(username);
    };

    return <List>
        <ListItems usernames={usernameList} username={username} showUser={showUser}/>
    </List>
};

const mapDispatchToProps = dispatch => {
    return {
        changeUsername: username => dispatch(changeUsername(username))
    }
};

const mapStateToProps = state => {
    return {
        username: state.user.username
    }
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(UserList);

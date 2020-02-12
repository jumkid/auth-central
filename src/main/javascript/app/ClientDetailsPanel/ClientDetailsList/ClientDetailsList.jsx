import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {makeStyles, List, ListItem, ListItemText} from '@material-ui/core';
import {changeClientDetailsId} from "../../App.redux-actions";
import Refetcher from "../../App.refetcher";

const style = makeStyles(theme => ({

   listItem: {
       cursor: 'pointer'
   }

}));

const ListItems = ({clientDetailsLst, cid, showClientDetails}) => {
    return clientDetailsLst != null ? clientDetailsLst.map(clientDetails => <ListItem key={clientDetails.id}
                                   button
                                   selected={cid === clientDetails.id}
                                   onClick={() => showClientDetails(clientDetails.id)}
    >
            <ListItemText
                primary={clientDetails.clientId}
            />
    </ListItem>) : <ListItem><ListItemText/></ListItem>
};

const ClientDetailsList = ({cid, changeClientDetailsId}) => {
    const [clientDetailsLst, setList] = useState([]);
    const fetchData = () => {
        fetch("/client-details", {
            method: 'GET',
            headers: {
                'Authorization': 'Basic',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            // body: JSON.stringify({var1: 1, var2: 2})
        }).then( resp => resp.json() )
            .then(resp => {if(resp.success) setList(resp.data);})
            .catch(err => console.log(err));
    };

    useEffect(() => {
        fetchData();
    }, []);

    Refetcher.add("ClientDetailsList", fetchData);

    const showClientDetails = (cid) => {
        console.log("edit client details", cid);
        changeClientDetailsId(cid);
    };

    return <List>
        <ListItems clientDetailsLst={clientDetailsLst} cid={cid} showClientDetails={showClientDetails}/>
    </List>
};

const mapDispatchToProps = dispatch => {
    return {
        changeClientDetailsId: cid => dispatch(changeClientDetailsId(cid))
    }
};

const mapStateToProps = state => {
    return {
        cid: state.clientDetails.clientId
    }
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ClientDetailsList);

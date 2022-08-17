var dbConn = require('../../config/db3.config');

var Userdb = function(userdb){
    this.ID = userdb.ID;
    this.GITLAB_ID = userdb.GITLAB_ID;
    this.USERNAME = userdb.USERNAME;
    this.NAME = userdb.NAME;
    this.PASSWORD = userdb.PASSWORD;
    this.GITLAB_TOKEN = userdb.GITLAB_TOKEN;
    this.ROLE = userdb.ROLE;
    this.EMAIL = userdb.EMAIL;
    this.CLASSES = userdb.CLASSES;
}

//get all users
Userdb.getalluser = (result)=>{
    dbConn.query('SELECT * FROM User', (err,res)=>{
        if(err)
            result(err);
        else
            result(null,res);
    })
}
//get user by id
Userdb.getuserbyid = (id,result)=>{
    dbConn.query('SELECT * FROM User WHERE id = ?',id, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}

//get name by id
Userdb.getnamebyid = (id,result)=>{
    dbConn.query('SELECT NAME FROM User WHERE id = ?',id, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}

//check by email
Userdb.checkbyemail = (email, result)=>{
    dbConn.query('SELECT count(*) FROM User WHERE email = ?', email, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}



//check by email
Userdb.checkbyid= (id, result)=>{
    dbConn.query('SELECT count(*) FROM User WHERE ID = ?', id, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}

//check by username
Userdb.checkbyusername = (username, result)=>{
    dbConn.query('SELECT count(*) FROM User WHERE username = ?', username, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}


//get username by id
Userdb.getusernamebyid = (id,result)=>{
    dbConn.query('SELECT USERNAME FROM User WHERE id = ?',id, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}


//get password by name
Userdb.getpwbyusername = (username,result)=>{
    dbConn.query('SELECT PASSWORD FROM User WHERE username = ?',username, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}

//get id by name
Userdb.getidbyusername = (username,result)=>{
    dbConn.query('SELECT ID FROM User WHERE username = ?',username, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}


//get gitlab id by name
Userdb.getgitlabidbyusername = (username,result)=>{
    dbConn.query('SELECT GITLAB_ID FROM User WHERE username = ?',username, (err,res)=>{
        if(err)
            result(res);
        else    
            result(null,res);
    }) 
}


//get Classes id by name
Userdb.getclassesbyusername = (username,result)=>{
    dbConn.query('SELECT CLASSES FROM User WHERE username = ?',username, (err,res)=>{
        if(err)
            result(res);
        else    
            result(null,res);
    }) 
}

//update password by username
Userdb.updatepwbyusername = (username, password, result)=>{
    dbConn.query('UPDATE User SET password=? WHERE username = ?',[password, username], (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}

//add user
Userdb.addUser = (UserReqData, result)=>{
    dbConn.query('INSERT INTO User SET ? ',UserReqData, (err, res)=>{
        if(err){
             result(err);
         }else{
             result(null,res);
         }
     })
}

//delete by id
Userdb.deleteUser = (id,result)=>{
    dbConn.query('DELETE FROM User WHERE id = ?',id, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}



//update User by username
Userdb.updateUser = (username,UserReqData, result)=>{
    dbConn.query('UPDATE User SET ID=?, USERNAME=?, NAME=?, PASSWORD=?, ROLE=?, EMAIL=?, CLASSES=? WHERE USERNAME =?',[UserReqData.ID,UserReqData.USERNAME,UserReqData.NAME,UserReqData.PASSWORD,UserReqData.ROLE,UserReqData.EMAIL,UserReqData.CLASSES,username],(err,res)=>{
        if(err)
            result(err);
        else    
            result(res);
    })

}



module.exports = Userdb;

const UserdbModel = require('../models/userdb.model');

//get all users
exports.getalluser = (req,res)=>{
    UserdbModel.getalluser((err,users)=>{
        if(err)
            res.send(err);
        else
            res.send(users);
    })
}


//get users by id
exports.getuserbyid = (req,res)=>{
    UserdbModel.getuserbyid(req.params.id, (err, user) =>{
        if(err)
            res.send(err);
        res.send(user);
    })
}

//get name by id
exports.getnamebyid = (req,res)=>{
    UserdbModel.getnamebyid(req.params.id, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}

//get username by id
exports.getusernamebyid = (req,res)=>{
    UserdbModel.getusernamebyid(req.params.id, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}


//get passwd by name
exports.getpwbyusername = (req,res)=>{
    UserdbModel.getpwbyusername(req.params.username, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}


//get classes by username
exports.getclassesbyusername = (req,res)=>{
    UserdbModel.getclassesbyusername(req.params.username, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}

//get id by username
exports.getidbyusername = (req,res)=>{
    UserdbModel.getidbyusername(req.params.username, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}

//get gitlab id by username
exports.getgitlabidbyusername = (req,res)=>{
    UserdbModel.getgitlabidbyusername(req.params.username, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}

//check by email
exports.checkbyemail = (req,res)=>{
    UserdbModel.checkbyemail(req.params.email, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}


//check by username
exports.checkbyusername = (req,res)=>{
    UserdbModel.checkbyusername(req.params.username, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}

//check by id
exports.checkbyid = (req,res)=>{
    UserdbModel.checkbyid(req.params.id, (err, pw) =>{
        if(err)
            res.send(err);
        res.send(pw);
    })
}

//delete by id
exports.deleteUser = (req,res)=>{
    UserdbModel.deleteUser(req.params.id, (err, user) =>{
        if(err)
            res.send(err);
        else
            res.json({success:true, message: 'User deleted successfully!'});;
    })
}


//add user
exports.addUser = (req,res) =>{
    const UserReqData = new UserdbModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        UserdbModel.addUser(UserReqData, (err, user)=>{
            if(err)
                res.send(err);
                res.json({status: true, message: 'User Created Successfully'});
        })
    }
}

//update User by username
exports.updateUser = (req,res) =>{
    const UserReqData = new UserdbModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        UserdbModel.updateUser(req.params.username, UserReqData, (err, user)=>{
            if(err)
            res.send(err);
            else
            res.json({status: true, message: 'User updated Successfully',data: user.insertId});
        })
    }
}

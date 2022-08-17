const express = require('express');
const router = express.Router();

const UserdbController = require('../controllers/userdb.controller');
const UserdbModel = require('../models/userdb.model');

//get all users
router.get('/user',UserdbController.getalluser);

//get user by id
router.get('/userbyid/:id',UserdbController.getuserbyid);

//get passwd by username
router.get('/pw/:username',UserdbController.getpwbyusername);

//get id by username
router.get('/id/:username',UserdbController.getidbyusername);

//get gitlab id by username
router.get('/gitlab_id/:username',UserdbController.getgitlabidbyusername);

//get username by id
router.get('/username/:id',UserdbController.getusernamebyid);

//get name by id
router.get('/name/:id',UserdbController.getnamebyid);

//check by username
router.get('/checkbyusername/:username',UserdbController.checkbyusername);

//check by email
router.get('/checkbyemail/:email',UserdbController.checkbyemail);

//check by id
router.get('/checkbyid/:id',UserdbController.checkbyid);

//add user
router.post('/add/',UserdbController.addUser);

//delete user by id
router.post('/delete/:id',UserdbController.deleteUser);

//get classes by username
router.get('/classes/:username',UserdbController.getclassesbyusername);

//update passwd by username
router.post('/update/pw/:username/:password', function(req,res) {
    UserdbModel.updatepwbyusername(req.params.username,req.params.password, (err,user)=>{
        if(err)
            res.send(err);
        else    
            res.json({success:true});
    })
})

//update all by username
router.post('/update/user/:username',UserdbController.updateUser);



module.exports = router;
const express = require('express');
const router = express.Router();

const SQreportModel = require('../models/sonarqubereport.model');
const SQreportcontroller = require('../controllers/sonarqubereport.controller');

//get SQreport by id and project_name
//router.get('/:student_id/:project_name',SQreportcontroller.getSQreportByIdandName);

router.get('/:student_id/:project_name', function(req, res) {
    SQreportModel.getSQreportByIdandName(req.params.student_id, req.params.project_name, (err, sqreport)=>{
        if(err)
        res.send(err);
        console.log('get sonarqubereport', sqreport);
        res.send(sqreport);
    })
});

router.post('/delete/:student_id/:projet_name', function(req,res) {
    console.log('delete');
    SQreportModel.deleteSQreport(req.params.student_id, req.params.project_name, (err, sqreport)=>{
        if(err)
            res.send(err);
        else
            res.json({success:true, message: 'SQreport deleted successfully!'});
    })
}); 

router.post('/add/', function(req,res) {
    console.log('ok');
    const sqreportReqData = new SQreportModel(req.body);
    console.log('sqreportReqData',sqreportReqData);
    //check null
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        SQreportModel.addSQreport(sqreportReqData, (err, sqreport)=>{
            if(err)
                res.send(err);
                res.json({status: true, message: 'SQreport added Successfully'});
        })
    }   
});

//router.post('/',SQreportcontroller.aaddNewSQreport);

module.exports = router;

const SQreportModel = require('../models/sonarqubereport.model');

//get by id and name
//exports.getSQreportByIdandName = (req, res)=>{
//    SQreportModel.getSQreportByIdandName(req.params.student_id, req.params.porject_name, (err, sqreport)=>{
 //       if(err)
//           res.send(err);
 //       console.log('get sonarqubereport', sqreport);
 //       res.send(sqreport);
//    })
//}

//create new sqreport
exports.addNewSQreport = (req,res) =>{
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
                res.json({status: true, message: 'SQreport added Successfully',data: sqreport.insertId});
        })
    }   
}


//get personal report
exports.getpersonalreport = (req,res) =>{
    SQreportModel.getpersonalreport(req.params.project_name, (err,reports)=> {
    if(err)
        res.send(err);
    else 
        res.send(reports);        
    })
}


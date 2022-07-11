const express = require('express');
const router = express.Router();

const CQController = require('../controllers/class_question.controller');
const CQModel = require('../models/class_question.model');

//get question bu class id 
router.get('/:class_id',CQController.getCQlist);

//create new class quesiton by class id
router.post('/add/:class_id',CQController.createNewCQ);

//delete class question by id
router.post('/delete/:class_id/:question_id',function(req, res) {
    CQModel.deleteCQ(req.params.class_id, req.params.question_id, (err, CQ)=>{
        if(err)
            res.send(err);
        else
            res.json({success:true, message: 'class_question deleted successfully!'});
    })
});

//update clas_question by class id
router.post('/update/:class_id/:question_id',function(req,res) {
    const CQReqData = new CQModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        CQModel.updateCQ(req.params.class_id, req.params.question_id,CQReqData ,(err,CQ)=>{
            if(err)
                res.send(err);
            else
                res.json({success:true, message: 'class_question updated successfully!'});
        })  
    }
});

module.exports = router; 
 
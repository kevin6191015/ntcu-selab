const express = require('express');
const router = express.Router();

const StudentController = require('../controllers/student.controller');
const StudentModel = require('../models/student.model');

//get students by class id
router.get('/:id',StudentController.getStudentByclassId);

//get students by class name
//router.get('/byname/:name',StudentController.getStudentByclassName);

//add by class id
router.post('/add/:id',StudentController.addStudentByclassId);

router.post('/update/:class_id/:student_id', function(req,res) {
    const StudentReqData = new StudentModel(req.body);
    StudentModel.updateStudentByclassIdandStudentId(req.params.class_id,req.params.student_id,StudentReqData, (err, Student)=>{
        if(err)
            res.send(err);
        else
            res.json({success:true, message: 'student update successfully!'});
    })
});

//delete
router.post('/delete/:class_id/:student_id', function(req,res) {
    StudentModel.deleteStudentByclassIdandStudentId(req.params.class_id,req.params.student_id, (err, Student)=>{
        if(err)
            res.send(err);
        else
            res.json({success:true, message: 'student delete successfully!'});
    })
})

//check by class and student id
router.get('/checkbyid/:class_id/:student_id', function(req,res) {
    StudentModel.checkbyid(req.params.class_id,req.params.student_id, (err, check)=>{
        if(err)
            res.send(err);
        else    
            res.send(check);
    })
});



module.exports = router;
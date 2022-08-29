
const StudentModel = require('../models/student.model');

//get all students by class id
exports.getStudentByclassId = (req, res)=>{
    console.log('get students by class id');
    StudentModel.getStudentByclassId(req.params.id, (err,students)=>{
        if(err)
            res.send(err);
        console.log('student list',students);
        res.send(students);
    })
}

//get all students by class id
exports.getStudentByclassName = (req, res)=>{
    console.log('get students by class name');
    StudentModel.getStudentByclassName(req.params.name, (err,students)=>{
        if(err)
            res.send(err);
        console.log('student list',students);
        res.send(students);
    })
}

//add student by class id 
exports.addStudentByclassId = (req,res)=>{
    const StudentReqData = new StudentModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        es.send(400).send({success: false, message: 'Please all fields'});
    }else{
        StudentModel.addStudentByclassId(req.params.id, StudentReqData, (err,student) =>{
            if(err)
                res.send(err);
            else
            res.json({status: true, message: 'Student Added Successfully',data: student.insertId})
            
        })
    }
}

//add students by class id 
exports.addStudentsByclassId = (req,res)=>{
    const StudentReqData = new StudentModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        es.send(400).send({success: false, message: 'Please all fields'});
    }else{
        StudentModel.addStudentsByclassId(req.params.id, StudentReqData, (err,student) =>{
            if(err)
                res.send(err);
            else
            res.json({status: true, message: 'Student Added Successfully',data: student.insertId})
            
        })
    }
}




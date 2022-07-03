const ClassModel = require('../models/class.model');

//get all classes
exports.getclasslist = (req,res)=>{
    ClassModel.getclasslist((err, classes) =>{
        if (err)
            res.send(err);
        res.send(classes);
    })
}

//add new class
exports.createNewClass = (req,res) =>{
    const classReqData = new ClassModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        ClassModel.createNewClass(classReqData, (err, classes)=>{
            if(err)
                res.send(err);
                res.json({status: true, message: 'New Class Created Successfully'});
        })
    } 
}

exports.deleteClass = (req,res)=>{
    ClassModel.deleteClass(req.params.class_id, (err,question)=>{
        if(err)
            res.send(err);
            res.json({success:true, message: 'Class deleted successfully!'});
    })
}

exports.updateClass = (req,res)=>{
    const classReqData = new ClassModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        ClassModel.updateClass(req.params.class_id, classReqData, (err,classes)=>{
            if(err)
                res.send(err);
            else
                res.json({status: true, message: 'Class Updated Successfully'});    
        })
    }
}
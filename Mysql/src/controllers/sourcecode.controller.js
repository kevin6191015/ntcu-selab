const SourcecodeModel = require('../models/sourcecode.model');

//get 
exports.getSourcecodeByName = (req,res) =>{
    SourcecodeModel.getSourcecodeByName(req.params.name, (err,sourcecode) =>{
        if(err)
            res.send(err);
        console.log("single sourcecode",sourcecode);
        res.send(sourcecode);
    })
}

// add new sourcecode
exports.addSourcecode = (req,res) =>{
    const sourcecodeReqData = new SourcecodeModel(req.body);
    console.log('sourcesodeReqData',sourcecodeReqData);
    //check null
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        SourcecodeModel.addSourcecode(sourcecodeReqData, (err, sourcecode)=>{
            if(err)
                res.send(err);
                res.json({status: true, message: 'Sourececode added Successfully',data: sourcecode.insertId});
        })
    }   
}

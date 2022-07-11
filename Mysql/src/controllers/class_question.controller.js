const CQModel = require('../models/class_question.model');

//get class question list
exports.getCQlist = (req,res)=>{
    CQModel.getCQlist(req.params.class_id, (err,cqlist) =>{
        if (err)
            res.send(err);
        res.send(cqlist);
    })
}

exports.createNewCQ = (req,res) =>{
    const CQReqData = new CQModel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        CQModel.createNewCQ(req.params.class_id, CQReqData, (err, CQ)=>{
            if(err)
                res.send(err);
            res.json({status: true, message: 'New Class question Created Successfully'});
        })
    }
} 
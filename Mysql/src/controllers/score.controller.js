
const ScoreMOdel = require('../models/score.model');


//get by project_name
exports.getbypn = (req,res)=>{
    ScoreMOdel.getbypn(req.params.project_name,(err,scores)=>{
        if(err)
            res.send(err);
        else    
            res.send(scores);
    })
}

//add score
exports.addScore = (req,res) => {
    const scoreReqData = new ScoreMOdel(req.body);
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        ScoreMOdel.addScore(scoreReqData, (err, score)=>{
            if(err)
                res.send(err);
                res.json({status: true, message: 'Score_list Created Successfully'});
        })
    }
}

//get everyone's latest score by and half porject_name
exports.geteveryonescore = (req,res) =>{
    ScoreMOdel.geteveryonescore(req.params.pn, (err,scores)=>{
        if(err)
            res.send(err);
        else 
            res.send(scores);
    })
}

//get person score
exports.getpersonscore = (req,res) =>{
    ScoreMOdel.getpersonscore(req.params.project_name, (err,scores)=>{
    if(err)
        res.send(err);
    else 
        res.send(scores);
    })
}

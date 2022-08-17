
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
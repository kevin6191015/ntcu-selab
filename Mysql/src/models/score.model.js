var dbConn = require('../../config/db.config');

var Score = function(score){
    this.student_id = score.student_id;
    this.project_name = score.project_name;
    this.submit_times = score.submit_times;
    this.analysis_date = score.analysis_date;
    this.unit_test_score = score.unit_test_score;
    this.code_quality = score.code_quality;
}

//get by project_name
Score.getbypn = (project_name,result)=>{
    dbConn.query('SELECT * FROM score_list WHERE project_name =?',project_name, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}


module.exports = Score;
var dbConn = require('../../config/db.config');//project db
var dbConn2 = require('../../config/db2.config');//system db

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

//add score
Score.addScore = (ScoreReqData, result) => {
    dbConn.query('INSERT INTO score_list SET ? ', ScoreReqData, (err, res)=>{
        if (err) {
            console.log('Errow while inserting data');
            console.log(err);
            result(err);
         }else{
            console.log('Score created successfully');
            result(null,res);
        }
    })
}

//
Score.geteveryonescore = (pn,result)=>{
    dbConn.query('SELECT student_id,MAX(submit_times) AS submit_times ,MAX(unit_test_score) AS unit_test_score,MAX(analysis_date) AS analysis_date FROM score_list WHERE SUBSTR(project_name,1,24) =? GROUP BY student_id',pn, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}

Score.getpeopleanswered = (semester, class_id, result)=>{
    class_id1 = padLeft(class_id,3);
    var sql1 = 'SELECT  question_id,release_time FROM class_' + class_id1 + '_questions'
    dbConn2.query(sql1, (err,res)=>{
        let i
        var sql4 =''
        var half_project_name =[]
        for (i = 0 ; i < JSON.parse(JSON.stringify(res)).length; i++) {
            var question_id = JSON.parse(JSON.stringify(res))[i].question_id;
            var release_time = JSON.parse(JSON.stringify(res))[i].release_time;
            half_project_name[i] = question_id + '_' + class_id1 + '_' + semester + '_' + release_time;
            sql4 += "SELECT COUNT(DISTINCT student_id) AS people_answered FROM score_list WHERE SUBSTR(project_name,1,24) = '" + half_project_name[i] + "'; ";
            console.log(half_project_name[i])
        }
        dbConn.query(sql4 ,(err,res)=>{
            if (err)
                result(err)
            else {
                if (half_project_name.length == 1) {
                    let people = [];
                    let temp = {people_answered: res[0].people_answered}
                    people.push(temp);
                    result(null,people);
                } else {
                    let people = [];
                    console.log(res)
                    for (let i = 0; i < half_project_name.length; i++) {
                        let temp = {people_answered: res[i][0].people_answered}
                        people.push(temp);
                    }
                    result(people);
                }
            }
            /*
            if(err)
                result(err)
            else{
                console.log(res)
                result(null,res)
            }
            */
        })
    }) 
}

Score.getpeoplecorrect = (semester, class_id, result)=>{
    class_id1 = padLeft(class_id,3);
    var sql1 = 'SELECT  question_id,release_time FROM class_' + class_id1 + '_questions'
    dbConn2.query(sql1, (err,res)=>{
        let i
        var sql2 =''
        var half_project_name =[]
        for (i = 0 ; i < JSON.parse(JSON.stringify(res)).length; i++) {
            var question_id = JSON.parse(JSON.stringify(res))[i].question_id;
            var release_time = JSON.parse(JSON.stringify(res))[i].release_time;
            half_project_name[i] = question_id + '_' + class_id1 + '_' +  semester + '_' + release_time
            sql2 += "SELECT COUNT(DISTINCT student_id) AS people_correct FROM score_list WHERE SUBSTR(project_name,1,24) = '"+ half_project_name[i] + "'AND unit_test_score = 100;"
        }
        dbConn.query(sql2 ,(err,res)=>{
            if (err)
                result(err)
            else {
                if (half_project_name.length == 1) {
                    let people = [];
                    let temp = {people_correct: res[0].people_correct}
                    people.push(temp);
                    result(null,people);
                } else {
                    let people = [];
                    console.log(res)
                    for (let i = 0; i < half_project_name.length; i++) {
                        let temp = {people_correct: res[i][0].people_correct}
                        people.push(temp);
                    }
                    result(people);
                }
            }
        })
    })
}

Score.getpersonscore = (project_name,result) =>{
    dbConn.query('SELECT code_quality,analysis_date,unit_test_score FROM score_list WHERE project_name =? ORDER BY submit_times DESC',project_name, (err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);        
    })
}


module.exports = Score;


function sleep(time) {
    return new Promise(resolve => setTimeout(resolve, time));
}
 
async function delay() {
    await sleep(500000);
}

function padLeft(str,lenght){
    if( str.length >= lenght)
        return str;
    else
        return padLeft("0"+str, lenght);
}
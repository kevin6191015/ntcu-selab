var dbConn = require('../../config/db2.config');

var Class_question = function(CQ){
    this.question_id = CQ.question_id
    this.question_name = CQ.question_name
    this.release_time = CQ.release_time
    this.deadline = CQ.deadline
    this.assignment_name = CQ.assignment_name
}

Class_question.getCQlist = (class_id, result)=>{
    class_id = padLeft(class_id,3);
    var sql = 'SELECT * FROM class_' + class_id + '_questions';
    dbConn.query(sql, (err,res)=>{
        if(err)
            result(null,err);
        else    
            result(null,res);
    })
}

Class_question.createNewCQ = (class_id, CQReqData, result) =>{
    class_id = padLeft(class_id,3);
    var sql = 'INSERT INTO class_'+ class_id + '_questions SET ?';
    dbConn.query(sql, CQReqData, (err,res)=>{
        if(err)
            result(err);
        else
            result(null,res);
    })
}

Class_question.deleteCQ = (class_id, question_id, result)=>{
    class_id = padLeft(class_id,3);
    var sql = 'DELETE FROM class_' + class_id + '_questions WHERE question_id =?';
    dbConn.query(sql, question_id,(err,res)=>{
        if(err)
            result(err);
        else    
            result(null,res);
    })
}

Class_question.updateCQ = (class_id, question_id, release_time, CQReqData, result) =>{
    class_id = padLeft(class_id,3);
    //console.log(class_id);
    //console.log(question_id); 
    var sql = 'UPDATE class_' + class_id + '_questions SET assignment_name=?,question_name=?,release_time=?,deadline=? WHERE question_id = ? AND release_time =?';
    dbConn.query(sql,[CQReqData.assignment_name, CQReqData.question_name,CQReqData.release_time,CQReqData.deadline, question_id,release_time], (err, res)=>{
        if(err)
            result(err);
        else
            result(null,res);
    })
}

function padLeft(str,lenght){ 
    if( str.length >= lenght) 
        return str;
    else
        return padLeft("0"+str, lenght);
}

module.exports = Class_question;
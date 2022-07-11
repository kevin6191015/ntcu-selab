var dbConn = require('../../config/db2.config');

var Class = function(CLass){
    this.class_id = CLass.class_id
    this.class_name = CLass.class_name
    this.teacher = CLass.teacher
    this.TA = CLass.TA
}

Class.getclasslist = (result)=>{
    dbConn.query('SELECT * FROM classes_list', (err,res)=>{
        if(err)
            result(null,err);
        else   
            result(null,res);
    })
}

Class.createNewClass = (classReqData, result) =>{
    let changeclass = JSON.parse(JSON.stringify(classReqData));
    dbConn.query('SELECT class_id FROM classes_list WHERE class_id = (SELECT MAX(class_id) FROM classes_list)', (err,res)=>{
        var class_id = JSON.parse(JSON.stringify(res))[0].class_id;
        class_id += 1;
        changeclass.class_id = class_id;       
        class_id = padLeft(class_id,3);
        console.log(changeclass);
        var sql1 = 'CREATE TABLE class_' + class_id + '_questions (question_id TEXT, question_name TEXT, release_time DATE, deadline DATE)';
        var sql2 = 'CREATE TABLE class_' + class_id + '_student (student_id TEXT, student_name TEXT)';
        dbConn.query(sql1,(err,res)=>{
            //if(err)
            //    result(null,err);
            //else
                //result(null,res);
        }) 
        dbConn.query(sql2,(err,res)=>{
            //if(err)
            //    result(null,err);
            //else
                //result(null,res);
        })
        dbConn.query('INSERT INTO classes_list SET ? ',changeclass, (err,res)=>{
            if(err)
                result(err);
            else    
                result(null,res);
        }) 
        
    })  
}

Class.deleteClass = (class_id, result)=>{
    class_id1 = padLeft(class_id,3);
    var sql1 = 'DROP TABLE class_' + class_id1 + '_questions';
    var sql2 = 'DROP TABLE class_' + class_id1 + '_student';
    dbConn.query(sql1,(err,res)=>{
        //if(err)
        //    result(err);
        //else
            //result(null,res);
    }) 
    dbConn.query(sql2,(err,res)=>{
        //if(err)
        //    result(err); 
        //else
            //result(null,res);
    })
    dbConn.query("DELETE FROM classes_list WHERE class_id=?",class_id,(err,res)=>{
        if(err)
            result(null,err);
        else   
            result(null,res);
    })
}

Class.updateClass = (class_id, classReqData,result)=>{
    dbConn.query('UPDATE classes_list SET class_name=?, teacher=?, TA=? WHERE class_id =?',[classReqData.class_name,classReqData.teacher,classReqData.TA,class_id], (err,res)=>{
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

module.exports = Class;

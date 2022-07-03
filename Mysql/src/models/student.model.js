var dbConn = require('../../config/db2.config');

var Student = function(student){
    this.student_id = student.student_id;
    this.student_name = student.student_name;
}



//get question by Num 
Student.getStudentByclassName = (id,result) =>{
    var class_id;
    dbConn.query('SELECT class_id FROM classes_list WHERE class_name = ?',id,(err,res)=>{
        var classes = JSON.parse(JSON.stringify(res));
        class_id = classes[0].class_id;
        //console.log(class_id);
        if(err){
            console.log('Error while fetching students by name',err);
            //result(null,err);
        }else{
            //result(null,res);
        }
        class_id = padLeft(class_id,3);
        var s = 'SELECT * From class_' + class_id +'_student'
        dbConn.query(s,(err,res)=>{
            //console.log(res);
            if(err){
                console.log('Error while fetching students by name',err);
                result(null,err);
            }else{
                result(null,res);
            }
        })
    })
}

Student.getStudentByclassId = (id,result) =>{
    var class_id;
    class_id = padLeft(id,3);
    var s = 'SELECT * From class_' + class_id +'_student'
    dbConn.query(s,(err,res)=>{
       //console.log(res);
        if(err){
            console.log('Error while fetching students by id',err);
            result(null,err);
        }else{
           result(null,res);
        }
    })
}

Student.addStudentByclassId = (id, StudentReqData, result)=>{
    var class_id;
    class_id = padLeft(id,3);
    var s = 'INSERT INTO class_' + class_id +'_student SET ?';
    dbConn.query(s,StudentReqData,(err,res)=>{
        if(err)
            result(err);
        else   
            result(null,res);
    })   
}

Student.updateStudentByclassIdandStudentId = (class_id , student_id, StudentReqData, result)=>{
    var Class_id;
    Class_id = padLeft(class_id,3);
    //var s = 'UPDATE class_' + Class_id + '_student SET student_id='+StudentReqData.student_id+',student_name='+StudentReqData.student_name+ ' WHERE student_id = '+StudentReqData.student_id
    var s = 'UPDATE class_' + Class_id + '_student SET student_id=?,student_name=? WHERE student_id = ?';
    dbConn.query(s,[StudentReqData.student_id,StudentReqData.student_name,student_id],(err,res)=>{
        if(err){
            result(null,err);
        }else{
            result(null,res);
        }
    })
}

Student.deleteStudentByclassIdandStudentId = (class_id, student_id,result)=>{
    var Class_id;
    Class_id = padLeft(class_id,3);
    var s = 'DELETE FROM class_' + Class_id + '_student WHERE student_id=?';
    dbConn.query(s,student_id,(err,res)=>{
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

module.exports = Student;
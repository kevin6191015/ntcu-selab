var dbConn = require('../../config/db.config');

var Sourcecoode = function(sourcecode){
    this.question_name = sourcecode.question_name;
    this.code = sourcecode.code;
}
//get Sourcecode by name
Sourcecoode.getSourcecodeByName = (name,result) =>{
    dbConn.query('SELECT * FROM sourcecode_list WHERE question_name=?', name, (err,res)=>{
        if(err){
            console.log("Error while fetching SourceCode by name");
            ressult(null,err);
        }else{
            result(null,res);
        }
    })
}

//add new Sourcecode
Sourcecoode.addSourcecode = (sourcecodeReqData, result) =>{
    dbConn.query('INSERT INTO sourcecode_list SET ? ',sourcecodeReqData, (err, res)=>{
        if(err){
            console.log('Errow while inserting data');
            result(err);
        }else{
            console.log('Sourcecode added successfully');
            result(null,res);
        }
    })
}

module.exports = Sourcecoode;

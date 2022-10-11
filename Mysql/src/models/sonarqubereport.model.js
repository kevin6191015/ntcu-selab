var dbConn = require('../../config/db.config');

var SQreport = function(sqreport){
    this.student_id = sqreport.student_id
    this.project_name = sqreport.project_name
    this.submit_times = sqreport.submit_times
    this.unit_test_result = sqreport.unit_test_result
    this.compile_result = sqreport.compile_result
    this.reliability_rating = sqreport.reliability_rating
    this.security_rating = sqreport.security_rating
    this.security_review_rating = sqreport.security_review_rating
    this.sqale_rating = sqreport.sqale_rating 
    this.analysis_date = sqreport.analysis_date
    this.source_code = sqreport.source_code
    this.code_smells = sqreport.code_smells
    this.bugs = sqreport.bugs
    this.vulnerabilities = sqreport.vulnerabilities
    this.code_smells_report = sqreport.code_smells_report
    this.suggestion_code = sqreport.suggestion_code
}

//get by id and project_name
SQreport.getSQreportByIdandName = ( project_name, result)=>{
    dbConn.query('SELECT * FROM Sonarqube_reports  WHERE project_name = ?', project_name,(err,res)=>{
        if(err){
            console.log('Error while fetching SQreport by name',err);
            result(err);
        }else{
            result(null,res);
        }
    })
}

//delete sqreport by id and name
SQreport.deleteSQreport = (student_id, project_name, result)=>{
	dbConn.query('DELETE FROM Sonarqube_reports  WHERE student_id = ? AND project_name = ?', [student_id,project_name],(err,res)=>{
		if(err){
			console.log('Error while deleting the sonarqube report');
			result(err);
		}else{
            console.log('work well')
			result(res);
		}
	})
}

//add new SQreport
SQreport.addSQreport = (sqreportReqData, result) =>{
    dbConn.query('INSERT INTO Sonarqube_reports SET ? ',sqreportReqData, (err, res)=>{
        if(err){
            console.log('Errow while inserting data');
            result(err); 
        }else{
            console.log('SQreport created successfully');
            result(null,res);
        }
    })
}

SQreport.getpersonalreport = (project_name, result) =>{
    dbConn.query('SELECT compile_result,source_code,report_suggestion FROM Sonarqube_reports WHERE project_name = ? ORDER BY submit_times DESC', project_name, (err,res)=>{
        if(err)
            result(err);
        else    
            result(res);         
    })
}


module.exports = SQreport;
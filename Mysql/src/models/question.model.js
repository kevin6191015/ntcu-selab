var dbConn = require('../../config/db.config');

var Question = function(question){
    this.num = question.num;
    this.question_name = question.question_name;
    this.question_description = question.question_description;
    this.category = question.category;
    this.image1 = question.image1;
    this.image2 = question.image2;
    this.input1 = question.input1;
    this.input2 = question.input2;
    this.input3 = question.input3;
    this.input4 = question.input4;
    this.input5 = question.input5;
    this.input6 = question.input6;
    this.input7 = question.input7;
    this.input8 = question.input8;
    this.input9 = question.input9;
    this.input10 = question.input10;
    this.output1 = question.output1;
    this.output2 = question.output2;
    this.output3 = question.output3;
    this.output4 = question.output4;
    this.output5 = question.output5;
    this.output6 = question.output6;
    this.output7 = question.output7;
    this.output8 = question.output8;
    this.output9 = question.output9;
    this.output10 = question.output10;
}
 
 //get all questions
 Question.getAllquestion = (result)=>{
     dbConn.query('SELECT * FROM question_bank', (err,res)=>{
         if(err){
             console.log('Erroe while fetching questions', err);
             result(null,err);
         }else{
             console.log('Questions fetched successfully');
             result(null,res);
         }
     })
 }   
   
//get question by Num 
  Question.getQuestionByNum = (num,result) =>{
      dbConn.query('SELECT * FROM question_bank WHERE num=?', num, (err,res)=>{
          if(err){
              console.log('Error while fetching question by num',err);
              result(null,err);
          }else{
              result(null,res);
          }
      })
  }
 
//create new question
  Question.createQuestion = (questionReqData, result) =>{
      dbConn.query('INSERT INTO question_bank SET ? ',questionReqData, (err, res)=>{
          if(err){
              console.log('Errow while inserting data');
              result(null,err);
          }else{
              console.log('Question created successfully');
              result(null,res);
          }
      })
  }

//update question
  Question.updateQuestion = (num, questionReqData, result)=>{
	  dbConn.query("UPDATE question_bank SET question_name=?,question_description=?,category=?,image1=?,image2=?,input1=?,input2=?,input3=?,input4=?,input5=?,input6=?,input7=?,input8=?,input9=?,input10=?,output1=?,output2=?,output3=?,output4=?,output5=?,output6=?,output7=?,output8=?,output9=?,output10=? WHERE num = ?",[questionReqData.question_name,questionReqData.question_description,questionReqData.category,questionReqData.image1,questionReqData.image2,questionReqData.input1,questionReqData.input2,questionReqData.input3,questionReqData.input4,questionReqData.input5,questionReqData.input6,questionReqData.input7,questionReqData.input8,questionReqData.input9,questionReqData.input10,questionReqData.output1,questionReqData.output2,questionReqData.output3,questionReqData.output4,questionReqData.output5,questionReqData.output6,questionReqData.output7,questionReqData.output8,questionReqData.output9,questionReqData.output10,num],(err,res)=>{
          if(err){
              console.log('Error while updating the question');
              result(null,err);
          }else{
              console.log('Question updated sucessfully');
              result(null,res);
          }
  })
}

//delete question
Question.deleteQuestion = (num, result)=>{
	dbConn.query("DELETE FROM question_bank WHERE num=?",num,(err,res)=>{
		if(err){
			console.log('Error while deleting the question');
			result(null,err);
		}else{
			result(null,res);
		}
	})
}

 module.exports = Question;

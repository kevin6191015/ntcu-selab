var dbConn = require('../../config/db.config');

var Testdata = function(testdata){
    this.num = testdata.num;
    this.question_name = testdata.question_name;
    this.question_description = testdata.question_description;
    this.image1 = testdata.image1;
    this.image2 = testdata.image2;
    this.input1 = testdata.input1;
    this.input2 = testdata.input2;
    this.input3 = testdata.input3;
    this.input4 = testdata.input4;
    this.input5 = testdata.input5;
    this.input6 = testdata.input6;
    this.input7 = testdata.input7;
    this.input8 = testdata.input8;
    this.input9 = testdata.input9;
    this.input10 = testdata.input10;
    this.output1 = testdata.output1;
    this.output2 = testdata.output2;
    this.output3 = testdata.output3;
    this.output4 = testdata.output4;
    this.output5 = testdata.output5;
    this.output6 = testdata.output6;
    this.output7 = testdata.output7;
    this.output8 = testdata.output8;
    this.output9 = testdata.output9;
    this.output10 = testdata.output10
    this.input_or_not = testdata.input_or_not;
    this.id = testdata.id;
}

//get testdata by id (changed from by name)
Testdata.getTestdatabyName = (question_name,result) =>{
      dbConn.query('SELECT question_name,id,input1,input2,input3,input4,input5,input6,input7,input8,input9,input10,output1,output2,output3,output4,output5,output6,output7,output8,output9,output10 FROM question_bank WHERE id=?', question_name, (err,res)=>{
          if(err){
              console.log('Error while fetching question by question_name',err);
              result(null,err);
          }else{
              result(null,res);
          }
      })
}
//get all testdata
Testdata.getAllTestdata = (result)=>{
	dbConn.query('SELECT question_name,id,input1,input2,input3,input4,input5,input6,input7,input8,input9,input10,output1,output2,output3,output4,output5,output6,output7,output8,output9,output10 FROM question_bank', (err,res)=>{
		if(err){
			console.log('Erroe while fetching testdatas', err);
            result(null,err);
		}else{
			console.log('Testdatas fetched successfully');
             result(null,res);
		}
	})
}

module.exports = Testdata;

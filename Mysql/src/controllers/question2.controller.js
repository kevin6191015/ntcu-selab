const QuestionModel = require('../models/question2.model');

//get all question list
exports.getquestionlist = (req,res)=>{
    //console.log('here all question list');
    QuestionModel.getAllquestion((err, questions) =>{
        console.log('We are here!');
        if(err)
			res.send(err);
        //console.log('Questions',questions); 
        res.send(questions);
    })
}

//get question by ID
exports.getQuestionByNum = (req, res)=>{
    QuestionModel.getQuestionByNum(req.params.id, (err, question)=>{
        if(err)
            res.send(err);
        //console.log('single question',question);
        res.send(question);
    })
}

//create new question
exports.createNewQuestion = (req,res) =>{
    const questionReqData = new QuestionModel(req.body);
    //console.log('questionReqData',questionReqData);
    //check null
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        QuestionModel.createQuestion(questionReqData, (err, question)=>{
            if(err)
                res.send(err);
                res.json({status: true, message: 'Qusetion Created Successfully'});
        })
    }   
}

//update question
exports.updateQuestion = (req,res)=>{
    const questionReqData = new QuestionModel(req.body);
    console.log('questionReqData update',questionReqData);
    //check null
    if(req.body.constructor === Object && Object.keys(req.body).length === 0){
        res.send(400).send({success: false, message: 'Please all fields'});
    }else{
        console.log('valid data');
        QuestionModel.updateQuestion(req.params.id, questionReqData, (err, question)=>{
            if(err)
            res.send(err);
            else
            res.json({status: true, message: 'Qusetion updated Successfully',data: question.insertId});
        })
    }

}

//delete question
exports.deleteQuestion = (req,res)=>{
	QuestionModel.deleteQuestion(req.params.id, (err,question)=>{
		if(err)
			res.send(err);
		res.json({success:true, message: 'Qusestion deleted successfully!'});
			
	})
}

exports.getQuestionByTeacher = (req,res)=>{
    QuestionModel.getQuestionByTeacher(req.params.teacher, (err, question)=>{
        if(err)
            res.send(err);
        res.send(question);
    })
}

exports.getQuestionByClass = (req,res)=>{
    QuestionModel.getQuestionByClass(req.params.class, (err, question)=>{
        if(err)
            res.send(err);
        res.send(question);
    })
}



//get teacher list
exports.getteachers = (req,res)=>{
    QuestionModel.getteachers((err,teachers)=>{
        if(err)
            res.send(err);
        else
            res.send(teachers);
    })
}

//get  list
exports.getclass = (req,res)=>{
    QuestionModel.getclass((err,classes)=>{
        if(err)
            res.send(err);
        else
            res.send(classes);
    })
}

const TestdataModel = require('../models/testdata.model');

//get testdata by name
exports.getTestdatabyName = (req,res)=>{
    TestdataModel.getTestdatabyName(req.params.question_id, (err, question)=>{
        if(err)
        res.send(err);
        console.log('single question',question);
        res.send(question);
    })
}

exports.getAllTestdata = (req,res)=>{
	TestdataModel.getAllTestdata((err, testdatas) =>{
		console.log('get all testdata');
		if(err)
			res.send(err);
		console.log('Testdatas',testdatas);
		res.send(testdatas);
	})
}
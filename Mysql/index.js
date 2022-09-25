//require('dotenv').config();
const express = require('express');
const bodyParser = require('body-parser');

const app = express();


const port = process.env.APP_PORT || 3000;

//parse request data content type application/x-www-form-rulencoded
app.use(bodyParser.urlencoded({extanded: false}));

//parse request data content type application/json
app.use(bodyParser.json());

app.get('/',(req,res)=>{
    res.send('Hello World!');
});

//import question routes
const questionRoutes = require('./src/routes/question.route');
//import testdata routes
const testdataRoutes = require('./src/routes/testdata.route');
//import sourcecode routes
const sourcecodeRoutes = require('./src/routes/sourcecode.route');
//import sonarqube_report routes
const sqreportRoutes = require('./src/routes/sonarqubereport.route');
//import student routes
const studentRoutes = require('./src/routes/student.route');
//import class routes
const classRoutes = require('./src/routes/class.route');
//import class question routes
const CQRoutes = require('./src/routes/class_question.route');
//import question2 routes
const questionRoutes2 = require('./src/routes/question2.route');
//import score routes
const scoreRoutes = require('./src/routes/score.route');
//import userdb routes
const userdbRoutes = require('./src/routes/userdb.route')

//creat question routes
app.use('/api/question1',questionRoutes);

//create testdata routes
app.use('/api/testdata',testdataRoutes);

//create sourcecode routes
app.use('/api/sourcecode',sourcecodeRoutes);

//create sonarqubereport routes
app.use('/api/sqreport',sqreportRoutes);

//create student routes
app.use('/api/student',studentRoutes);

//Create class routes
app.use('/api/class',classRoutes);

//Create
app.use('/api/classquestion',CQRoutes);

//create question2 routes
app.use('/api/question2',questionRoutes2);

//create score routes
app.use('/api/score',scoreRoutes);

//create userdb routes
app.use('/api/user',userdbRoutes);


//listen to port
app.listen(port, ()=>{
    console.log(`Express is running at port ${port}`);
});

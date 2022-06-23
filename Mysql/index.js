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

//creat question routes
app.use('/api/question',questionRoutes);

//create testdata routes
app.use('/api/testdata',testdataRoutes);

//listen to port
app.listen(port, ()=>{
    console.log(`Express is running at port ${port}`);
});

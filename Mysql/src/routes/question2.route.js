const express = require('express');
const router = express.Router();

const QuestionController = require('../controllers/question2.controller');

//get all questions
router.get('/',QuestionController.getquestionlist);

//get question by id
router.get('/byid/:id',QuestionController.getQuestionByNum);

//create new question
router.post('/add/',QuestionController.createNewQuestion);

router.post('/update/:id',QuestionController.updateQuestion);
//update question by id

//delete question by id
router.post('/delete/:id',QuestionController.deleteQuestion);

//get by teacher
router.get('/byteacher/:teacher',QuestionController.getQuestionByTeacher);

//get by class
router.get('/public/',QuestionController.getQuestionPublic);

//get teacher list
router.get('/teachers/',QuestionController.getteachers);

//get class list
router.get('/class/',QuestionController.getclass);

//get latest qid
router.get('/qid/',QuestionController.getLatesetQid);

module.exports = router;

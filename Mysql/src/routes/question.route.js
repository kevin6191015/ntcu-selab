const express = require('express');
const router = express.Router();

const QuestionController = require('../controllers/question.controller');

//get all questions
router.get('/',QuestionController.getquestionlist);

//get question by num
router.get('/:id',QuestionController.getQuestionByNum);

//create new question
router.post('/add/',QuestionController.createNewQuestion);

//update question by id
router.post('/update/:id',QuestionController.updateQuestion);


//delete question by id
router.post('/delete/:id',QuestionController.deleteQuestion);

module.exports = router;

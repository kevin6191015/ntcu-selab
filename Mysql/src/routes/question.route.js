const express = require('express');
const router = express.Router();

const QuestionController = require('../controllers/question.controller');

//get all questions
router.get('/',QuestionController.getquestionlist);

//get question by num
router.get('/:num',QuestionController.getQuestionByNum);

//create new question
router.post('/',QuestionController.createNewQuestion);

//update question by num
router.put('/:num',QuestionController.updateQuestion);

//delete question by num
router.delete('/:num',QuestionController.deleteQuestion);

module.exports = router;

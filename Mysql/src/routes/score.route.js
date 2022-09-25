const express = require('express');
const router = express.Router();

const ScoreController = require('../controllers/score.controller');
const ScoreMOdel = require('../models/score.model');

//get by project_name
router.get('/:project_name',ScoreController.getbypn);

//add score
router.post('/add/',ScoreController.addScore);


//get everyone's latest score by and half porject_name
router.get('/geteveryonescore/:pn',ScoreController.geteveryonescore);

//get people answered
router.get('/getpeopleanswered/:semester/:class_id', function(req, res) {
  ScoreMOdel.getpeopleanswered(req.params.semester, req.params.class_id, (err, Score) =>{
    if(err)
      res.send(err);
    else    
      res.send(Score);
  })
});

//get people correct
router.get('/getpeoplecorrect/:semester/:class_id', function(req, res) {
  ScoreMOdel.getpeoplecorrect(req.params.semester, req.params.class_id, (err, Score) =>{
    if(err)
      res.send(err);
    else    
      res.send(Score);
  })
});

//get person score
router.get('/getpersonscore/:project_name',ScoreController.getpersonscore);

module.exports = router;
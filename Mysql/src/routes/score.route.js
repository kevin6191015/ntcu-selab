const express = require('express');
const router = express.Router();

const ScoerController = require('../controllers/score.controller');

//get by project_name
router.get('/:project_name',ScoerController.getbypn);

module.exports = router;
const express = require('express');
const router = express.Router();

const TestdataController = require('../controllers/testdata.controller');

//get testdata by name
router.get('/:question_name',TestdataController.getTestdatabyName);

//get all testdata
router.get('/',TestdataController.getAllTestdata);

module.exports = router;

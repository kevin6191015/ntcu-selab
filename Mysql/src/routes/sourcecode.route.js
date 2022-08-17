const express = require('express');
const router = express.Router();

const SourcecodeController = require('../controllers/sourcecode.controller');

//get sourcecode by name
router.get('/:name',SourcecodeController.getSourcecodeByName);

//add sourcecode
router.post('/add/',SourcecodeController.addSourcecode);

//update sourcecode
router.post('/update/:name',SourcecodeController.updateSourcecode);

//delete sourcecode
router.post('/delete/:name',SourcecodeController.deleteSourcecode);

module.exports = router;

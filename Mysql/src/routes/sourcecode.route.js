const express = require('express');
const router = express.Router();

const SourcecodeController = require('../controllers/sourcecode.controller');

//get sourcecode by name
router.get('/:name',SourcecodeController.getSourcecodeByName);

//add sourcecode
router.post('/',SourcecodeController.addSourcecode);

module.exports = router;

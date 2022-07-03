const express = require('express');
const router = express.Router();

const ClassController = require('../controllers/class.controller');

//get all classes
router.get('/',ClassController.getclasslist);

//create new class
router.post('/add/',ClassController.createNewClass);

//delete class by id
router.post('/delete/:class_id',ClassController.deleteClass);

//update class by id 
router.post('/update/:class_id',ClassController.updateClass);

module.exports = router;

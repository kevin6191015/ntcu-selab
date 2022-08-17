const express = require('express');
const router = express.Router();

const ClassController = require('../controllers/class.controller');

//get all classes
router.get('/',ClassController.getclasslist);

//get all semester
router.get('/semester/',ClassController.getSemester);

//create new class
router.post('/add/',ClassController.createNewClass);

//delete class by id
router.post('/delete/:class_id',ClassController.deleteClass);

//update class by id 
router.post('/update/:class_id',ClassController.updateClass);

//get classes by semester
router.get('/:semester',ClassController.getClassbysemester);

module.exports = router;

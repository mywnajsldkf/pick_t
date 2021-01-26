const express = require('express');
const router = express.Router();
const Trailer = require('../models/Trailer');

// router.use(bodyParser.urlencoded({extended:true}));

/*
router.post('/test', function(req, res) {
  res.json({
    msg: 'Working'
  });
});
*/

// trailer 등록
router.post('/register', async(req, res, next)=>{
  try {
    const trailer = await Trailer.create({
      trailerName: req.body.trailerName,
      license: req.body.license,
      rentalPlace: req.body.rentalPlace,
      capacity: req.body.capacity,
      facilities: req.body.facilities,
      description: req.body.description
    });

    if(!trailer){
      res.status(400).json({
        success: false, 
        msg: 'Something went wrong'
      });
    }

    // 성공
    res.status(200).json({
      success: true, 
      trailer: trailer,
      msg: 'Successfully created'
    });
  } catch (error) {
    next(error);
  }

});

module.exports = router;
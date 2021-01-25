const express = require('express');
const router = express.Router();
const Trailer = require('../models/Trailer');
const bcryptjs = require('bcryptjs');
const user_jwt = require('../middleware/user_jwt');

router.post('/trailers', async(req,res,next) => {
  try {
    const trailer = await Trailer.create({
      userId: req.body.id,
      trailerName: req.body.trailerName,
      license: req.body.license,
      rentalPlace: req.body.rentalPlace,
      capacity: req.body.capacity,
      facilities: req.body.facilities,
      description: req.body.description
    });

    if(!trailer) {
      res.status(400).json({
        success: false,
        msg: 'Something went wrong'
      });
    }

    res.status(200).json({
      success: true,
      trailer: trailer,
      msg: 'Successfully created'
    });
  } catch(error) {
    next(error);
  }

});

module.exports = router;

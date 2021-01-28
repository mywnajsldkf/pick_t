const express = require('express');
const router = express.Router();
const Trailer = require('../models/Trailer');
const bcryptjs = require('bcryptjs');
const user_jwt = require('../middleware/user_jwt');

//트레일러 정보 등록 API
router.post('/trailers', user_jwt, async(req, res, next) => {
  try {
    const trailer = await Trailer.create({
      userId: req.user.id,
      trailerPhoto: req.body.trailerPhoto,
      trailerName: req.body.trailerName,
      license: req.body.license,
      rentalPlace: req.body.rentalPlace,
      cost: req.body.cost,
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

//메인화면 모든 트레일러 정보 호출 API
router.get('/trailers', user_jwt, async(req,res,next) => {
  try {
    const trailers = await Trailer.find();

    const user = req.user.id;
    console.log(user);

    if(trailers.length == 0) {
      res.status(400).json({
        success: false,
        msg: 'Trailer not exists'
      });
    }

    res.status(200).json({
      success: true,
      totalNumberOfTrailers: trailers.length,
      user: user,
      trailer: trailers,
      msg: 'Successfully fetched'
    });
  } catch(error) {
    next(error);
  }
});

//트레일러 선택시 트레일러에 대한 상세 정보 확인 API
router.get('/trailers/:id', user_jwt, async(req, res, next) => {
  try {
    let trailer = await Trailer.findById(req.params.id);
    const user = req.user.id;

    if(!trailer) {
      res.status(400).json({
        success: false,
        msg: 'trailerInfo not exists'
      });
    }

    res.status(200).json({
      success: true,
      user: user,
      trailer: trailer,
      msg: 'Successfully fetched'
    });
  } catch(error) {
    next(error);
  }
})

//등록한 트레일러 삭제 API - 문제 발생
router.delete('/trailers/:id', user_jwt, async(req, res, next) => {
  try {
    let registeredTrailer = await Trailer.findById(req.params.id);

    if(!registeredTrailer) {
      res.status(400).json({
        success: false,
        msg: 'Trailer not exists'
      });
    }

    registeredTrailer = await Trailer.findByIdAndDelete(req.params.id);

    res.status(200).json({
      success: true,
      msg: 'Successfully Deleted Trailer'
    });
  } catch(error) {
    next(error);
  }
});


module.exports = router;

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
router.post('/trailers', async(req, res, next)=>{
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

// 메인화면에서 모든 트레일러 정보 호출
router.get('/trailers', async(req, res, next)=>{
  try{
    // 모두 찾음
    const trailers = await Trailer.find();
    // 실패
    if(trailers.length == 0){
      res.status(400).json({
        success: false,
        msg: 'Trailer not exists'
      });
    }
    // 성공
    res.status(200).json({
      success: true, 
      totalNumberOfTrailers: trailers.length, 
      trailer: trailers,
      msg: 'Successfully fetched'
    });
  }catch(error){
    next(error);
  }
});

module.exports = router;
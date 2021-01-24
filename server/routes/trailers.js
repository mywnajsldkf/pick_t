const express = require('express');
const router = express.Router();
// const bodyParser = require('body-parser');
const Trailer = require('../models/Trailer');

// router.use(bodyParser.urlencoded({extended:true}));


router.post('/test', function(req, res) {
  res.json({
    msg: 'Working'
  });
});

// trailer 생성
router.post('/register', function(req, res){
    Trailer.create({
        name: req.body.name,
        license: req.body.license,
        rent: req.body.rent,
        cost: req.body.cost,
        num: req.body.num,
        facility: req.body.facility
    },
    function(err, trailer){
        if(err) return res.status(500).send("User 생성 실패");
        res.status(200).send("User 생성 실패")
    });
});

module.exports = router;
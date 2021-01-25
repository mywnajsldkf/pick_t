const express = require('express');
const router = express.Router();
const Trailer = require('../models/Trailer');
const bcryptjs = require('bcryptjs');
const user_jwt = require('../middleware/user_jwt');

// router.post('/users/:id/trailers', async(req,res,next) => {
//   const userId = req.params['id'];
//   const{trailerName, license, rentalPlace, capacity, facilities, description} = req.body;
//
//   try {
//     let trailer = new Trailer();
//
//     trailer.userId = userId;
//     trailer.trailerName = trailerName;
//     trailer.license = license;
//     trailer.rentalPlace = rentalPlace;
//     trailer.capacity = capacity;
//     trailer.facilities = facilities;
//     trailer.description = description;
//
//     await trailer.save();
//
//   } catch(error) {
//     console.log(error);
//   }
//
// });

module.exports = router;

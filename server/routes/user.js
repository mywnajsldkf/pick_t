const express = require('express');
const router = express.Router();
const User = require('../models/User');
const Trailer = require('../models/Trailer');
const bcryptjs = require('bcryptjs');
const user_jwt = require('../middleware/user_jwt');
const jwt = require('jsonwebtoken');
const { token } = require('morgan');

//회원가입 API
router.post('/users',async(req, res, next) => {
    const{ username, email, password, nickname, phone } = req.body;

    try {
        let user_exits = await User.findOne({ email: email })

        if(user_exits) {
            return res.status(400).json({
                success: false,
                msg: 'User already exists'
            });
        }

        let user = new User();

        user.username = username
        user.email = email

        const salt = await bcryptjs.genSalt(10);
        user.password = await bcryptjs.hash(password, salt);

        user.nickname = nickname
        user.phone = phone

        await user.save();

        const payload = {
            user: {
                id: user.id
            }
        }

        console.log(user.id);
        console.log(user._id);

        jwt.sign(payload, process.env.jwtUserSecret, {
            expiresIn: 360000
        }, (err,token) => {
            if(err) throw err;
            res.status(200).json({
                success: true,
                token: token
            });
        });

    } catch(err) {
        console.log(err);
    }
});

//로그인 API
router.post('/login', async(req, res, next) => {
    const email = req.body.email;
    const password = req.body.password;

    try {
        let user = await User.findOne({
            email: email
        });

        if(!user) {
            return res.status(400).json({
                success: false,
                msg:'User not exists go & register to continue.'
            });
        }

    const isMatch = await bcryptjs.compare(password, user.password)

    if(!isMatch) {
        return res.status(400).json({
            success: false,
            msg: 'Invalid password'
        });
    }

    const payload = {
        user: {
            id: user.id
        }
    }

    jwt.sign(
        payload, process.env.jwtUserSecret,
        {
            expiresIn: 360000
        }, (err,token) => {
            if(err) throw err;

            res.status(200).json({
                success: true,
                msg: 'User logged in',
                token: token,
                user: user
            });
        }
    )

    } catch(error)  {
        console.log(error.message);
        res.status(500).json({
            success: false,
            msg: 'Server Error'
        })
    }
});

//마이페이지 개인정보 호출 API
router.get('/users', user_jwt, async(req, res, next) => {
    try {
        const user = await User.findById(req.user.id).select('-password -__v');
            res.status(200).json({
                success: true,
                user: user
            });
    } catch(error) {
        console.log(error.message);
        res.status(500).json({
            success: false,
            msg: 'Server Error'
        })
    }
})

//개인정보 수정 API
router.put('/users/:id', user_jwt, async(req, res, next) => {
  try {
    let user = await User.findById(req.params.id);

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'UserInfo not exists'
      });
    }

    user = await User.findByIdAndUpdate(req.params.id, req.body, {
      new: true,
      runValidators: true
    }).select('-password -__v');

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'Something went wrong.'
      });
    }

    res.status(200).json({
      success: true,
      user: user,
      msg: 'Successfully updated'
    });

  } catch(error) {
    next(error);
  }
});

//관심 목록 등록 API
router.put('/users/:id/likeLists', user_jwt, async(req, res, next) => {
  try {
    let user = await User.findById(req.params.id);

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'User not exists'
      });
    }

    user = await User.findByIdAndUpdate(req.params.id, { $push: { likeLists: req.body } }, {
      new: true,
    });

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'Something went wrong.'
      });
    }

    res.status(200).json({
      success: true,
      user: user,
      msg: 'Successfully likeList registered'
    });

  } catch(error) {
    next(error);
  }
});

//관심 목록 가져오기 API
router.get('/users/:id/likeLists', user_jwt, async(req, res, next) => {
  try {
    let user = await User.findById(req.params.id);

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'User not exists'
      });
    }

    let likedTrailer = await User.findById(req.params.id).select('-_id -username -email -password -nickname -phone -__v -reservationLists');

    if(!likedTrailer) {
      res.status(400).json({
        success: false,
        msg: 'likedTrailer not exists.'
      });
    }

    res.status(200).json({
      success: true,
      trailer: likedTrailer,
      msg: 'Successfully likeList registered'
    });

  } catch(error) {
    next(error);
  }
});

//관심 목록 등록 해제 API
router.delete('/users/:id/likeLists', user_jwt, async(req, res, next) => {
  try {
    let user = await User.findById(req.params.id);

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'User not exists'
      });
    }

    let likedTrailer = await User.findByIdAndUpdate(req.params.id, { $pull: { likeLists: { trailerId: req.body.trailerId } } });

    if(!likedTrailer) {
      res.status(400).json({
        success: false,
        msg: 'LikedTrailer went wrong'
      });
    }

    res.status(200).json({
      success: true
    });
  } catch(error) {
    next(error);
  }
});

//예약하기 API
router.put('/users/:id/reservationLists', user_jwt, async(req, res, next) => {
  try {
    let user = await User.findById(req.params.id);

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'User not exists'
      });
    }

    user = await User.findByIdAndUpdate(req.params.id, { $push: { reservationLists: req.body } }, {
      new: true,
    });

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'Something went wrong.'
      });
    }

    res.status(200).json({
      success: true,
      user: user,
      msg: 'Successfully registered'
    });

  } catch(error) {
    next(error);
  }
});

//예약 목록 가져오기 API
router.get('/users/:id/reservationLists', user_jwt, async(req, res, next) => {
  try {
    let user = await User.findById(req.params.id);

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'User not exists'
      });
    }

    let reservedTrailer = await User.findById(req.params.id).select('-_id -username -email -password -nickname -phone -__v -likeLists');

    if(!reservedTrailer) {
      res.status(400).json({
        success: false,
        msg: 'ReservedTrailer not exists.'
      });
    }

    res.status(200).json({
      success: true,
      trailer: reservedTrailer,
      msg: 'Successfully retrieved'
    });

  } catch(error) {
    next(error);
  }
});

//회원 탈퇴 API
router.delete('/users/:id', user_jwt, async(req, res, next) => {
  try {
    let user = await User.findById(req.params.id);

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'User not exists '
      });
    }

    //해당 유저가 등록한 트레일러 삭제
    let registeredTrailer = await Trailer.findOne({ userId: req.params.id });

    if(!registeredTrailer) {
      res.status(200).json({
        success: true,
        msg: 'Registered trailer not exists'
      });
    }

    registeredTrailer = await Trailer.findOneAndDelete({ userId: req.params.id });
    user = await User.findByIdAndDelete(req.params.id);

    res.status(200).json({
      success: true,
      msg: 'Successfully Deleted User'
    });
  } catch(error) {
    next(error);
  }
});

module.exports = router; //user.js 라는 파일이 모듈로써 동작하기 위해서는 이 파일을 밖으로 누구를 export할 수 있음.

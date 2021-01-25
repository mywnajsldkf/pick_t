const express = require('express');
const router = express.Router();
const User = require('../models/User');
const bcryptjs = require('bcryptjs');
const user_jwt = require('../middleware/user_jwt');
const jwt = require('jsonwebtoken');
const { token } = require('morgan');

router.get('/users', user_jwt, async(req, res, next) => {
    try {
        const user = await User.findById(req.user.id).select('-password -username -__v');
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
    });

    if(!user) {
      res.status(400).json({
        success: false,
        msg: 'Something went wrong.'
      });
    }

    res.status.json({
      success: true,
      user: user,
      msg: 'Successfully updated'
    });

  } catch(error) {
    next(error);
  }
});

module.exports = router; //user.js 라는 파일이 모듈로써 동작하기 위해서는 이 파일을 밖으로 누구를 export할 수 있음.

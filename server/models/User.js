const mongoose = require('mongoose');

const likeListSchema = new mongoose.Schema({
  trailerId: {
    type: String,
    required: true
  },

  trailerName: {
    type: String,
    required: true
  },

  rentalPlace: {
    type: String,
    required: true
  },

  like: {
    type: Boolean,
    default: true
  },

  publishedDate: {
    type: Date,
    default: Date.now
  }
});

const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },

    email: {
        type: String,
        required: true
    },

    password: {
        type: String,
        required: true
    },

    nickname: {
      type: String,
      required: true
    },

    phone: {
      type: String,
      required: true
    },

    likeLists: [likeListSchema]
});

module.exports = mongoose.model('User', userSchema);

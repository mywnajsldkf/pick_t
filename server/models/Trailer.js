const mongoose = require('mongoose');

const trailerSchema = new mongoose.Schema({
  userId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  },

  publishedDate: {
    type: Date,
    default: Date.now
  },

  trailerPhoto: {
    type: String,
    required: true
  },

  trailerName: {
    type: String,
    required: true
  },

  license: {
    type: String,
    required: true
  },

  rentalPlace: {
    type: String,
    required: true
  },

  cost: {
    type: Number,
    required: true
  },

  capacity: {
    type: String,
    required: true
  },

  facilities: {
    type: String,
    required: true
  },

  description: {
    type: String,
    required: true
  }
});

module.exports = mongoose.model('Trailer', trailerSchema);

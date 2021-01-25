const mongoose = require('mongoose');

const trailerSchema = new mongoose.Schema({
  userId: {
    type: mongoose.SchemaTypes.ObjectId,
    ref: 'User'
  },

  reportingDate: {
    type: Date,
    default: Date.now
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

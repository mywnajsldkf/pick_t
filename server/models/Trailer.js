const mongoose = require('mongoose');

const trailerSchema = new mongoose.Schema({
    id: {
        type: String,
        required: true
    },

    trailer:[{
      trailerName: String,
      license: String,
      rentalPlace: String,
      capacity: String,
      facilities: String,
      description: String,
      required: true}]
});

module.exports = mongoose.model('Trailer', trailerSchema);

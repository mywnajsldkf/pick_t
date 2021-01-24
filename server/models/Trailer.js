const mongoose = require('mongoose');

const trailerSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },

    license:{
        type: String,
        required: true
    },

    rent: {
        type: String,
        required: true
    },

    cost: {
      type: String,
      required: true
    },

    num: {
      type: String,
      required: true
    },

    facility:{
        type: String,
        required: true
    }
});

module.exports = mongoose.model('Trailer', trailerSchema);
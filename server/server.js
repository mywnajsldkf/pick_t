const express = require('express')
const dotenv = require('dotenv')
const connectDB = require('./config/db')
const colors = require('colors')
const morgan = require('morgan')

const app = express();

app.use(morgan('dev'));

app.use(express.json({
    express: true
}))

// use dotenv file for DB connection
dotenv.config({
    path:'./config/config.env'
});

connectDB();

const PORT = process.env.PORT || 3001;
app.listen(PORT,
    console.log(`Server running mode on port ${PORT}`.red.underline.bold)
);
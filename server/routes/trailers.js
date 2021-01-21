var express = require('express');
var http = require('http');
var bodyParser= require('body-parser');
var app = express();

app.set('port',process.env.PORT || 3001);
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json());
var router = express.Router();

//첫 번째 미들웨어
app.use('api/pickt/trailer/register',function(req, res, next) {

    const trailer = {'name':'', 'license':'', 'rent':'', 'cost':'', 'num':'', 'facility':''}

    var name = req.body.name;
    var license = req.body.license;
    var rent = req.body.rent;
    var cost = req.body.cost;
    var num = req.body.num;
    var facility = req.body.facility;

    trailer.name = name;
    trailer.license = license;
    trailer.rent = rent;
    trailer.cost = cost;
    trailer.num = num;
    trailer.facility = facility;

    res.send(trailer);
});

var server = http.createServer(app).listen(app.get('port'),function(){
   console.log("서버 실행 : "+ app.get('port')); 
});

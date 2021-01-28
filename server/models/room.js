const mongoose=require('mongoose');

const{Schema}=mongoose;
const roomSchema=new Schema({
    roomNumber:{
        type:String,
        required:true,
    },
    owner:{
        type:String,
        required:true,
    },
    passsword:String,
    createdAt:{
        type:Date,
        default:Date.now,
    },
});

module.exports=mongoose.model('Room',roomSchema);
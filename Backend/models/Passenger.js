const mongoose = require("mongoose");

// Define the schema for the Passenger model
const passengerSchema = new mongoose.Schema({
  fName: {
    type: String,
    required: true,
  },
  lName: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
  },
  password: {
    type: String,
    required: true,
  },
  accountType: {
    type: String,
    required: true,
  },
  identifier: {
    type:String,
  }
});

// Create the Passenger model
const Passenger = mongoose.model("Passenger", passengerSchema);

module.exports = Passenger;

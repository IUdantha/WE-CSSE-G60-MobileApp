// models/Violation.js
const mongoose = require("mongoose");

const violationSchema = new mongoose.Schema({
  passengerName: String,
  identifier: String, // NIC/Passport
  busNumber: String,
  description: String,
  time: String,
  createdAt: {
    type: Date,
    default: Date.now,
  },
});

const Violation = mongoose.model("Violation", violationSchema);

module.exports = Violation;

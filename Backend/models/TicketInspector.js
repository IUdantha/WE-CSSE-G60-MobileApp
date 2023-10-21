const mongoose = require("mongoose");

// Define the schema for the TicketInspector model
const inspectorSchema = new mongoose.Schema({
  empID: {
    type: String,
    required: true,
  },
  fullName: {
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
});

// Create the TicketInspector model
const TicketInspector = mongoose.model("Ticket Inspector", inspectorSchema);

module.exports = TicketInspector;

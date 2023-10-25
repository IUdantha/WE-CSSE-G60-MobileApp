const router = require("express").Router();
let TicketInspector = require("../models/TicketInspector");
let Passenger = require("../models/Passenger");

// Handle POST request for checking user credentials
router.route("/").post(async (req, res) => {
  const { email, password } = req.body;
  console.log("Inside the check credentials router");
  try {
    const ticketInspector = await TicketInspector.findOne({ email, password });
    const passenger = await Passenger.findOne({ email, password });

    if (ticketInspector) {
      console.log("Valid Ticket Inspector");
      return res.json({ isValid: true, isPassenger: false, ticketInspector });
    }
    if (passenger) {
      console.log("Valid Passenger");
      return res.json({ isValid: true, isPassenger: true, passenger });
    }

    // If no matching user is found, return an invalid credentials response
    console.log("Invalid credentials");
    return res
      .status(401)
      .json({ isValid: false, status: "Invalid credentials" });
  } catch (error) {
    return res.status(500).json({
      status: "Error checking credentials",
      error: error.message,
    });
  }
});

module.exports = router;

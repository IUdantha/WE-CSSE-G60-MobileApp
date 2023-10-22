const router = require("express").Router();
let TicketInspector = require("../models/TicketInspector");
let Passenger = require("../models/Passenger");

// --------- Adding a Ticket Inspector ----------
router.route("/add").post((req, res) => {
  const empID = req.body.empID;
  const fullName = req.body.fullName;
  const email = req.body.email;
  const password = req.body.password;

  console.log("Inside the inspector router");

  const newTicketInspector = new TicketInspector({
    empID,
    fullName,
    email,
    password,
  });

  // js then = java if
  newTicketInspector
    .save()
    .then(() => {
      console.log("Ticket Inspector added to DB");
      res.json("Ticket Inspector Added");
    })
    .catch((err) => {
      console.log(err);
    });
});


// router.route("/get/:passengerId").get(async (req, res) => {
//     let passengerId = req.params.passengerId;

//     const passenger = await Passenger.findById(passengerId)
//       .then((passenger) => {
//         res.status(200).send({
//           status: "Passenger successfuly fetched",
//           passenger: passenger,
//         });
//       })
//       .catch((err) => {
//         console.log(err.message);
//         res
//           .status(500)
//           .send({ status: "Error with get passenger", error: err.message });
//       });
//   });

// Handle POST request for checking credentials
router.route("/checkCredentials").post(async (req, res) => {
  const { email, password } = req.body;
  console.log("Inside the check credentials router");
  try {
    // Query the database to find a passenger with the provided email and password
    const ticketInspector = await TicketInspector.findOne({ email, password });

    if (ticketInspector) {
      // Passenger with the provided credentials exists
      console.log("Valid credentials");
      res.json({ isValid: true, ticketInspector });
    } else {
      // No ticket inspector found with the provided credentials
      console.log("Invalid credentials");
      res.status(401).json({ isValid: false, status: "Invalid credentials" });
    }
  } catch (error) {
    res
      .status(500)
      .json({ status: "Error checking credentials", error: error.message });
  }
});

module.exports = router;

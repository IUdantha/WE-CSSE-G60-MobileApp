const router = require("express").Router();
let Passenger = require("../models/Passenger");
const Violation = require('../models/Violation');

// --------- Adding a passenger ----------
router.route("/add").post((req, res) => {
  const fName = req.body.fName;
  const lName = req.body.lName;
  const email = req.body.email;
  const password = req.body.password;
  const accountType = req.body.accountType;
  const identifier = req.body.identifier;

  console.log("Inside the router");

  const newPassenger = new Passenger({
    fName,
    lName,
    email,
    password,
    accountType,
    identifier,
  });

  // js then = java if
  newPassenger
    .save()
    .then(() => {
      console.log("Passenger added to DB");
      res.json("Passenger Added");
    })
    .catch((err) => {
      console.log(err);
    });
});

// ---------- Viewg all passengers -----------
router.route("/").get((req, res) => {
  //http://localhost:8060/student/
  Passenger.find()
    .then((passengers) => {
      res.json(passengers);
    })
    .catch((err) => {
      console.log(err);
    });
});

router.route("/get/:passengerId").get(async (req, res) => {
  let passengerId = req.params.passengerId;

  const passenger = await Passenger.findById(passengerId)
    .then((passenger) => {
      res.status(200).send({
        status: "Passenger successfuly fetched",
        passenger: passenger,
      });
    })
    .catch((err) => {
      console.log(err.message);
      res
        .status(500)
        .send({ status: "Error with get passenger", error: err.message });
    });
});

// Handle POST request for checking credentials
router.route("/checkCredentials").post(async (req, res) => {
  const { email, password } = req.body;
  console.log("Inside the check credentials router");
  try {
    // Query the database to find a passenger with the provided email and password
    const passenger = await Passenger.findOne({ email, password });

    if (passenger) {
      // Passenger with the provided credentials exists
      console.log("Valid credentials");
      res.json({ isValid: true, passenger });
    } else {
      // No passenger found with the provided credentials
      console.log("Invalid credentials");
      res.status(401).json({ isValid: false, status: "Invalid credentials" });
    }
  } catch (error) {
    res
      .status(500)
      .json({ status: "Error checking credentials", error: error.message });
  }
});

// Handle POST request for checking passenger violation
router.route("/checkViolation").post(async (req, res) => {
  const passengerID = req.body.passengerID;
  console.log("Checking Passenger Violation");

  try {
    const passenger = await Passenger.findById(passengerID);

    if (passenger) {
      console.log("Valid Passenger");
      return res.json({ isValid: true, passenger });
    }

    console.log("Violation Detected");
    return res.json({ isValid: false });
  } catch (error) {
    return res.status(500).json({ isValid: false , status: "Error checking Passenger violation", error: error.message });
  }
});


// Handle a POST request to store violation data
router.post('/violation', async (req, res) => {
  try {
    const {
      passengerName,
      identifier,
      busNumber,
      description,
      time,
    } = req.body;

    // Create a new Violation document
    const violation = new Violation({
      passengerName,
      identifier,
      busNumber,
      description,
      time,
    });

    // Save the violation document to the MongoDB collection
    await violation.save();

    res.status(201).json({ message: 'Violation data stored successfully' });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'An error occurred while storing the violation data' });
  }
});

module.exports = router;



module.exports = router;

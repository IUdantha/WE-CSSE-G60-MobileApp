const router = require("express").Router();
let Passenger = require("../models/Passenger");

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

// ---------- Viewg all student -----------
// router.route("/").get((req,res) => {    //http://localhost:8060/student/
//     Student.find().then((students) => {
//         res.json(students)
//     }).catch((err) => {
//         console.log(err)
//     })
// })

// // --------- Update a student ----------
// router.route("/update/:sid").put(async(req,res) => {    //http://localhost:8060/student/update/it21454882
//     let userId = req.params.sid;

//     // const name = req.body.name;
//     // const age = Number(req.body.age);
//     // const gender = req.body.gender;
//     const {name, age, gender} = req.body;    //using destructure method

//     const updateStudent = {
//         name,
//         age,
//         gender
//     }

//     const update = await Student.findByIdAndUpdate(userId, updateStudent).then(() => {
//         res.status(200).send({status: "user updated"});    //user: update   send update user to frontend
//     }).catch((err) => {
//         console.log(err);
//         res.status(500).send({status: "Error with updating data", error: err.message()});
//     })
// })

// // --------- Delete a student ----------
// router.route("/delete/:sid").delete(async(req,res) => {
//     let userId = req.params.sid;

//     await Student.findByIdAndDelete(userId).then(() => {
//         res.status(200).send({status: "User deleted"});
//     }).catch((err) => {
//         console.log(err.message);
//         res.status(500).send({status: "Error with delete user", error: err.message});
//         //500 internal error?
//     })
// })

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
      res.json({ status: "Credentials are correct", passenger });
    } else {
      // No passenger found with the provided credentials
      console.log("Invalid credentials");
      res.status(401).json({ status: "Invalid credentials" });
    }
  } catch (error) {
    res
      .status(500)
      .json({ status: "Error checking credentials", error: error.message });
  }
});

module.exports = router;

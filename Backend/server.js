const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const cors = require("cors");
const app = express();
require("dotenv").config();

const PORT = process.env.PORT || 8090;

// IP address
const serverIpAddress = "192.168.1.3";

app.use(cors());
app.use(bodyParser.json());

const URL = process.env.MONGODB_URL;

mongoose.connect(URL, {
  // useCreateIndex: true,
  useNewUrlParser: true,
  useUnifiedTopology: true,
  // useFindAndModify: false
});

const connection = mongoose.connection;

connection.once("open", () => {
  console.log("MongoDB connection success!");
});

app.listen(PORT, () => {
  console.log(
    `Server is up and running on,  ip Address:${serverIpAddress}: Port number: ${PORT}`
  );
});

// -----------------------------------------------
const passengerRouter = require("./routes/passengerRoutes.js");
app.use("/passenger", passengerRouter); //http://localhost:8060/passenger

const TicketInspectorRouter = require("./routes/ticketInspectorRoutes.js");
app.use("/ticketInspector", TicketInspectorRouter);

const loginRouter = require("./routes/loginRoute.js");
app.use("/login", loginRouter);


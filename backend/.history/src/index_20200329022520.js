const express = require('express');
const routes = requires('./routes');

const app = express();

app.use(express.json());
app.use(routes);

app.listen(3333);
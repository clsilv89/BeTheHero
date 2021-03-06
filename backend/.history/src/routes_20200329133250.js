const express = require('express');
const ongController = require('./controllers/ongController');
const incidentController = require('./controllers/incidentController');



const routes = express.Router();

routes.get('/ongs', ongController.index);

routes.post('/ongs', ongController.create);

routes.post('/incidents', incidentController.create);

module.exports = routes;

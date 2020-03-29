const express = require('express');
const ongController = require('./controllers/ongController');

const routes = express.Router();

routes.get('/ongs', async (request, response) => {
    
});

routes.post('/ongs', ongController.create);

module.exports = routes;
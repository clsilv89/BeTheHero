const express = require('express');

const routes = express.Router();

routes.post('/ongs', (request, response) => {
    const { name, email, whatsapp, city, UF } = request.body;

    console.log(params);

    return response.json();
});

module.exports = routes;
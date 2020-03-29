const express = require('express');

const routes = express.Router();

routes.post('/ongs', (request, response) => {
    const params = request.body;

    console.log(params);

    return response.json();
});

module.exports = routes;
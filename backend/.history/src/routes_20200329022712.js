const express = require('express');

const routes = express.Router();

routes.post('/users', (request, response) => {
    const params = request.body;

    console.log(params);

    return response.json({
        evento: 'Semana Omnistack',
        aluno: 'Caio'
    });
});

module.exports = routes;
const express = require('express');

const app = express();

app.post('/users', (request, response) => {
    const params = request.query;

    console.log(params);

    return response.json({
        evento: 'Semana Omnistack',
        aluno: 'Caio Luz da Silva'
    });
});

app.listen(3333);
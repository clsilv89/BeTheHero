const express = require('express');

const app = express();

app.use(express.json());

app.post('/users', (request, response) => {
    const params = request.body;

    console.log(params);

    return response.json({
        evento: 'Semana Omnistack',
        aluno: 'Caio'
    });
});

app.listen(3333);``
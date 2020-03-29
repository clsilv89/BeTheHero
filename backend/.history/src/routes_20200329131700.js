const express = require('express');
const ongController = require('./controllers/ongController');

const routes = express.Router();

routes.get('/ongs', async (request, response) => {
    const ongs = await connection('ongs').select('*');
    
    return response.json(ongs);
})

routes.post('/ongs', async (request, response) => {
    
});

module.exports = routes;
const connection = require('../database/connection');

module.exports = {
    async create(request, response) {
        const { id } = request.body;

        const ong = await connection('ongs')
        .where('id', id)
        .select( 'name')
        .first();

        if(!ong) {
            return response.status(400).json({
                error: 'No ONG foung with this ID'
            })
        }
        return reponse.json(ong);
    }
};
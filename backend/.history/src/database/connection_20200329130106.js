const knex = require("knex");
const configuration = require("../../kknexfile");

const connection = knex(configuration.development);

module.exports = connection;
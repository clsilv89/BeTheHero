const connection = require("../database/connection");
const crypto = require("crypto");

module.exports = {
  async create(request, response) {
    const { name, email, whatsapp, city, UF } = request.body;

    const id = crypto.randomBytes(4).toString("HEX");

    await connection("ongs").insert({
      id,
      name,
      email,
      whatsapp,
      city,
      UF
    });

    return response.json({ id });
  }
};

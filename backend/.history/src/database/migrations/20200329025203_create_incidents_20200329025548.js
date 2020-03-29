
exports.up = function(knex) {
    return knex.schema.createTable('incidents', function (table) {
        table.increments();
        table.string('title').notNullable();
        table.string('desciption').notNullable();
        table.decimal('value`').notNullable();
    });
};

exports.down = function(knex) {
  
};

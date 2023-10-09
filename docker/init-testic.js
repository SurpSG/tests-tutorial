db.createCollection('users');

var usersData = [
    {
        name: 'test',
        salary: 1000.0,
        department: 'IT',
    }
];

db.users.insertMany(usersData);

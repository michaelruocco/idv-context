db.createUser(
    {
        user: "idv",
        pwd: "welcome01",
        roles: [
            {
                role: "readWrite",
                db: "idv"
            }
        ]
    }
);
db.createCollection("identity");
db.identity.createIndex({ "aliases.value": 1, "aliases.type": 1 }, { unique: true });
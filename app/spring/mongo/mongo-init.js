db.createUser(
    {
        user: "idv",
        pwd: "welcome01",
        roles: [
            {
                role: "readWrite",
                db: "idv-local"
            }
        ]
    }
);

db.createCollection("context");
db.context.createIndex({ "timestamp": 1 }, { expireAfterSeconds: 360 });

db.createCollection("attempt");

db.createCollection("identity");
db.identity.createIndex({ "aliases.value": 1, "aliases.type": 1 }, { unique: true });


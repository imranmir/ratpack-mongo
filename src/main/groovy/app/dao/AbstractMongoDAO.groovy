package app.dao

import com.mongodb.DBCollection
import com.mongodb.Mongo

abstract class AbstractMongoDAO {
    abstract String getCollectionName();

    DBCollection coll

    public AbstractMongoDAO(Mongo mongo, String database) {
        def db = mongo.getDB(database)
        coll = db.getCollection(getCollectionName())
    }
}

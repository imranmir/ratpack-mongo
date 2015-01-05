package app.dao

import com.mongodb.BasicDBObject
import com.mongodb.DBCursor
import com.mongodb.Mongo
import com.mongodb.util.JSON
import groovy.stream.Stream
import org.bson.types.ObjectId
import static groovy.json.JsonOutput.toJson
import app.domain.Person

class PeopleDAO extends AbstractMongoDAO {

    PeopleDAO(Mongo mongo, String database) {
        super(mongo, database)
    }

    List<String> getAll(List<String> fields) {
        BasicDBObject fieldsObj = fields.inject([:]) { obj, String name ->
            if (!obj) obj = new BasicDBObject(name, 1)
            else ((BasicDBObject)obj).append(name, 1)
            obj
        }

        def curs = coll.find(new BasicDBObject(), fieldsObj)
        def s = Stream.from curs map { item -> JSON.serialize(item) }
        s.collect()
    }

    String getPerson(String id) {
        BasicDBObject obj = [_id: new ObjectId(id)] as BasicDBObject
        JSON.serialize(coll.findOne(obj))
    }

    String findPersonById(String id){
        DBCursor x = coll.find(new BasicDBObject("id", id))
//        BasicDBObject b = x.toArray().get(0)
//        Person p = new Person(b)
//        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>"+JSON.serialize(p.toMap())
        JSON.serialize(coll.find(new BasicDBObject("id", id)))
    }

    BasicDBObject save(Person person) {
        String json = toJson(person.toMap())
        def obj = JSON.parse(json) as BasicDBObject
        coll.insert obj
        obj
    }

    void delete(String id){
        coll.remove(new BasicDBObject("id", id))
    }

    @Override
    String getCollectionName() {
        'people'
    }
}

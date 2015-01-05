package app.domain

import com.mongodb.BasicDBObject
import ratpack.form.Form

class Person {
    String id
    String _id
    String firstName
    String lastName

    Map toMap() {
        return [firstName: firstName, lastName: lastName, id: id, _id: _id]
    }

    Person() {}

    Person(Form form) {
        firstName = form.get('firstName')
        lastName = form.get('lastName')
        id = form.get('id')
    }

    Person(BasicDBObject dbObject) {
        firstName = dbObject.get('firstName')
        lastName = dbObject.get('lastName')
        id = dbObject.get('id')
        _id = dbObject.get('_id')
    }
}

import app.dao.PeopleDAO
import app.modules.DAOModule
import app.domain.Person
import ratpack.form.Form

import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson
import static com.mongodb.util.JSON.serialize
import ratpack.jackson.JacksonModule

ratpack {
    bindings {
        add new DAOModule(new File('./config', 'Config.groovy'))
        add new JacksonModule()
    }

    handlers {
        handler("api/people") {
            byMethod {
                post { PeopleDAO dao ->
                    Form form = parse(Form.class)
                    byContent {
                        json {
                            //parse jsonNode() for parsing input json
                            response.send serialize(dao.save(new Person(form)))
                        }
                    }
                }

                get { PeopleDAO dao ->
                    response.send toJson(dao.getAll(['firstName', 'lastName', '_id', 'id']))
                }
            }
        }

        handler("api/people/:id") {
            byMethod {
                get { PeopleDAO dao ->
                    def id = pathTokens.id
                    def pers = dao.findPersonById(id)
                    if (!pers) {
                        clientError 404
                        return
                    } else {
                        response.send pers
                    }
                }

                delete { PeopleDAO dao ->
                    def id = pathTokens.id
                    dao.delete(id)
                    response.send "Deletion Success"

                }
            }
        }

        handler("") {
            byMethod {
                get {
                    render groovyTemplate('hello.html', title: 'my title')   //todo this does not work
                }
            }
        }


    }
}

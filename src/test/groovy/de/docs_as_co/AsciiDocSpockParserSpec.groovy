package de.docs_as_co

import spock.lang.Narrative
import spock.lang.Specification

@Narrative("""\
The AsciiDocSpockParser is able to parse an AsciiDoc file and extract Specificaions written in a given syntax.
""")
class AsciiDocSpockParserSpec extends Specification {

    def "Parse File"() {
        given: "an AsciiDoc document"
            def doc = getClass().getResource('/testData/Specification.adoc').text
        when: "the parser is invoked"
            def result = AsciiDocSpockParser.parse(doc)
        then: "we've got one spec"
            result.specs.size() == 1
        and: "its name is 'Login'"
            'Login' in result.specs.keySet()
        and: "the narrative has two lines"
            result.specs.Login.narrative.size() == 2
        and: "narrative starts with 'This is'"
            result.specs.Login.narrative[0].startsWith("This is")
        and: "the spec has two features"
            result.specs.Login.features.size() == 2
        and: "Features are called 'Successful login' and 'Unsuccessful login'"
            'Successful login'   == result.specs.Login.features.keySet()[0]
            'Successful login'   in result.specs.Login.features.keySet()
            'Unsuccessful login' in result.specs.Login.features.keySet()
        and: "each feature contains three blocks"
            result.specs.Login.features['Successful login'].blocks.size()==4
            result.specs.Login.features['Unsuccessful login'].blocks.size()==4
        and: "blocks entry equals predefined list"
            result.specs.Login.features['Successful login'].blocks[0] == ['given', 'User is on the login page']
            result.specs.Login.features['Successful login'].blocks[1] == ['when', 'User enters his credentials and clicks \'login\'-button']
            result.specs.Login.features['Successful login'].blocks[2] == ['then', 'The Overview-Screen appears']
            result.specs.Login.features['Unsuccessful login'].blocks[0] == ['given', 'User is on the login page']
            result.specs.Login.features['Unsuccessful login'].blocks[1] == ['when', 'User enters his credentials and clicks \'login\'-button']
            result.specs.Login.features['Unsuccessful login'].blocks[2] == ['then', 'The Overview-Screen appears']
        and: "last block is 'where'-block"
            result.specs.Login.features['Unsuccessful login'].blocks[3][0] == 'where'
        and: "where block has 2 and 1 elements"
            result.specs.Login.features['Successful login'].blocks[3][1].size() == 2
            result.specs.Login.features['Unsuccessful login'].blocks[3][1].size() == 1
    }
}

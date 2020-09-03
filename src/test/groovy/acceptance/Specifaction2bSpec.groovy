package acceptance

import spock.lang.Ignore
import spock.lang.Narrative
import spock.lang.Unroll
import geb.spock.GebReportingSpec

@Narrative("""
This test uses the calculator feature of Google
to showcase the Geb-Reports
""")
class Specification2bSpec extends GebReportingSpec {

    @Ignore("not implemented")
    def "User calls Calculator"() {
        given: "User is on the main page"
        when: "User enters 'calc' as search-term and submits the form"
        then: "the search result displays a calculator"
    }

    @Unroll()
    @Ignore("not implemented")
    def "User does a calculation"() {
        given: "User is on the main page"
        when: "User enters a `calculation` as search-term and submits the form"
        then: "the search result displays the `result` within the calculator"

        where:
            calculation | result
            '3+4'       | 7
            '6*7'       | 42
    }
}
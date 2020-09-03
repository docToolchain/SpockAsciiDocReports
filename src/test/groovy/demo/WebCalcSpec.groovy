package demo

import org.openqa.selenium.Keys
import spock.lang.Ignore
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Unroll
import geb.Page
import geb.spock.GebReportingSpec

class BingHomePage extends Page {
    static url = "https://www.bing.com"

    static at = {
        title ==~ /.*Bing.*/
    }

    static content = {
        form() {$("form#sb_form")}
        searchBtn(){$("input#sb_form_go")}
    }

}
class BingSearchResultPage extends Page {
    static url = "https://www.bing.com/search"

    static at = {
        title ==~ /.*Bing.*/
    }
}
@Narrative("""
This test uses the calculator feature of Google
to showcase the Geb-Reports
""")
class WebCalcSpec extends GebReportingSpec {

    @Issue("issue-a")
    def "Bing-Rechner aufrufen"() {
        when: "die Bing Homepage aufgerufen wird"
            to BingHomePage
        then: "wird die Bing Homepage angezeigt"
            report "Bing Home Page"
            at BingHomePage
        when: "nach 'calc' gesucht wird"
            form.q = 'calc'
            sleep(200)
            form.q << Keys.ENTER
        then: "erscheint die Suchresultseite"
            report "Bing Search Result Page"
            at BingSearchResultPage
    }
    // tag::test2[]
    @Unroll()
    def "Rechner kann addieren : data driven"() {
        setup: "gehe auf die Google Homepage"
            to BingHomePage
            report "Bing Homepage"
            at BingHomePage
        when: "'calc #arg1 + #arg2' ins Suchfeld eingegeben wird"
            form.q = "calc $arg1 + $arg2"
            sleep(200)
            form.q << Keys.ENTER
        then: "dann erscheint im Calculator #erwartetesErgebnis"
            report "result $arg1 + $arg2"
            $('span#rcTB').text() as Integer == erwartetesErgebnis

        where:
            arg1 | arg2 || erwartetesErgebnis
            3   |  4   ||  7
            4   |  3   ||  7
            -5   |  7   ||  2
            -7   |  5   || -2
    }
    def "Rechner kann addieren"() {
        setup: "gehe auf die Google Homepage"
            to BingHomePage
            report "Bing Homepage"
            at BingHomePage
        when: "'calc 8 + 9' ins Suchfeld eingegeben wird"
            form.q = "calc 8 + 9"
            sleep(200)
            form.q << Keys.ENTER
        then: "dann erscheint im Calculator 17"
            report "result 17"
            $('span#rcTB').text() as Integer == 17

    }

    // end::test2[]

}

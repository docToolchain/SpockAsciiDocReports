/*
* This Spock specification was generated by the Gradle 'init' task.
*/
package demo

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll
import geb.Page
import geb.spock.GebSpec

class GoogleHomePage extends Page {
    static url = "http://www.google.com"
}

class GebTestSpec extends GebSpec {

    def "go method does NOT set the page"() {
        given:
            Page oldPage = page

        when:
            go "http://www.google.com"
            report "Google Main page"
        then:
            oldPage == page
            currentUrl.contains "google"
    }
}
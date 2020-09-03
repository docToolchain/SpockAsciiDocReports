package demo


import spock.lang.Ignore
import spock.lang.Specification

import static org.junit.Assert.*

class Calculator {
    def clear() {}
    BigDecimal plus( def a, def b) {
        return a + b
    }
}
class TaschencalculatorSpec extends Specification {

    void testeAdditionJUnit() {
        Calculator calculator = new Calculator()
        BigDecimal result = calculator.plus( 3, 4)
        assertEquals(7, result, 0.1)
    }

    void "Rechner kann addieren Spock I"() {
        given:
            Calculator calculator = new Calculator()
        when:
            BigDecimal result = calculator.plus( 3, 4)
        then:
            assertEquals(7, result, 0.1)
    }

    void "Rechner kann addieren Spock II"() {
        given: "ein Caclulator Objekt"
            Calculator calculator = new Calculator()
        when: "zwei Zahlen addiert werden"
            BigDecimal result = calculator.plus( 3, 4)
        then: "erhalte ich das richtige Ergebnis"
            assertEquals(7, result, 0.1)
    }

    void "Rechner kann addieren Spock III"() {
        given: "ein Caclulator Objekt"
            Calculator calculator = new Calculator()
        when: "#a und #b addiert werden"
            BigDecimal result = calculator.plus( a, b)
        then: "erhalte ich #expected"
            assertEquals(expected, result, 0.1)
        where: "Beispieldaten"
             a   |   b   || expected
             3   |   4   ||  7
             4   |   3   ||  7
            -5   |   7   ||  2
            -7   |   5   || -2
    }

    void "Rechner kann addieren Spock IV"() {
        given: "ein Caclulator Objekt"
            def calculator = new Calculator()
        when: "#a und #b addiert werden"
            def result = calculator.plus( a, b)
        then: "erhalte ich #expected"
            result == expected
        where: "Beispieldaten"
             a   |   b   || expected
             3   |   4   ||  7
             4   |   3   ||  7
            -5   |   7   ||  2
            -7   |   5   || -2
    }

    // tag::test1[]
    def "Rechner kann addieren"() {
        setup: "neuen Calculator instanziieren"
            Calculator calculator = new Calculator()

        when: "3 + 4 eingegeben wird"
            BigDecimal result = calculator.plus( 3, 4)

        then: "dann ist das Ergebnis 7"
            result == 7
    }

    // end::test1[]

    // tag::test2[]
    //@Unroll()
    def "Rechner kann addieren : data driven"() {
        setup: "neuen Calculator instanziieren"
            Calculator calculator = new Calculator()

        when: "#arg1 + #arg2 eingegeben wird"
            BigDecimal result = calculator.plus( arg1, arg2)

        then: "dann ist das Ergebnis #erwartetesErgebnis"
            result == erwartetesErgebnis

        where:
            arg1 | arg2 || erwartetesErgebnis
             3   |  4   ||  7
             4   |  3   ||  7
            -5   |  7   ||  2
            -7   |  5   || -2
    }

    // end::test2[]

    // tag::test3[]
    @Ignore("noch nicht implementiert")
    def "Rechner kann subtrahieren"() {
        expect:
            1 == 2
    }

    // end::test3[]

    @Ignore("noch nicht implementiert")
    def "Rechner kann multiplizieren"() {
        expect:
            1 == 2
    }

    @Ignore("noch nicht implementiert")
    def "Rechner kann dividieren"() {
        expect:
            1 == 2
    }

}

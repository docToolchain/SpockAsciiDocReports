= Spock Test Results
// toc-title definition MUST follow document title without blank line!
:toc-title: Table of Contents
:toclevels: 4

//add some additional styles which make the test-report a bit nicer
++++
<style>
    div.PASS {
        background-color: #DFD;
    }
    div.PASS pre {
        background-color: #EFE !important;
    }
    div.FAIL {
        background-color: #FDD;
        border: 2px solid red;
    }
    div.FAIL pre {
        background-color: #FEE !important;
    }
    div.IGNORED {
        background-color: #DDD;
        border: none;
        color: #AAA;
    }
</style>
++++

// numbering from here on
:numbered:

<% try { %>

<% def stats = com.athaydes.spockframework.report.util.Utils.aggregateStats( data ) %>

== Specification run results

=== Specifications summary

[small]#created on ${new Date()} by ${System.properties['user.name']}#

.summary
[options="header"]
|==================================================================================================================================
| Total          | Passed          | Failed          | Feature failures | Feature errors   | Success rate        | Total time (ms)
| ${stats.total} | ${stats.passed} | ${stats.failed} | ${stats.fFails}  | ${stats.fErrors} | ${stats.successRate}| ${stats.time}
|==================================================================================================================================

=== Specifications

[options="header"]
|===================================================================
|Name  | Features | Failed | Errors | Skipped | Success rate | Time
<% data.each { name, map ->
    %>| $name | ${map.totalRuns} | ${map.failures} | ${map.errors} | ${map.skipped} | ${map.successRate} | ${map.time} | ${map.dump()}
<% } %>
|===================================================================


<% data.each { name, map -> %>

<<<<
//tag::${name.replaceAll('[^a-zA-Z0-9]','')}[]
include::${name}.adoc[]
//end::${name.replaceAll('[^a-zA-Z0-9]','')}[]

<% } %>

[small]#generated with ${com.athaydes.spockframework.report.SpockReportExtension.PROJECT_URL}[Athaydes Spock Reports]#

<% } catch (Exception e) { out << e } %>
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
    div.collapsed {
        height: 0px;
        padding-top: 4px;
        padding-bottom: 4px;
        overflow: hidden;
    }
    div.collapsed:before {
        content: 'test';
    }
    div.PASS pre {
        background-color: #EFE !important;
    }
    a.PASS {
        color: #080;
    }
    div.FAIL, div.ERROR, div.FAILURE {
        background-color: #FDD;
        border: 2px solid red;
    }
    a.FAIL, a.ERROR, a.FAILURE {
        color: #A00;
    }
    div.FAIL pre, div.ERROR pre, div.FAILURE pre {
        background-color: #FEE !important;
    }
    div.IGNORED {
        background-color: #DDD;
        border: none;
        color: #AAA;
    }
    a.IGNORED {
        color: #AAA;
    }
    div.PASS p, div.FAIL p, div.IGNORED p, div.ERROR p, div.FAILURE p {
        margin: 0;
    }
    div.PASS p, div.FAIL p, div.IGNORED p, div.ERROR p, div.FAILURE p {
        margin: 0;
    }
    div.PASS, div.FAIL, div.IGNORED, div.ERROR, div.FAILURE {
        padding: 3px 5px;
    }
    div.PASS pre, div.FAIL pre, div.IGNORED pre, div.ERROR pre, div.FAILURE pre {
        padding: 3px 5px !important;
        margin: 0 0 0 15px !important;
    }
    div.PASS div.listingblock, div.FAIL div.listingblock, div.IGNORED div.listingblock div.ERROR div.listingblock div.FAILURE div.listingblock {
        margin: 0;
    }
    div.PASS div.imageblock, div.FAIL div.imageblock, div.IGNORED div.imageblock, div.ERROR div.imageblock, div.FAILURE div.imageblock {
        margin: 0;
    }
    div.PASS table, div.FAIL table, div.IGNORED table, div.ERROR table, div.FAILURE table {
        margin: 0 !important;
    }
    div.PASS td, div.FAIL td, div.IGNORED td, div.ERROR td, div.FAILURE td {
        padding: 2px 6px;
    }
    div.PASS th, div.FAIL th, div.IGNORED th, div.ERROR th, div.FAILURE th {
        padding: 2px 6px;
    }

</style>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // re-style TOC
        var tocLabels = document.querySelectorAll('#toc a');

        tocLabels.forEach(label => {
            if (label.text.includes("IGNORED:")) {
                label.classList.add('IGNORED');
            };
            if (label.text.includes("FAIL:")) {
                label.classList.add('FAIL');
            };
            if (label.text.includes("FAILURE:")) {
                label.classList.add('FAIL');
            };
            if (label.text.includes("ERROR:")) {
                label.classList.add('FAIL');
            };
            if (label.text.includes("PASS:")) {
                label.classList.add('PASS');
            };
        });

        // collapse passed tests
        var passedTests = document.querySelectorAll('div.PASS');
        passedTests.forEach(test => {
            test.classList.add('collapsed');
            test.addEventListener("click", function(){ this.classList.toggle('collapsed'); }, false);
        })

    });
</script>

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
| Total          | Passed          | Failed          | Feature failures | Feature errors   | Success rate         | Total time (ms)
| ${stats.total} | ${stats.passed} | ${stats.failed} | ${stats.fFails}  | ${stats.fErrors} | ${stats.successRate}%| ${stats.time}
|==================================================================================================================================

=== Specifications

[options="header"]
|===================================================================
| Name  | Features | Failed | Errors | Skipped | Success rate | Time
<% data.each { name, map ->
    %>| $name | ${map.stats.totalRuns} | ${map.stats.failures} | ${map.stats.errors} | ${map.stats.skipped} | ${map.stats.successRate}% | ${map.stats.time}
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
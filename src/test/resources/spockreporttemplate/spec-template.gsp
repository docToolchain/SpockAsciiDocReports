<%
    def fmt = new com.athaydes.spockframework.report.internal.StringFormatHelper()
    def file = data.info.pkg.replaceAll("[.]","/")+"/"+data.info.filename
    def classname = data.info.pkg+"."+data.info.filename
    def stats = com.athaydes.spockframework.report.util.Utils.stats( data )
%>== Report for ${data.info.description.className}

=== Summary

[options="header",cols="asciidoc,asciidoc"]
|====
|Total Runs        |Success Rate 													 |Total time												|Failures					 |Errors					|Skipped
|${stats.totalRuns}|${fmt.toPercentage(stats.successRate)} |${fmt.toTimeDuration(stats.time)} |${stats.failures} |${stats.errors} |${stats.skipped}
|====

{gitwebpath}${file}[${file}]

=== Features
<%
    features.eachFeature { name, result, blocks, iterations, params ->
%>

==== $result: $name

//feature start

//fail or success
[role="$result"]
****

//given, when, then blocks with code
<%
for ( block in blocks ) {
%>
${block.kind} **${block.text}** +
<% if (block.sourceCode) { %>
[source, groovy]
----
${block.sourceCode.join('\n')}
----
<% } //if %>
<%
} // for
%>

//where-block as table
<%
    def executedIterations = iterations.findAll { it.dataValues || it.errors }
    if ( params && executedIterations ) {
 %>
[options="header"]
|====
| ${params.join( ' | ' )} |
<%
    for ( iteration in executedIterations ) {
%>| ${iteration.dataValues.join( ' | ' )} | ${iteration.errors ? '(FAIL)' : '(PASS)'}
<%  }
%>|====


<%  } // if
%>

//error output
<%
        def problems = executedIterations.findAll { it.errors }
        if ( problems ) {
            out << "\n[IMPORTANT]\n.The following Error occured\n====\n"
            for ( badIteration in problems ) {
                if ( badIteration.dataValues ) {
                    out << '* ' << badIteration.dataValues << '\n'
                }
                for ( error in badIteration.errors ) {
                    out << '----\n' << error << '\n----\n'
                }
            }
            out << "====\n"
        }
 %>
****

//next feature
 <%
    }
 %>

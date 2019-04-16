<%
    def fmt = new com.athaydes.spockframework.report.internal.StringFormatHelper()
    def file = data.info.pkg.replaceAll("[.]","/")+"/"+data.info.filename
    def classname = data.info.pkg+"."+data.info.filename
    def stats = com.athaydes.spockframework.report.util.Utils.stats( data )
    def gebUtils, gebReport, specReport
    try {
        def c = Class.forName("com.aoe.gebspockreports.GebReportUtils")
        if(c) {
            gebUtils = c.newInstance()

            gebReport = gebUtils.readGebReport()
            specReport = gebReport.findSpecByLabel(utils.getSpecClassName(data))
        }
    } catch (Exception e) {
        //it seems that GebSpockReports is not installed
    }
%>== Report for ${data.info.description.className}

<%
def narrative = (data.info.narrative ?: '')
%>
${narrative}

=== Summary
[options="header",cols="asciidoc,asciidoc"]
|====
|Total Runs        |Success Rate 													 |Total time												|Failures					 |Errors					|Skipped
|${stats.totalRuns}|${fmt.toPercentage(stats.successRate)} |${fmt.toTimeDuration(stats.time)} |${stats.failures} |${stats.errors} |${stats.skipped}
|====

{gitwebpath}${file}[${file}]

=== Features
<%
    def featureCount = 0

    features.eachFeature { name, result, blocks, iterations, params ->
        def isIgnored = result == 'IGNORED'
        featureCount += isIgnored ? 0 : 1
        def gebFeatureReport = specReport?.findFeatureByNumberAndName(featureCount, name)
        def gebArtifacts = gebFeatureReport?.artifacts
%>

==== $result: $name

<%
	def whyIgnored = description.getAnnotation(spock.lang.Ignore)?.value()
        out << (whyIgnored?:'')

%>

//feature start

//fail or success
[role="$result"]
****

//given, when, then blocks with code
<%
for ( block in blocks ) {
%>
${block.kind} **${block.text?:' - '}** +
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


<%  } // if %>



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

<% if (gebArtifacts) { %>

.Screenshots:
[cols="a,a,a,a"]
|====

<% gebArtifacts.sort { it.number }.eachWithIndex { artifact, i ->
        def label = artifact.label?.replaceFirst(name+"-", '')
        def trCssClass = label.endsWith('-failure') ? 'geb-failure' : ''
        def imageFile = "./" + artifact.files.find { it.endsWith('png') }
        def domSnapshotFile = "./" + artifact.files.find { it.endsWith('html') }
        %>
|
.${label}
image::${imageFile.replaceAll(" ","%20").replaceAll('\\\\','/')}[screenshot $label]
//link:${domSnapshotFile.replaceAll(" ","%20")}[$label]
        <%
        }
//fill remaining cells
(gebArtifacts.size()%4).times {
     out << "| \r\n"
}
%>
|====
<% } //if (gebArtifacts)%>

****

//next feature
 <%
    }
 %>

<%
    def unassignedArtifacts = specReport?.getUnassignedGebArtifacts()
    if (unassignedArtifacts) {
%>

== Unassigned Geb Artifacts

The following artifacts could not be mapped to a feature.

++++

<div class="geb-artifacts">
    juhu!
    <table>
        <thead>
        <tr>
            <th>Label</th>
            <th>Image</th>
            <th>Html</th>
            <th>Page object</th>
        </tr>
        </thead>
        <tbody>
        <% unassignedArtifacts.forEach { artifact ->
            def label = artifact.label
            def imageFile = "./" + artifact.files.find { it.endsWith('png') }
            def domSnapshotFile = "./" + artifact.files.find { it.endsWith('html') }
        %>
        <tr>
            <td>$label</td>
            <td><a href="$imageFile">png</a></td>
            <td><a href="$domSnapshotFile">html</a></td>
            <td>${artifact.pageObject}</td>
        </tr>
        <%
                } %>
        </tbody>
    </table>
</div>

++++

<% } %>
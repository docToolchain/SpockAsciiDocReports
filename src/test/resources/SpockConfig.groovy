spockReports {
    set 'com.athaydes.spockframework.report.showCodeBlocks': true
    set 'com.athaydes.spockframework.report.outputDir': 'build/spock-reports'

    set 'com.athaydes.spockframework.report.IReportCreator' : 'com.athaydes.spockframework.report.template.TemplateReportCreator'

    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.specTemplateFile' : '/spockreporttemplate/spec-template.gsp'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.reportFileExtension' : 'adoc'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryTemplateFile' : '/spockreporttemplate/summary.gsp'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryFileName' : 'summary.adoc'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.enabled' : true
}
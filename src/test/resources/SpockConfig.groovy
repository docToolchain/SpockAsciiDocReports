spockReports {
    set 'com.athaydes.spockframework.report.showCodeBlocks': true
    set 'com.athaydes.spockframework.report.outputDir': 'build/spock-reports'

    set 'com.athaydes.spockframework.report.IReportCreator' : 'com.athaydes.spockframework.report.template.TemplateReportCreator'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.specTemplateFile' : '/spockreporttemplate/spec-template.gsp'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.reportFileExtension' : 'adoc'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryTemplateFile' : '/spockreporttemplate/summary.gsp'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryFileName' : 'summary.adoc'
/**
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.specTemplateFile' : '/templates/myspec-template.html'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.reportFileExtension' : 'html'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryTemplateFile' : '/templates/summary-template.html'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryFileName' : 'index.html'

 **/

    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.enabled' : true
}
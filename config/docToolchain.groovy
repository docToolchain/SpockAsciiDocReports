outputPath = 'build/docs'

// Path where the docToolchain will search for the input files.
// This path is appended to the docDir property specified in gradle.properties
// or in the command line, and therefore must be relative to it.
inputPath = 'src/docs'

inputFiles = [
              [file: 'tutorial.adoc',            formats: ['html','pdf']],
              [file: 'spock-reports.adoc',       formats: ['html','pdf']],
             ]

taskInputsDirs = ["${inputPath}/images"]

taskInputsFiles = []

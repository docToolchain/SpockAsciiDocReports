package de.docs_as_co

class AsciiDocSpockParser {

    static Map parse( def doc ) {
        Map result = [specs:[:]]

        String state = "searchSpec"
        String blockMarker = ""
        String specName = ""
        String featureName = ""
        String featureBlockMarker = ""

        doc.eachLine { String line ->
            switch (state) {
                case "searchSpec":
                    if (line.toLowerCase().startsWith('.spec:')) {
                        specName = line[7..-1].trim()
                        if (result.specs[specName]) {

                        } else {
                            result.specs[specName] = [ narrative: [], features: [:]]
                        }
                        state = "getBlockMarker"
                    }
                    break

                case "getBlockMarker":
                    blockMarker = line
                    state = "readNarrative"
                    break

                case "readNarrative":
                    if (line.startsWith('.')) {
                        featureName = line[1..-1].trim()
                        state = "getFeatureBlockMarker"
                        result.specs[specName].features[featureName] = [blocks: []]
                    } else if (line) {
                        result.specs[specName].narrative << line
                    }
                    break

                case "getFeatureBlockMarker":
                    featureBlockMarker = line
                    state = "readFeature"
                    break

                case "readFeature":
                    if (line.trim() == featureBlockMarker) {
                        state = "findFeatureOrSpecEnd"
                    } else {
                        if (line.toLowerCase() ==~ /([|] *)(given|when|then|expect|and).*/) {
                            def block = line.split("[|]")[1..2]
                            block[0] = block[0].toLowerCase().trim()
                            block[1] = block[1].trim()
                            result.specs[specName].features[featureName].blocks << block
                        }
                        if (line.toLowerCase() ==~ /(given|when|then|expect|and)::.*/) {
                            def block = line.split("::")
                            block[0] = block[0].toLowerCase().trim()
                            block[1] = block[1].trim()
                            result.specs[specName].features[featureName].blocks << block
                        }
                        if (line.toLowerCase() ==~ /[.]where/) {
                            def block = ['where',[]]
                            result.specs[specName].features[featureName].blocks << block
                            state = "readWhereTableMarker"
                        }
                    }
                    break

                case "readWhereTableMarker":
                    assert line.startsWith("|===")
                    state = "readWhereTable"
                    break

                case "readWhereTable":
                    if (line.startsWith("|===")) {
                        state = "readFeature"
                    } else {
                        if (line) {
                            def row = line.split("[|]")[1..-1]
                            result.specs[specName].features[featureName].blocks[-1][1] << row
                        }
                    }
                case "findFeatureOrSpecEnd":
                    if (line.startsWith('.')) {
                        featureName = line[1..-1]
                        state = "getFeatureBlockMarker"
                    }
                    if (line.trim()==blockMarker) {
                        state = "searchSpec"
                    }
                    break

            }
        }
        /**
                specs: [
                        'Login': [
                                'narrative': "This is the narrative. Let's see where this will lead to.\nCan I use **bold**?",
                                'features': [
                                        [
                                                'name': 'test',
                                                'blocks': [
                                                        'given': 'User is on the login page',
                                                        'when': 'User enters his credentials and clicks \'login\'-button',
                                                        'then': 'The Overview-Screen appears'
                                                ]
                                        ],
                                        [
                                                'name': 'test',
                                                'blocks': [
                                                        'given': 'User is on the login page',
                                                        'when': 'User enters his credentials and clicks \'login\'-button',
                                                        'then': 'The Overview-Screen appears'
                                                ]
                                        ]
                                ]
                        ]
                ]
        ]
         **/
        return result
    }
}

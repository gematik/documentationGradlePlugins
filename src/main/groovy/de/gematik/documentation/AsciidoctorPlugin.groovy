/*
 * Copyright (c) 2019 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.gematik.documentation


import org.gradle.api.Project
import org.gradle.api.logging.StandardOutputListener

/**
 * Generates the Asciidoc documentation with the specific configured Settings
 *
 */
class AsciidoctorPlugin extends DocumentationPlugin {

    public static final String VERSION_ASCIIDOCTORJ = '1.6.2'

    @Override
    void apply(final Project project) {
        super.apply(project)
        project.getPluginManager().apply("org.asciidoctor.convert")
        project.tasks.asciidoctor.dependsOn(project.tasks.copyPlantumlsToDocDir)
        project.tasks.asciidoctor.doFirst {
            project.tasks.copyPlantumlsToDocDir
        }
        project.extensions.getByName("asciidoctorj").version = VERSION_ASCIIDOCTORJ

        project.afterEvaluate { p ->
            project.asciidoctor {
                def gematikAsciidoctor = project.extensions.findByType(DocumentationPluginExtension)
                def asciidocRootSourceDir = 'doc'
                if (gematikAsciidoctor.asciidocRootSourceDir != null)
                    asciidocRootSourceDir = gematikAsciidoctor.asciidocRootSourceDir
                def asciidocDestinationFolder = project.buildDir.path + '/documentation'
                if (gematikAsciidoctor.asciidocDestinationFolder != null)
                    asciidocDestinationFolder = gematikAsciidoctor.asciidocDestinationFolder
                println "AsciidoctorPlugin use asciidoctorj: " + project.extensions.getByName("asciidoctorj").version

                dependsOn project.tasks.jrubyPrepare

                requires = ['asciidoctor-diagram']
                gemPath = project.tasks.jrubyPrepare.outputDir
                sourceDir = project.file(asciidocRootSourceDir)
                sources {
                    include 'userguide/' + gematikAsciidoctor.projectShortcut + '_Main.adoc'
                }
                backends = ['html5', 'pdf']
                outputDir = project.file(asciidocDestinationFolder)
                def versionReference = gematikAsciidoctor.documentVersionReference
                if (versionReference == null)
                    versionReference = "version_" + gematikAsciidoctor.projectShortcut
                if (project.file('version.txt').exists())
                    attributes.put(versionReference, project.file('version.txt').text.trim())
                else
                    attributes.put(versionReference, project.file('../version.txt').text.trim())

                //Used to break the build if includes not found
                ext.capturedOutput = []
                def listener = { ext.capturedOutput << it } as StandardOutputListener

                logging.addStandardErrorListener(listener)
                logging.addStandardOutputListener(listener)

                doLast {
                    logging.removeStandardOutputListener(listener)
                    logging.removeStandardErrorListener(listener)
                    ext.capturedOutput.join('').with { output ->
                        if (output =~ /include file not found:/) {
                            throw new RuntimeException("Include file(s) not found.\n" + output)
                        }
                    }
                }
            }
        }
    }
}

/*
 * Copyright (c) 2021 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
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

    @Override
    void apply(final Project project) {
        super.apply(project)

        project.getPluginManager().apply("org.asciidoctor.jvm.convert")
        project.getPluginManager().apply("org.asciidoctor.jvm.pdf")
        project.tasks.asciidoctor.dependsOn(project.tasks.copyPlantumlsToDocDir)
        project.tasks.asciidoctor.dependsOn(project.tasks.asciidoctorPdf)
        project.tasks.asciidoctor.doLast { project.tasks.asciidoctorPdf }
        project.tasks.asciidoctor.doFirst {
            project.tasks.copyPlantumlsToDocDir
        }

        project.afterEvaluate { p ->
            project.asciidoctorj {
                modules {
                    diagram.use()
                    diagram.version '1.5.16'
                }
            }
        }
        def gematikAsciidoctor = project.extensions.findByType(DocumentationPluginExtension)
        def asciidocRootSourceDir = 'doc'
        if (gematikAsciidoctor.asciidocRootSourceDir != null)
            asciidocRootSourceDir = gematikAsciidoctor.asciidocRootSourceDir
        def asciidocDestinationFolder = project.buildDir.path + '/documentation'
        if (gematikAsciidoctor.asciidocDestinationFolder != null)
            asciidocDestinationFolder = gematikAsciidoctor.asciidocDestinationFolder

        println "AsciidoctorPlugin use asciidoctorj: " + project.extensions.getByName("asciidoctorj").version

        project.afterEvaluate { p ->
            project.asciidoctor {

                baseDir project.file(asciidocRootSourceDir + "/userguide")
                sourceDir project.file(asciidocRootSourceDir)
                sources {
                    include "userguide/" + gematikAsciidoctor.projectShortcut + '_Main.adoc'
                }
                def versionReference = gematikAsciidoctor.documentVersionReference
                if (versionReference == null)
                    versionReference = "version_" + gematikAsciidoctor.projectShortcut
                if (project.file('version.txt').exists())
                    attributes "${versionReference}" : project.file('version.txt').text.trim()
                else
                    attributes "${versionReference}" : project.file('../version.txt').text.trim()

                outputDir = project.file(asciidocDestinationFolder)

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

        project.afterEvaluate { p ->
            project.asciidoctorPdf {
                baseDir project.file(asciidocRootSourceDir + "/userguide")
                sourceDir project.file(asciidocRootSourceDir)
                sources {
                    include 'userguide/' + gematikAsciidoctor.projectShortcut + '_Main.adoc'
                }
                outputDir = project.file(asciidocDestinationFolder)
                asciidoctorj {
                    attributes 'source-highlighter': 'rouge'
                    def versionReference = gematikAsciidoctor.documentVersionReference
                    if (versionReference == null)
                        versionReference = "version_" + gematikAsciidoctor.projectShortcut
                    if (project.file('version.txt').exists())
                        attributes "${versionReference}" : project.file('version.txt').text.trim()
                    else
                        attributes "${versionReference}" : project.file('../version.txt').text.trim()
                }
            }
        }
    }
}

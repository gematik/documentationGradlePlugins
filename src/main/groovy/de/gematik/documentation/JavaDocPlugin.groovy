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

/**
 * Javadoc Plugin with Asciidoctor settings
 */
class JavaDocPlugin extends DocumentationPlugin {
    @Override
    void apply(final Project project) {
        super.apply(project)
        if (project.tasks.findByName("publishToMavenLocal") != null)
            project.tasks.publishToMavenLocal.dependsOn(project.tasks.javadoc)
        project.tasks.javadoc.doFirst {
            project.tasks.copyPlantumlsToDocDir
        }

        project.afterEvaluate { p ->
            project.javadoc {
                def gematikAsciidoctor = project.extensions.findByType(DocumentationPluginExtension)
                def javadocOverviewAdoc = "doc/userguide/" + gematikAsciidoctor.projectShortcut + "_Introduction.adoc"

                if (gematikAsciidoctor.javadocDestinationFolder != null)
                    project.tasks.clean {
                        delete gematikAsciidoctor.javadocDestinationFolder
                    }

                if (gematikAsciidoctor.javadocOverviewAdoc != null)
                    javadocOverviewAdoc = gematikAsciidoctor.javadocOverviewAdoc

                dependsOn project.tasks.copyPlantumlsToDocDir
                doFirst {
                    project.tasks.copyPlantumlsToDocDir
                }

                options.overview = javadocOverviewAdoc
                options.addStringOption "encoding", "UTF-8"
                options.addBooleanOption "noTimestamp", true

            }
        }
    }
}

/*
 * Copyright (c) 2020 gematik GmbH
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
package de.gematik.documentation.task

import de.gematik.documentation.DocumentationPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

/**
 * This Task copy all *.plantUml files from src/** to PROJECT/doc/plantuml/{projectShortcut} and remove footer
 */
class CopyPlantumlsToDocDir extends DefaultTask {

    Project project

    @Inject
    CopyPlantumlsToDocDir(Project project) {
        this.project = project
        setGroup("Gematik")
        setDescription("Copy all *.plantUml files from src/** to PROJECT/doc/plantuml/{projectShortcut} and remove footer")
    }

    @Override
    String toString() {
        return super.toString()
    }

    @TaskAction
    def copyPlantumlsToDocDir() {
        def gematikAsciidoctor = project.extensions.getByType(DocumentationPluginExtension)
        def srcDir = "src/main/java"

        def plantumlDir = "doc/plantuml/" + gematikAsciidoctor.projectShortcut
        if (gematikAsciidoctor.plantUmlDestinationFolder != null)
            plantumlDir = gematikAsciidoctor.plantUmlDestinationFolder
        project.copy {
            from(srcDir) {
                include "**/*.plantuml"
            }
            // flatten the collected plantuml files into the ${plantumlDir} directory
            eachFile {
                path = name
            }
            // remove title and footer from generated plantuml files
            filter { line ->
                line.replaceAll("title __.*|right footer|^PlantUML.*|^For more.*|endfooter", "")
            }
            into plantumlDir
            includeEmptyDirs = false
        }
    }
}

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
package de.gematik.documentation

import de.gematik.documentation.task.CopyPlantumlsToDocDir
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Install the necessary  gems for asciidoctor and add the Tasks such as configurations
 */
class DocumentationPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.getPluginManager().apply("com.github.jeysal.graphviz")
        project.getPluginManager().apply("com.github.jruby-gradle.base")
        project.configurations {
            asciidoclet
        }

        project.repositories {
            rubygems('https://rubygems.org')
        }
        project.dependencies {
            delegate.asciidoclet("org.asciidoctor:asciidoclet:1.5.6")
            delegate.gems("rubygems:asciidoctor:1.5.8")
            delegate.gems("rubygems:asciidoctor-diagram:1.5.19")
            delegate.gems("rubygems:concurrent-ruby:1.1.5")
        }
        if (project.getTasks().findByName("copyPlantumlsToDocDir") == null)
            project.getTasks().create("copyPlantumlsToDocDir", CopyPlantumlsToDocDir.class, project)

        if (project.extensions.findByType(DocumentationPluginExtension) == null)
            project.extensions.create('gematikDocumentation', DocumentationPluginExtension)
    }
}

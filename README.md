# Gematik Gradle Plugins

## Description

This Plugin supports easy documentation in AsciiDoc format and also JavaDoc in AsciiDoc format.
For that is an comprehensive configuration defined.

## Configuration

The plugins are configured via the gradle file in a configuration block gematikDocumentation {}.
The following parameters are available:

<table>
<caption>Table Title</caption>
<colgroup>
<col style="width: 20%" />
<col style="width: 20%" />
<col style="width: 20%" />
<col style="width: 20%" />
<col style="width: 20%" />
</colgroup>
<thead>
<tr class="header">
<th>Parameter</th>
<th>Description</th>
<th>Mandatory</th>
<th>Default-Value</th>
<th>Used by</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p>projectShortcut</p></td>
<td><p>Jira abbreviation for the project</p></td>
<td><p>✓</p></td>
<td><p> — </p></td>
<td><ul>
<li><p>DocumentationPlugin</p></li>
<li><p>AsciidoctorPlugin</p></li>
<li><p>JavaDocPlugin</p></li>
</ul></td>
</tr>
<tr class="even">
<td><p>plantUmlDestinationFolder</p></td>
<td><p>Target directory for copying PlantUmls from src/main/java/**</p></td>
<td><p>✗</p></td>
<td><p>doc/plantuml/{projectShortcut}</p></td>
<td><ul>
<li><p>DocumentationPlugin</p></li>
<li><p>AsciidoctorPlugin</p></li>
<li><p>JavaDocPlugin</p></li>
</ul></td>
</tr>
<tr class="odd">
<td><p>javadocUrl</p></td>
<td><p>Title link URL for Javadoc</p></td>
<td><p>✗</p></td>
<td><p> — </p></td>
<td><ul>
<li><p>JavaDocPlugin</p></li>
</ul></td>
</tr>
<tr class="even">
<td><p>javadocOverviewAdoc</p></td>
<td><p>Document that should be shown in the overview with Javadoc</p></td>
<td><p>✗</p></td>
<td><p>doc/userguide/{projectShortcut}_Introduction.adoc</p></td>
<td><ul>
<li><p>JavaDocPlugin</p></li>
</ul></td>
</tr>
<tr class="odd">
<td><p>javadocDestinationFolder</p></td>
<td><p>Output-Directory for JavaDocs</p></td>
<td><p>✗</p></td>
<td><p>Gradle Default is ${project.docsDir}/javadoc</p></td>
<td><ul>
<li><p>JavaDocPlugin</p></li>
</ul></td>
</tr>
<tr class="even">
<td><p>javadocAttributesFile</p></td>
<td><p>Configuration file for Asciidocs in Javadoc</p></td>
<td><p>✗</p></td>
<td><p>doc/javadoc/config.adoc</p></td>
<td><ul>
<li><p>JavaDocPlugin</p></li>
</ul></td>
</tr>
<tr class="odd">
<td><p>documentVersionReference</p></td>
<td><p>Name of the reference in the Asciidoc documents to be replaced with the current Project version</p></td>
<td><p>✗</p></td>
<td><p>version_{projectShortcut}</p></td>
<td><ul>
<li><p>AsciidoctorPlugin</p></li>
</ul></td>
</tr>
<tr class="even">
<td><p>asciidocRootSourceDir</p></td>
<td><p>Source root folder for {asciidocRootSourceDir}/userguide/{projectShortcut}_Main.adoc</p></td>
<td><p>✗</p></td>
<td><p>doc</p></td>
<td><ul>
<li><p>AsciidoctorPlugin</p></li>
</ul></td>
</tr>
<tr class="odd">
<td><p>asciidocDestinationFolder</p></td>
<td><p>Output-Directory for Asciidocs</p></td>
<td><p>✗</p></td>
<td><p>${project.buildDir.path}/documentation</p></td>
<td><ul>
<li><p>AsciidoctorPlugin</p></li>
</ul></td>
</tr>
</tbody>
</table>

**Example Configuration.**

    gematikDocumentation {
        projectShortcut = "HCARDA"
        documentVersionReference = "version_healthcard_access"
    }

# DocumentationPlugin

This plugin defines global settings and basis for the following plugins.
This plugin adds the following other plugins, dependencies and tasks:

## Plugins

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<thead>
<tr class="header">
<th>PluginId</th>
<th>Version</th>
<th>Additional Information</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p>com.github.jruby-gradle.bas</p></td>
<td><p>1.6.0</p></td>
<td><p><a href="https://plugins.gradle.org/plugin/com.github.jruby-gradle.base" class="uri">https://plugins.gradle.org/plugin/com.github.jruby-gradle.base</a></p></td>
</tr>
<tr class="even">
<td><p>com.github.jeysal.graphviz</p></td>
<td><p>1.4.1</p></td>
<td><p><a href="https://github.com/jeysal/gradle-graphviz-plugin" class="uri">https://github.com/jeysal/gradle-graphviz-plugin</a></p></td>
</tr>
</tbody>
</table>

## Dependencies

<table>
<colgroup>
<col style="width: 25%" />
<col style="width: 25%" />
<col style="width: 25%" />
<col style="width: 25%" />
</colgroup>
<thead>
<tr class="header">
<th>Dependency</th>
<th>Configuration</th>
<th>Version</th>
<th>Additional Information</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p>org.asciidoctor:asciidoclet</p></td>
<td><p>asciidoclet</p></td>
<td><p>1.5.6</p></td>
<td><p><a href="https://asciidoctor.org/docs/asciidoctorj/" class="uri">https://asciidoctor.org/docs/asciidoctorj/</a></p></td>
</tr>
<tr class="even">
<td><p>rubygems:asciidoctor</p></td>
<td><p>gems</p></td>
<td><p>1.5.8</p></td>
<td><p><a href="https://asciidoctor.org/docs/asciidoctorj/" class="uri">https://asciidoctor.org/docs/asciidoctorj/</a></p></td>
</tr>
<tr class="odd">
<td><p>rubygems:asciidoctor-diagram</p></td>
<td><p>gems</p></td>
<td><p>1.5.19</p></td>
<td><p><a href="https://asciidoctor.org/docs/asciidoctor-diagram/" class="uri">https://asciidoctor.org/docs/asciidoctor-diagram/</a></p></td>
</tr>
<tr class="even">
<td><p>rubygems:concurrent-ruby</p></td>
<td><p>gems</p></td>
<td><p>1.1.5</p></td>
<td><p><a href="https://github.com/ruby-concurrency/concurrent-ruby" class="uri">https://github.com/ruby-concurrency/concurrent-ruby</a></p></td>
</tr>
</tbody>
</table>

## Tasks

### copyPlantumlsToDocDir

This task copies all **\*.plantuml** files from **PROJECT/src/main/java** to **{plantUmlDestinationFolder}**

**Add copyPlantumlsToDocDir.**

    buildscript {
        dependencies {
            classpath "de.gematik:documentation-plugin:1.2.0"
        }
    }
    apply plugin: "de.gematik.asciidoctor"

# JavaDocPlugin

This plugin creates the JavaDoc with Plantumls and Asciidoc Files.
The plugin functionality would automatically executed for **publishToMavenLocal** task or manuel with

**\#&gt; gradle javadoc** (this task also calls the copyPlantumlsToDocDir task)

Output: {javadocDestinationFolder} or default PROJECT\\build\\docs\\javadoc

## Plugins

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td><p>PluginId</p></td>
<td><p>Version</p></td>
<td><p>Additional Information</p></td>
</tr>
<tr class="even">
<td><p>org.asciidoctor.convert</p></td>
<td><p>1.4.1</p></td>
<td><p><a href="https://plugins.gradle.org/plugin/org.asciidoctor.convert" class="uri">https://plugins.gradle.org/plugin/org.asciidoctor.convert</a></p></td>
</tr>
</tbody>
</table>

## Integration

**Add JavaDocPlugin.**

    buildscript {
        dependencies {
            classpath "de.gematik:documentation-plugin:1.2.0"
        }
    }
    apply plugin: "de.gematik.javadoc"

# AsciidoctorPlugin

This plugin takes care of generation AsciiDoc in PDF and HTML pages.
The source file **{asciidocRootSourceDir}/userguide/{projectShortcut}\_Main.adoc** is used.

Manual execution:
**\#&gt; gradle asciidoctor** (This task also calls the copyPlantumlsToDocDir task)

Output: {asciidocDestinationFolder}

## Plugins

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td><p>PluginId</p></td>
<td><p>Version</p></td>
<td><p>Additional Information</p></td>
</tr>
<tr class="even">
<td><p>org.asciidoctor.convert</p></td>
<td><p>1.4.1</p></td>
<td><p><a href="https://plugins.gradle.org/plugin/org.asciidoctor.convert" class="uri">https://plugins.gradle.org/plugin/org.asciidoctor.convert</a></p></td>
</tr>
</tbody>
</table>

## Integration

**Add JavaDocPlugin.**

    buildscript {
        dependencies {
            classpath "de.gematik:documentation-plugin:1.2.0"
        }
    }
    apply plugin: "de.gematik.asciidoctor"

# Repository for dependencies

The Plugin needs the following repository definition in your init.gradle

**Add Repository jcenter.**

    allprojects {
        buildscript {
            repositories {
                jcenter()
            }
        }
    }

or this

**Add Repository plugins.gradle.org.**

    allprojects {
        buildscript {
            repositories {
                maven {
                    url "https://plugins.gradle.org/m2/"
                }
            }
        }
    }

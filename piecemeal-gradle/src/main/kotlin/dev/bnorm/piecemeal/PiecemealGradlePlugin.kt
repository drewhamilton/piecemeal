/*
 * Copyright (C) 2022 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.bnorm.piecemeal

import dev.bnorm.piecemeal.BuildConfig.SUPPORT_LIBRARY_COORDINATES
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet.Companion.COMMON_MAIN_SOURCE_SET_NAME
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetContainer
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@Suppress("unused") // Used via reflection
class PiecemealGradlePlugin : KotlinCompilerPluginSupportPlugin {
  override fun apply(target: Project) {
    target.extensions.create("piecemeal", PiecemealGradleExtension::class.java)

    target.afterEvaluate {
      if (target.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
        val kotlin = target.extensions.getByName("kotlin") as KotlinSourceSetContainer
        kotlin.sourceSets.getByName(COMMON_MAIN_SOURCE_SET_NAME) { sourceSet ->
          sourceSet.dependencies {
            implementation(SUPPORT_LIBRARY_COORDINATES)
          }
        }
      } else {
        if (target.plugins.hasPlugin("org.gradle.java-test-fixtures")) {
          target.dependencies.add("testFixturesImplementation", SUPPORT_LIBRARY_COORDINATES)
        }
        target.dependencies.add(IMPLEMENTATION_CONFIGURATION_NAME, SUPPORT_LIBRARY_COORDINATES)
      }
    }
  }

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

  override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_ID

  override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
    groupId = BuildConfig.KOTLIN_PLUGIN_GROUP,
    artifactId = BuildConfig.KOTLIN_PLUGIN_NAME,
    version = BuildConfig.KOTLIN_PLUGIN_VERSION,
  )

  override fun applyToCompilation(
    kotlinCompilation: KotlinCompilation<*>
  ): Provider<List<SubpluginOption>> {
    val project = kotlinCompilation.target.project
    val extension = project.extensions.getByType(PiecemealGradleExtension::class.java)

    return project.provider {
      listOf(
        SubpluginOption(
          key = "enableJavaSetters",
          value = extension.enableJavaSetters.get().toString(),
        ),
      )
    }
  }
}

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

package dev.bnorm.piecemeal.plugin

import com.google.auto.service.AutoService
import dev.bnorm.piecemeal.BuildConfig
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
@Suppress("unused") // Used via reflection
@AutoService(CommandLineProcessor::class)
class PiecemealCommandLineProcessor : CommandLineProcessor {
  companion object {
    val ENABLE_JAVA_SETTERS = CliOption(
      optionName = "enableJavaSetters",
      valueDescription = "<boolean>",
      description = "Enable Java setter generation.",
      required = false,
      allowMultipleOccurrences = false,
    )
  }

  override val pluginId: String = BuildConfig.KOTLIN_PLUGIN_ID

  override val pluginOptions: Collection<CliOption> = listOf(
    ENABLE_JAVA_SETTERS,
  )

  override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
    return when (option) {
      ENABLE_JAVA_SETTERS -> configuration.put(PiecemealConfiguration.ENABLE_JAVA_SETTERS, value.toBoolean())
      else -> error("Unexpected config option: '${option.optionName}'")
    }
  }
}

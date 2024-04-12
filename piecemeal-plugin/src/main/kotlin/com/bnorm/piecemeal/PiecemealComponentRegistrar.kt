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

package com.bnorm.piecemeal

import com.bnorm.piecemeal.fir.PiecemealFirExtensionRegistrar
import com.bnorm.piecemeal.ir.PiecemealIrGenerationExtension
import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

@AutoService(ComponentRegistrar::class)
class PiecemealComponentRegistrar : ComponentRegistrar {

  override val supportsK2: Boolean
    get() = true

  override fun registerProjectComponents(
    project: MockProject,
    configuration: CompilerConfiguration,
  ) {
    FirExtensionRegistrar.registerExtension(project, PiecemealFirExtensionRegistrar())
    IrGenerationExtension.registerExtension(project, PiecemealIrGenerationExtension())
  }
}

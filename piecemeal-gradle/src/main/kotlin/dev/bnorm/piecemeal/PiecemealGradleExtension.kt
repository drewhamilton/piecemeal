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

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

open class PiecemealGradleExtension(objectFactory: ObjectFactory) {
  /**
   * Classes annotated with `@Piecemeal` will have Java-style setters added to
   * their builder class.
   */
  val enableJavaSetters: Property<Boolean> =
    objectFactory.property(Boolean::class.java)
      .convention(false)
}

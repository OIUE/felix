/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.felix.ipojo.manipulator.metadata.annotation;

import org.apache.felix.ipojo.manipulator.metadata.annotation.registry.BindingRegistry;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
<<<<<<< HEAD
import org.objectweb.asm.commons.EmptyVisitor;
=======
import org.objectweb.asm.Opcodes;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import org.objectweb.asm.tree.FieldNode;

/**
 * @author <a href="mailto:dev@felix.apache.org">Felix Project Team</a>
 */
<<<<<<< HEAD
public class FieldMetadataCollector extends EmptyVisitor implements FieldVisitor {
=======
public class FieldMetadataCollector extends FieldVisitor {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

    /**
     * Binding's registry.
     */
    private BindingRegistry registry;

    /**
     * The workbench currently in use.
     */
    private ComponentWorkbench workbench;

    /**
     * Visited field.
     */
    private FieldNode node;

    public FieldMetadataCollector(ComponentWorkbench workbench, FieldNode node) {
<<<<<<< HEAD
=======
        super(Opcodes.ASM5);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        this.workbench = workbench;
        this.node = node;
        this.registry = workbench.getBindingRegistry();
    }

    /**
     * Visit annotations on the current field.
     * @param desc : annotation name
     * @param visible : is the annotation a runtime annotation.
     * @return the annotation visitor visiting the annotation
     * @see org.objectweb.asm.FieldVisitor#visitAnnotation(java.lang.String, boolean)
     */
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

        // Return the visitor to be executed (may be null)
        return registry.selection(workbench)
                .field(this, node)
                .annotatedWith(desc)
                .get();

    }


}

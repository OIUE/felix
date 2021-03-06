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

package org.apache.felix.ipojo.manipulator.metadata.annotation.visitor.generic;

import org.apache.felix.ipojo.manipulator.metadata.annotation.visitor.util.Elements;
import org.apache.felix.ipojo.metadata.Attribute;
import org.apache.felix.ipojo.metadata.Element;
import org.objectweb.asm.AnnotationVisitor;
<<<<<<< HEAD
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;
=======
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

/**
 * @author <a href="mailto:dev@felix.apache.org">Felix Project Team</a>
 */
<<<<<<< HEAD
public class SubArrayVisitor extends EmptyVisitor implements AnnotationVisitor {
=======
public class SubArrayVisitor extends AnnotationVisitor {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

    /**
     * Parent element.
     */
    private Element m_elem;

    /**
     * Attribute name.
     */
    private String m_name;

    /**
     * Attribute value.
     * (accumulator)
     */
    private String m_acc;

    /**
     * Constructor.
<<<<<<< HEAD
=======
     *
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     * @param elem : element element.
     * @param name : attribute name.
     */
    public SubArrayVisitor(Element elem, String name) {
<<<<<<< HEAD
=======
        super(Opcodes.ASM5);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        m_elem = elem;
        m_name = name;
    }

    /**
     * Visit a 'simple' element of the visited array.
<<<<<<< HEAD
     * @param name : null
     * @param value : element value.
     * @see org.objectweb.asm.commons.EmptyVisitor#visit(String, Object)
=======
     *
     * @param name  : null
     * @param value : element value.
     * @see org.objectweb.asm.AnnotationVisitor#visit(String, Object)
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     */
    public void visit(String name, Object value) {
        if (m_acc == null) {
            if (!(value instanceof Type)) {
                m_acc = "{" + value.toString();
            } else {
                // Attributes of type class need a special handling
                m_acc = "{" + ((Type) value).getClassName();
            }
        } else {
            if (!(value instanceof Type)) {
                m_acc = m_acc + "," + value.toString();
            } else {
                // Attributes of type class need a special handling
                m_acc = m_acc + "," + ((Type) value).getClassName();
            }
        }
    }

    /**
     * Visits an enumeration attribute.
<<<<<<< HEAD
     * @param name the attribute name
     * @param desc the enumeration descriptor
=======
     *
     * @param name  the attribute name
     * @param desc  the enumeration descriptor
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     * @param value the attribute value
     */
    public void visitEnum(String name, String desc, String value) {
        if (m_acc == null) {
            m_acc = "{" + value;
        } else {
            m_acc = m_acc + "," + value;
        }
    }


    /**
     * Visit an annotation element of the visited array.
<<<<<<< HEAD
     * @param name : null
     * @param desc : annotation to visit
     * @return the visitor which will visit the annotation
     * @see org.objectweb.asm.commons.EmptyVisitor#visitAnnotation(String, String)
=======
     *
     * @param name : null
     * @param desc : annotation to visit
     * @return the visitor which will visit the annotation
     * @see org.objectweb.asm.AnnotationVisitor#visitAnnotation(String, String)
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     */
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        // Sub annotations are map to sub-elements
        Element elem = Elements.buildElement(Type.getType(desc));
        m_elem.addElement(elem);
        return new GenericVisitor(elem);
    }

    /**
     * End of the visit.
<<<<<<< HEAD
     * @see org.objectweb.asm.commons.EmptyVisitor#visitEnd()
=======
     *
     * @see org.objectweb.asm.AnnotationVisitor#visitEnd()
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     */
    public void visitEnd() {
        if (m_acc != null) {
            // We have analyzed an attribute
            m_elem.addAttribute(new Attribute(m_name, m_acc + "}"));
        }
    }

}

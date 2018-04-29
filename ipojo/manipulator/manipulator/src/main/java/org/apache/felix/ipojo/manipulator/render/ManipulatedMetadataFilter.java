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

package org.apache.felix.ipojo.manipulator.render;

<<<<<<< HEAD
import org.apache.felix.ipojo.manipulation.MethodCreator;
=======
import org.apache.felix.ipojo.manipulation.ClassManipulator;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import org.apache.felix.ipojo.metadata.Attribute;
import org.apache.felix.ipojo.metadata.Element;

/**
 * A {@code ManipulatedMetadataFilter}
 *
 * @author <a href="mailto:dev@felix.apache.org">Felix Project Team</a>
 */
public class ManipulatedMetadataFilter implements MetadataFilter {

    public boolean accept(Element element) {

        // TODO I'm sure we can do better then testing blindly all attributes
        // iPOJO manipulated elements filter
        for (Attribute attribute : element.getAttributes()) {
            String value = attribute.getValue();

            // Filters:
            // * manipulated methods
            // * fields for the InstanceManager
            // * InstanceManager setter
<<<<<<< HEAD
            if (value.startsWith(MethodCreator.PREFIX)
=======
            if (value.startsWith(ClassManipulator.PREFIX)
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                    || value.contains("org.apache.felix.ipojo.InstanceManager")
                    || value.contains("_setInstanceManager")) {
                return true;
            }
        }

        return false;
    }
}

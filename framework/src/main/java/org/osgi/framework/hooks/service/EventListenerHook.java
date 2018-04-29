/*
<<<<<<< HEAD
 * Copyright (c) OSGi Alliance (2010, 2012). All Rights Reserved.
=======
 * Copyright (c) OSGi Alliance (2010, 2013). All Rights Reserved.
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
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

package org.osgi.framework.hooks.service;

import java.util.Collection;
import java.util.Map;
<<<<<<< HEAD
=======
import org.osgi.annotation.versioning.ConsumerType;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.hooks.service.ListenerHook.ListenerInfo;

/**
 * OSGi Framework Service Event Listener Hook Service.
 * 
 * <p>
 * Bundles registering this service will be called during framework service
 * (register, modify, and unregister service) operations.
 * 
 * @ThreadSafe
 * @since 1.1
<<<<<<< HEAD
 * @version $Id: b0b99b29206f272ad479fa08ffcd5ef5fda909b8 $
 */

=======
 * @author $Id: 2b80241ca24005be3a9a3550138e1ba9a3a9ad6e $
 */
@ConsumerType
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
public interface EventListenerHook {
	/**
	 * Event listener hook method. This method is called prior to service event
	 * delivery when a publishing bundle registers, modifies or unregisters a
	 * service. This method can filter the listeners which receive the event.
	 * 
	 * @param event The service event to be delivered.
	 * @param listeners A map of Bundle Contexts to a collection of Listener
	 *        Infos for the bundle's listeners to which the specified event will
	 *        be delivered. The implementation of this method may remove bundle
	 *        contexts from the map and listener infos from the collection
	 *        values to prevent the event from being delivered to the associated
	 *        listeners. The map supports all the optional {@code Map}
	 *        operations except {@code put} and {@code putAll}. Attempting to
	 *        add to the map will result in an
	 *        {@code UnsupportedOperationException}. The collection values in
	 *        the map supports all the optional {@code Collection} operations
	 *        except {@code add} and {@code addAll}. Attempting to add to a
	 *        collection will result in an {@code UnsupportedOperationException}
	 *        . The map and the collections are not synchronized.
	 */
	void event(ServiceEvent event, Map<BundleContext, Collection<ListenerInfo>> listeners);
}

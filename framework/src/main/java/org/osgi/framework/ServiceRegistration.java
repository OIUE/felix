/*
<<<<<<< HEAD
 * Copyright (c) OSGi Alliance (2000, 2012). All Rights Reserved.
=======
 * Copyright (c) OSGi Alliance (2000, 2014). All Rights Reserved.
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

package org.osgi.framework;

import java.util.Dictionary;
<<<<<<< HEAD
=======
import org.osgi.annotation.versioning.ProviderType;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

/**
 * A registered service.
 * 
 * <p>
 * The Framework returns a {@code ServiceRegistration} object when a
 * {@code BundleContext.registerService} method invocation is successful. The
 * {@code ServiceRegistration} object is for the private use of the registering
 * bundle and should not be shared with other bundles.
 * <p>
 * The {@code ServiceRegistration} object may be used to update the properties
 * of the service or to unregister the service.
 * 
 * @param <S> Type of Service.
 * @see BundleContext#registerService(String[],Object,Dictionary)
 * @ThreadSafe
<<<<<<< HEAD
 * @noimplement
 * @version $Id: a84248da0db0538708d2394a9478153e06b8afb9 $
 */

=======
 * @author $Id: 0bc5bfa68ae7cb4a409c066585d3ab4077d80eeb $
 */
@ProviderType
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
public interface ServiceRegistration<S> {
	/**
	 * Returns a {@code ServiceReference} object for a service being registered.
	 * <p>
	 * The {@code ServiceReference} object may be shared with other bundles.
	 * 
	 * @throws IllegalStateException If this {@code ServiceRegistration} object
	 *         has already been unregistered.
	 * @return {@code ServiceReference} object.
	 */
	public ServiceReference<S> getReference();

	/**
	 * Updates the properties associated with a service.
	 * 
	 * <p>
<<<<<<< HEAD
	 * The {@link Constants#OBJECTCLASS} and {@link Constants#SERVICE_ID} keys
=======
	 * The {@link Constants#OBJECTCLASS}, {@link Constants#SERVICE_BUNDLEID},
	 * {@link Constants#SERVICE_ID} and {@link Constants#SERVICE_SCOPE} keys
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
	 * cannot be modified by this method. These values are set by the Framework
	 * when the service is registered in the OSGi environment.
	 * 
	 * <p>
	 * The following steps are required to modify service properties:
	 * <ol>
<<<<<<< HEAD
	 * <li>The service's properties are replaced with the provided properties.
	 * <li>A service event of type {@link ServiceEvent#MODIFIED} is fired.
=======
	 * <li>The service's properties are replaced with the provided properties.</li>
	 * <li>A service event of type {@link ServiceEvent#MODIFIED} is fired.</li>
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
	 * </ol>
	 * 
	 * @param properties The properties for this service. See {@link Constants}
	 *        for a list of standard service property keys. Changes should not
	 *        be made to this object after calling this method. To update the
	 *        service's properties this method should be called again.
	 * 
	 * @throws IllegalStateException If this {@code ServiceRegistration} object
	 *         has already been unregistered.
	 * @throws IllegalArgumentException If {@code properties} contains case
	 *         variants of the same key name.
	 */
	public void setProperties(Dictionary<String, ?> properties);

	/**
	 * Unregisters a service. Remove a {@code ServiceRegistration} object from
	 * the Framework service registry. All {@code ServiceReference} objects
	 * associated with this {@code ServiceRegistration} object can no longer be
	 * used to interact with the service once unregistration is complete.
	 * 
	 * <p>
	 * The following steps are required to unregister a service:
	 * <ol>
	 * <li>The service is removed from the Framework service registry so that it
<<<<<<< HEAD
	 * can no longer be obtained.
=======
	 * can no longer be obtained.</li>
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
	 * <li>A service event of type {@link ServiceEvent#UNREGISTERING} is fired
	 * so that bundles using this service can release their use of the service.
	 * Once delivery of the service event is complete, the
	 * {@code ServiceReference} objects for the service may no longer be used to
<<<<<<< HEAD
	 * get a service object for the service.
	 * <li>For each bundle whose use count for this service is greater than
	 * zero: <br>
	 * The bundle's use count for this service is set to zero. <br>
	 * If the service was registered with a {@link ServiceFactory} object, the
	 * {@code ServiceFactory.ungetService} method is called to release the
	 * service object for the bundle.
=======
	 * get a service object for the service.</li>
	 * <li>For each bundle whose use count for this service is greater than
	 * zero:
	 * <ul>
	 * <li>The bundle's use count for this service is set to zero.</li>
	 * <li>If the service was registered with a {@link ServiceFactory} object,
	 * the {@code ServiceFactory.ungetService} method is called to release the
	 * service object for the bundle.</li>
	 * </ul>
	 * </li>
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
	 * </ol>
	 * 
	 * @throws IllegalStateException If this {@code ServiceRegistration} object
	 *         has already been unregistered.
	 * @see BundleContext#ungetService(ServiceReference)
	 * @see ServiceFactory#ungetService(Bundle, ServiceRegistration, Object)
	 */
	public void unregister();
}

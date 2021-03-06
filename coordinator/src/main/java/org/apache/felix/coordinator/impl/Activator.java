/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.coordinator.impl;

import java.util.Hashtable;

<<<<<<< HEAD
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.felix.jmx.service.coordinator.CoordinatorMBean;
import org.apache.felix.service.coordinator.Coordinator;
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceFactory;
<<<<<<< HEAD
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings("deprecation")
=======
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.coordinator.Coordinator;

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
public class Activator implements BundleActivator
{

    private CoordinationMgr mgr;

<<<<<<< HEAD
    private ServiceTracker mbeanServerTracker;

    private ServiceRegistration coordinatorService;

    private ServiceRegistration coordinatorCommand;

    public void start(BundleContext context)
    {
        mgr = new CoordinationMgr();

        try
        {
            mbeanServerTracker = new MBeanServerTracker(context, mgr);
            mbeanServerTracker.open();
        }
        catch (MalformedObjectNameException e)
        {
            // TODO log
        }

        ServiceFactory factory = new CoordinatorFactory(mgr);
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(Constants.SERVICE_DESCRIPTION, "Coordinator Service Implementation");
        props.put(Constants.SERVICE_VENDOR, "The Apache Software Foundation");
        coordinatorService = context.registerService(Coordinator.class.getName(), factory, props);

        try
        {
            coordinatorCommand = CrdCommand.create(context, mgr);
        }
        catch (Throwable t)
        {
            // most probably missing resolved packages, ignore
        }
    }

    public void stop(BundleContext context)
    {
        if (coordinatorCommand != null)
        {
            coordinatorCommand.unregister();
            coordinatorCommand = null;
        }

=======
    private ServiceRegistration coordinatorService;

    public void start(final BundleContext context)
    {
        LogWrapper.setContext(context);

        mgr = new CoordinationMgr();

        final ServiceFactory factory = new CoordinatorFactory(mgr);
        final Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(Constants.SERVICE_DESCRIPTION, "Coordinator Service Implementation");
        props.put(Constants.SERVICE_VENDOR, "The Apache Software Foundation");
        coordinatorService = context.registerService(Coordinator.class.getName(), factory, props);
    }

    public void stop(final BundleContext context)
    {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        if (coordinatorService != null)
        {
            coordinatorService.unregister();
            coordinatorService = null;
        }

<<<<<<< HEAD
        if (mbeanServerTracker != null)
        {
            mbeanServerTracker.close();
            mbeanServerTracker = null;
        }

        mgr.cleanUp();
=======
        mgr.cleanUp();

        LogWrapper.setContext(null);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    static final class CoordinatorFactory implements ServiceFactory
    {

        private final CoordinationMgr mgr;

        CoordinatorFactory(final CoordinationMgr mgr)
        {
            this.mgr = mgr;
        }

<<<<<<< HEAD
        public Object getService(Bundle bundle, ServiceRegistration registration)
=======
        public Object getService(final Bundle bundle, final ServiceRegistration registration)
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        {
            return new CoordinatorImpl(bundle, mgr);
        }

<<<<<<< HEAD
        public void ungetService(Bundle bundle, ServiceRegistration registration, Object service)
=======
        public void ungetService(final Bundle bundle, final ServiceRegistration registration, final Object service)
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        {
            ((CoordinatorImpl) service).dispose();
        }

    }
<<<<<<< HEAD

    static final class MBeanServerTracker extends ServiceTracker
    {

        private final CoordinationMgr mgr;

        private final ObjectName objectName;

        MBeanServerTracker(final BundleContext context, final CoordinationMgr mgr) throws MalformedObjectNameException
        {
            super(context, MBeanServer.class.getName(), null);
            this.mgr = mgr;
            this.objectName = new ObjectName(CoordinatorMBean.OBJECTNAME);
        }

        @Override
        public Object addingService(ServiceReference reference)
        {
            MBeanServer server = (MBeanServer) super.addingService(reference);

            try
            {
                server.registerMBean(mgr, objectName);
            }
            catch (Exception e)
            {
                // TODO: log
            }

            return server;
        }

        @Override
        public void removedService(ServiceReference reference, Object service)
        {
            try
            {
                ((MBeanServer) service).unregisterMBean(objectName);
            }
            catch (Exception e)
            {
                // TODO: log
            }

            super.removedService(reference, service);
        }
    }
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
}

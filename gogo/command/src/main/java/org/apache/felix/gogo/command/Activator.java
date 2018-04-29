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
package org.apache.felix.gogo.command;

import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator
{
<<<<<<< HEAD
    private volatile ServiceTracker m_tracker = null;

    public void start(BundleContext bc) throws Exception
    {
        Hashtable props = new Hashtable();
=======
    public void start(BundleContext bc) {
        BundleContext systemBundleContext = bc.getBundle(0).getBundleContext();
        Hashtable<String, Object> props = new Hashtable<>();
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        props.put("osgi.command.scope", "felix");
        props.put("osgi.command.function", new String[] {
            "bundlelevel", "frameworklevel", "headers",
            "help", "install", "lb", "log", "refresh",
            "resolve", "start", "stop", "uninstall", "update",
            "which" });
        bc.registerService(
<<<<<<< HEAD
            Basic.class.getName(), new Basic(bc), props);
=======
            Basic.class.getName(), new Basic(systemBundleContext), props);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

        // Register "inspect" command for R4.3 or R4.2 depending
        // on the underlying framework.
        props.put("osgi.command.scope", "felix");
        props.put("osgi.command.function", new String[] { "inspect" });
        try
        {
            getClass().getClassLoader().loadClass("org.osgi.framework.wiring.BundleWiring");
            bc.registerService(
<<<<<<< HEAD
                Inspect.class.getName(), new Inspect(bc), props);
=======
                Inspect.class.getName(), new Inspect(systemBundleContext), props);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }
        catch (Throwable th)
        {
            bc.registerService(
                Inspect42.class.getName(), new Inspect42(bc), props);
        }

        props.put("osgi.command.scope", "felix");
        props.put("osgi.command.function", new String[] { "cd", "ls" });
        bc.registerService(
            Files.class.getName(), new Files(bc), props);
<<<<<<< HEAD

        m_tracker = new ServiceTracker(
            bc, "org.apache.felix.bundlerepository.RepositoryAdmin", null);
        m_tracker.open();
        props.put("osgi.command.scope", "obr");
        props.put("osgi.command.function", new String[] {
            "deploy", "info", "javadoc", "list", "repos", "source" });
        bc.registerService(
            OBR.class.getName(), new OBR(bc, m_tracker), props);
    }

    public void stop(BundleContext bc) throws Exception
    {
        m_tracker.close();
=======
    }

    public void stop(BundleContext bc) {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }
}
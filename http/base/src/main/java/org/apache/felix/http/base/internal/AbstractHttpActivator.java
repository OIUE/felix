/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.felix.http.base.internal;

public abstract class AbstractHttpActivator
    extends AbstractActivator
{
<<<<<<< HEAD
    private DispatcherServlet dispatcher;
    private EventDispatcher eventDispatcher;
    private HttpServiceController controller;

    protected final DispatcherServlet getDispatcherServlet()
    {
        return this.dispatcher;
    }

    protected final EventDispatcher getEventDispatcher()
    {
        return this.eventDispatcher;
    }

=======
    private HttpServiceController controller;

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    protected final HttpServiceController getHttpServiceController()
    {
        return this.controller;
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    protected void doStart()
        throws Exception
    {
        this.controller = new HttpServiceController(getBundleContext());
<<<<<<< HEAD
        this.dispatcher = new DispatcherServlet(this.controller);
        this.eventDispatcher = new EventDispatcher(this.controller);
    }

=======
    }

    @Override
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    protected void doStop()
        throws Exception
    {
        this.controller.unregister();
<<<<<<< HEAD
        this.dispatcher.destroy();
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }
}

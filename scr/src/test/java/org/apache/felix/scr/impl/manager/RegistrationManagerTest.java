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
package org.apache.felix.scr.impl.manager;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.apache.felix.scr.impl.manager.RegistrationManager.RegState;
import org.junit.Test;


public class RegistrationManagerTest
{
<<<<<<< HEAD
    
    private volatile boolean fail;
    
    private int n = 10;
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    
    private TRM trm = new TRM();
    
    @Test
    public void testRegistrationManager( ) throws Exception
    {
       
=======

    private volatile boolean fail;

    private int n = 10;
    private ArrayList<Thread> threads = new ArrayList<>();

    private TRM trm = new TRM();

    @Test
    public void testRegistrationManager( ) throws Exception
    {

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        for (int setup = 0; setup < 1 << n; setup++ )
        {
            runTest(setup);
            if (fail)
            {
                fail("failed at " + setup);
            }
        }
    }
<<<<<<< HEAD
    
    private void runTest(int setup) throws InterruptedException
    {   
=======

    private void runTest(int setup) throws InterruptedException
    {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        final CountDownLatch done = new CountDownLatch( n );
        for (int i = 0; i < n; i++)
        {
            boolean b = ((setup >>> i) & 1) == 0;
            final RegState change = b? RegState.registered: RegState.unregistered;
            new Thread(new Runnable() {

<<<<<<< HEAD
=======
                @Override
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                public void run()
                {
                    trm.changeRegistration( change, null );
                }
<<<<<<< HEAD
                
=======

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            }).start();
            done.countDown();
        }
        done.await();
    }

<<<<<<< HEAD
    
    private class TRM extends RegistrationManager<Object> 
=======

    private class TRM extends RegistrationManager<Object>
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    {

        @Override
        Object register(String[] services)
        {
            try
            {
                Thread.sleep( 1 );
            }
            catch ( InterruptedException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return new Object();
        }

        @Override
<<<<<<< HEAD
=======
        void postRegister(Object t)
        {
            // TODO Auto-generated method stub
        }

        @Override
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        void unregister(Object serviceRegistration)
        {
            try
            {
                Thread.sleep( 1 );
            }
            catch ( InterruptedException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
<<<<<<< HEAD
        void log(int level, String message, Object[] arguments, Throwable ex)
        {
            if ( arguments.length == 1 && (arguments[0] instanceof ArrayList))
=======
        void log(int level, String message, Throwable ex, Object... arguments)
        {
            if ( arguments != null && arguments.length == 1 && (arguments[0] instanceof ArrayList))
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            {
                ArrayList<RegState> opqueue = ( ArrayList<org.apache.felix.scr.impl.manager.RegistrationManager.RegState> ) arguments[0];
//                System.out.println("opqueue: " + opqueue);
                if (opqueue.size() > 1)
                {
                    for (int i = 1; i < opqueue.size(); i++)
                    {
                        if (opqueue.get( i -1  ) == opqueue.get(i))
                        {
                            fail = true;
                            System.out.println("opqueue: " + opqueue);
                            return;
                        }
                    }
                }
            }
<<<<<<< HEAD
            
=======

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }

        @Override
        long getTimeout()
        {
            // TODO Auto-generated method stub
            return 10;
        }

        @Override
        void reportTimeout()
        {
            // TODO Auto-generated method stub
<<<<<<< HEAD
            
        }
        
=======

        }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }
}

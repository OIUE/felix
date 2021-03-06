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
package org.apache.felix.gogo.runtime;

public class EOFError extends SyntaxError
{
    private static final long serialVersionUID = 1L;
<<<<<<< HEAD
    
    public EOFError(int line, int column, String message)
    {
        super(line, column, message);
    }
=======

    private final String missing;
    private final String repair;

    public EOFError(int line, int column, String message, String missing, String repair)
    {
        super(line, column, message);
        this.missing = missing;
        this.repair = repair;
    }

    public String repair()
    {
        return repair;
    }

    public String missing()
    {
        return missing;
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
}

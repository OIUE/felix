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
package org.apache.felix.framework;

import org.apache.felix.framework.cache.Content;
import org.apache.felix.framework.cache.DirectoryContent;
import org.apache.felix.framework.cache.JarContent;
import org.apache.felix.framework.ext.ClassPathExtenderFactory;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.ImmutableList;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.framework.util.Util;
import org.apache.felix.framework.util.manifestparser.ManifestParser;
import org.apache.felix.framework.util.manifestparser.NativeLibrary;
import org.apache.felix.framework.util.manifestparser.NativeLibraryClause;
import org.apache.felix.framework.wiring.BundleCapabilityImpl;
import org.apache.felix.framework.wiring.BundleWireImpl;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.Version;
import org.osgi.framework.namespace.NativeNamespace;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

import java.io.File;
import java.io.IOException;
<<<<<<< HEAD
import java.net.InetAddress;
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
<<<<<<< HEAD
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.ImmutableList;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.framework.util.Util;
import org.apache.felix.framework.util.manifestparser.ManifestParser;
import org.apache.felix.framework.cache.Content;
import org.apache.felix.framework.util.manifestparser.R4Library;
import org.apache.felix.framework.wiring.BundleCapabilityImpl;
import org.apache.felix.framework.wiring.BundleWireImpl;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

/**
 * The ExtensionManager class is used in several ways.
 * <p>
 * First, a private instance is added (as URL with the instance as
 * URLStreamHandler) to the classloader that loaded the class.
 * It is assumed that this is an instance of URLClassloader (if not extension
 * bundles will not work). Subsequently, extension bundles can be managed by
 * instances of this class (their will be one instance per framework instance).
 * </p>
 * <p>
 * Second, it is used as module definition of the systembundle. Added extension
 * bundles with exported packages will contribute their exports to the
 * systembundle export.
 * </p>
 * <p>
 * Third, it is used as content loader of the systembundle. Added extension
 * bundles exports will be available via this loader.
 * </p>
 */
// The general approach is to have one private static instance that we register
// with the parent classloader and one instance per framework instance that
// keeps track of extension bundles and systembundle exports for that framework
// instance.
<<<<<<< HEAD
class ExtensionManager extends URLStreamHandler implements Content
{
    // The private instance that is added to Felix.class.getClassLoader() -
    // will be null if extension bundles are not supported (i.e., we are not
    // loaded by an instance of URLClassLoader)
    static final ExtensionManager m_extensionManager;

    static
    {
        // pre-init the url sub-system as otherwise we don't work on gnu/classpath
        ExtensionManager extensionManager = new ExtensionManager();
        try
        {
            (new URL("http://felix.extensions:9/")).openConnection();
        }
        catch (Throwable t)
        {
            // This doesn't matter much - we only need the above to init the url subsystem
        }

        // We use the secure action of Felix to add a new instance to the parent
        // classloader.
        try
        {
            Felix.m_secureAction.addURLToURLClassLoader(Felix.m_secureAction.createURL(
                Felix.m_secureAction.createURL(null, "http:", extensionManager),
                "http://felix.extensions:9/", extensionManager),
                Felix.class.getClassLoader());
        }
        catch (Throwable ex)
        {
            // extension bundles will not be supported.
            extensionManager = null;
=======
class ExtensionManager implements Content
{
    static final ClassPathExtenderFactory.ClassPathExtender m_extenderFramework;
    static final ClassPathExtenderFactory.ClassPathExtender m_extenderBoot;

    static
    {
        ClassPathExtenderFactory.ClassPathExtender extenderFramework = null;
        ClassPathExtenderFactory.ClassPathExtender extenderBoot = null;

        if (!"true".equalsIgnoreCase(Felix.m_secureAction.getSystemProperty(FelixConstants.FELIX_EXTENSIONS_DISABLE, "false")))
        {
            ServiceLoader<ClassPathExtenderFactory> loader = ServiceLoader.load(ClassPathExtenderFactory.class,
                    ExtensionManager.class.getClassLoader());


            for (Iterator<ClassPathExtenderFactory> iter = loader.iterator();
                 iter.hasNext() && (extenderFramework == null || extenderBoot == null); )
            {
                try
                {
                    ClassPathExtenderFactory factory = iter.next();

                    if (extenderFramework == null)
                    {
                        try
                        {
                            extenderFramework = factory.getExtender(ExtensionManager.class.getClassLoader());
                        }
                        catch (Throwable t)
                        {
                            // Ignore
                        }
                    }
                    if (extenderBoot == null)
                    {
                        try
                        {
                            extenderBoot = factory.getExtender(null);
                        }
                        catch (Throwable t)
                        {
                            // Ignore
                        }
                    }
                }
                catch (Throwable t)
                {
                    // Ignore
                }
            }

            try
            {
                if (extenderFramework == null)
                {
                    extenderFramework = new ClassPathExtenderFactory.DefaultClassLoaderExtender()
                            .getExtender(ExtensionManager.class.getClassLoader());
                }
            }
            catch (Throwable t) {
                // Ignore
            }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }

        m_extenderFramework = extenderFramework;
        m_extenderBoot = extenderBoot;
    }

    private final Logger m_logger;
    private final Map m_configMap;
    private final Map m_headerMap = new StringMap();
<<<<<<< HEAD
    private final BundleRevision m_systemBundleRevision;
    private volatile List<BundleCapability> m_capabilities = Collections.EMPTY_LIST;
    private volatile Set<String> m_exportNames = Collections.EMPTY_SET;
    private volatile Object m_securityContext = null;
    private final List m_extensions;
    private volatile Bundle[] m_extensionsCache;
    private final Set m_names;
    private final Map m_sourceToExtensions;

    // This constructor is only used for the private instance added to the parent
    // classloader.
    private ExtensionManager()
    {
        m_logger = null;
        m_configMap = null;
        m_systemBundleRevision = null;
        m_extensions = new ArrayList();
        m_extensionsCache = new Bundle[0];
        m_names = new HashSet();
        m_sourceToExtensions = new HashMap();
=======
    private final BundleRevisionImpl m_systemBundleRevision;
    private volatile List<BundleCapability> m_capabilities = Collections.EMPTY_LIST;
    private volatile Set<String> m_exportNames = Collections.EMPTY_SET;
    private volatile Object m_securityContext = null;
    private final List<ExtensionTuple> m_extensionTuples = Collections.synchronizedList(new ArrayList<ExtensionTuple>());

    private static class ExtensionTuple
    {
        private final BundleActivator m_activator;
        private final Bundle m_bundle;
        private volatile boolean m_failed;
        private volatile boolean m_started;

        public ExtensionTuple(BundleActivator activator, Bundle bundle)
        {
            m_activator = activator;
            m_bundle = bundle;
        }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    /**
     * This constructor is used to create one instance per framework instance.
     * The general approach is to have one private static instance that we register
     * with the parent classloader and one instance per framework instance that
     * keeps track of extension bundles and systembundle exports for that framework
     * instance.
     *
     * @param logger the logger to use.
     * @param configMap the configuration to read properties from.
     * @param felix the framework.
     */
    ExtensionManager(Logger logger, Map configMap, Felix felix)
    {
        m_logger = logger;
        m_configMap = configMap;
        m_systemBundleRevision = new ExtensionManagerRevision(felix);
<<<<<<< HEAD
        m_extensions = null;
        m_extensionsCache = null;
        m_names = null;
        m_sourceToExtensions = null;
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

// TODO: FRAMEWORK - Not all of this stuff really belongs here, probably only exports.
        // Populate system bundle header map.
        m_headerMap.put(FelixConstants.BUNDLE_VERSION,
            m_configMap.get(FelixConstants.FELIX_VERSION_PROPERTY));
        m_headerMap.put(FelixConstants.BUNDLE_SYMBOLICNAME,
            FelixConstants.SYSTEM_BUNDLE_SYMBOLICNAME);
        m_headerMap.put(FelixConstants.BUNDLE_NAME, "System Bundle");
        m_headerMap.put(FelixConstants.BUNDLE_DESCRIPTION,
            "This bundle is system specific; it implements various system services.");
        m_headerMap.put(FelixConstants.EXPORT_SERVICE,
            "org.osgi.service.packageadmin.PackageAdmin," +
            "org.osgi.service.startlevel.StartLevel," +
            "org.osgi.service.url.URLHandlers");

        Properties configProps = Util.toProperties(m_configMap);
        // The system bundle exports framework packages as well as
        // arbitrary user-defined packages from the system class path.
        // We must construct the system bundle's export metadata.
        // Get configuration property that specifies which class path
        // packages should be exported by the system bundle.
        String syspkgs =
<<<<<<< HEAD
            (String) m_configMap.get(FelixConstants.FRAMEWORK_SYSTEMPACKAGES);
=======
            "true".equalsIgnoreCase(configProps.getProperty(FelixConstants.USE_PROPERTY_SUBSTITUTION_IN_SYSTEMPACKAGES)) ?
                Util.getPropertyWithSubs(configProps, FelixConstants.FRAMEWORK_SYSTEMPACKAGES) :
                configProps.getProperty(FelixConstants.FRAMEWORK_SYSTEMPACKAGES);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        // If no system packages were specified, load our default value.
        syspkgs = (syspkgs == null)
            ? Util.getDefaultProperty(logger, Constants.FRAMEWORK_SYSTEMPACKAGES)
            : syspkgs;
        syspkgs = (syspkgs == null) ? "" : syspkgs;
        // If any extra packages are specified, then append them.
        String pkgextra =
<<<<<<< HEAD
            (String) m_configMap.get(FelixConstants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
        syspkgs = ((pkgextra == null) || (pkgextra.trim().length() == 0))
            ? syspkgs : syspkgs + "," + pkgextra;
=======
            "true".equalsIgnoreCase(configProps.getProperty(FelixConstants.USE_PROPERTY_SUBSTITUTION_IN_SYSTEMPACKAGES)) ?
                Util.getPropertyWithSubs(configProps, FelixConstants.FRAMEWORK_SYSTEMPACKAGES_EXTRA) :
                configProps.getProperty(FelixConstants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
        syspkgs = ((pkgextra == null) || (pkgextra.trim().length() == 0))
            ? syspkgs : syspkgs + (pkgextra.trim().startsWith(",") ? pkgextra : "," + pkgextra);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        m_headerMap.put(FelixConstants.BUNDLE_MANIFESTVERSION, "2");
        m_headerMap.put(FelixConstants.EXPORT_PACKAGE, syspkgs);

        // The system bundle alsp provides framework generic capabilities
        // as well as arbitrary user-defined generic capabilities. We must
        // construct the system bundle's capabilitie metadata. Get the
        // configuration property that specifies which capabilities should
        // be provided by the system bundle.
        String syscaps =
<<<<<<< HEAD
            (String) m_configMap.get(FelixConstants.FRAMEWORK_SYSTEMCAPABILITIES);
=======
            (String) Util.getPropertyWithSubs(configProps, FelixConstants.FRAMEWORK_SYSTEMCAPABILITIES);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        // If no system capabilities were specified, load our default value.
        syscaps = (syscaps == null)
            ? Util.getDefaultProperty(logger, Constants.FRAMEWORK_SYSTEMCAPABILITIES)
            : syscaps;
        syscaps = (syscaps == null) ? "" : syscaps;
        // If any extra capabilities are specified, then append them.
        String capextra =
<<<<<<< HEAD
            (String) m_configMap.get(FelixConstants.FRAMEWORK_SYSTEMCAPABILITIES_EXTRA);
        syscaps = ((capextra == null) || (capextra.trim().length() == 0))
            ? syscaps : syscaps + "," + capextra;
=======
            (String) Util.getPropertyWithSubs(configProps, FelixConstants.FRAMEWORK_SYSTEMCAPABILITIES_EXTRA);
        syscaps = ((capextra == null) || (capextra.trim().length() == 0))
            ? syscaps : syscaps + (capextra.trim().startsWith(",") ? capextra : "," + capextra);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        m_headerMap.put(FelixConstants.PROVIDE_CAPABILITY, syscaps);
        try
        {
            ManifestParser mp = new ManifestParser(
                m_logger, m_configMap, m_systemBundleRevision, m_headerMap);
            List<BundleCapability> caps = aliasSymbolicName(mp.getCapabilities());
<<<<<<< HEAD
=======
            caps.add(buildNativeCapabilites());
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            appendCapabilities(caps);
        }
        catch (Exception ex)
        {
            m_capabilities = Collections.EMPTY_LIST;
            m_logger.log(
                Logger.LOG_ERROR,
                "Error parsing system bundle export statement: "
                + syspkgs, ex);
<<<<<<< HEAD
=======
        }
    }

    protected BundleCapability buildNativeCapabilites() {
        String osArchitecture = (String)m_configMap.get(FelixConstants.FRAMEWORK_PROCESSOR);
        String osName = (String)m_configMap.get(FelixConstants.FRAMEWORK_OS_NAME);
        String osVersion = (String)m_configMap.get(FelixConstants.FRAMEWORK_OS_VERSION);
        String userLang = (String)m_configMap.get(FelixConstants.FRAMEWORK_LANGUAGE);
        Map<String, Object> attributes = new HashMap<String, Object>();
        
        //Add all startup properties so we can match selection-filters
        attributes.putAll(m_configMap);

        if( osArchitecture != null )
        {
            attributes.put(NativeNamespace.CAPABILITY_PROCESSOR_ATTRIBUTE, NativeLibraryClause.getProcessorWithAliases(osArchitecture));
        }

        if( osName != null)
        {
            attributes.put(NativeNamespace.CAPABILITY_OSNAME_ATTRIBUTE, NativeLibraryClause.getOsNameWithAliases(osName));
        }

        if( osVersion != null)
        {
            attributes.put(NativeNamespace.CAPABILITY_OSVERSION_ATTRIBUTE, new Version(NativeLibraryClause.normalizeOSVersion(osVersion)));
        }

        if( userLang != null)
        {
            attributes.put(NativeNamespace.CAPABILITY_LANGUAGE_ATTRIBUTE, userLang);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }

        return new BundleCapabilityImpl(getRevision(), NativeNamespace.NATIVE_NAMESPACE, Collections.<String, String> emptyMap(), attributes);
    }

    private static List<BundleCapability> aliasSymbolicName(List<BundleCapability> caps)
    {
        if (caps == null)
        {
            return new ArrayList<BundleCapability>(0);
        }

        List<BundleCapability> aliasCaps = new ArrayList<BundleCapability>(caps);

        String[] aliases = {
            FelixConstants.SYSTEM_BUNDLE_SYMBOLICNAME,
            Constants.SYSTEM_BUNDLE_SYMBOLICNAME };

        for (int capIdx = 0; capIdx < aliasCaps.size(); capIdx++)
        {
            BundleCapability cap = aliasCaps.get(capIdx);

            // Need to alias bundle and host capabilities.
            if (cap.getNamespace().equals(BundleRevision.BUNDLE_NAMESPACE)
                || cap.getNamespace().equals(BundleRevision.HOST_NAMESPACE))
            {
                // Make a copy of the attribute array.
                Map<String, Object> aliasAttrs =
                    new HashMap<String, Object>(cap.getAttributes());
                // Add the aliased value.
                aliasAttrs.put(cap.getNamespace(), aliases);
                // Create the aliased capability to replace the old capability.
                cap = new BundleCapabilityImpl(
                    cap.getRevision(),
                    cap.getNamespace(),
                    cap.getDirectives(),
                    aliasAttrs);
                aliasCaps.set(capIdx, cap);
            }

            // Further, search attributes for bundle symbolic name and alias it too.
            for (Entry<String, Object> entry : cap.getAttributes().entrySet())
            {
                // If there is a bundle symbolic name attribute, add the
                // standard alias as a value.
                if (entry.getKey().equalsIgnoreCase(Constants.BUNDLE_SYMBOLICNAME_ATTRIBUTE))
                {
                    // Make a copy of the attribute array.
                    Map<String, Object> aliasAttrs =
                        new HashMap<String, Object>(cap.getAttributes());
                    // Add the aliased value.
                    aliasAttrs.put(Constants.BUNDLE_SYMBOLICNAME_ATTRIBUTE, aliases);
                    // Create the aliased capability to replace the old capability.
                    aliasCaps.set(capIdx, new BundleCapabilityImpl(
                        cap.getRevision(),
                        cap.getNamespace(),
                        cap.getDirectives(),
                        aliasAttrs));
                    // Continue with the next capability.
                    break;
                }
            }
        }

        return aliasCaps;
    }

<<<<<<< HEAD
    public BundleRevision getRevision()
=======
    public BundleRevisionImpl getRevision()
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    {
        return m_systemBundleRevision;
    }

    public Object getSecurityContext()
    {
        return m_securityContext;
    }

    public synchronized void setSecurityContext(Object securityContext)
    {
        m_securityContext = securityContext;
    }

    /**
     * Add an extension bundle. The bundle will be added to the parent classloader
     * and it's exported packages will be added to the module definition
     * exports of this instance. Subsequently, they are available form the
     * instance in it's role as content loader.
     *
     * @param felix the framework instance the given extension bundle comes from.
     * @param bundle the extension bundle to add.
     * @throws BundleException if extension bundles are not supported or this is
     *          not a framework extension.
     * @throws SecurityException if the caller does not have the needed
     *          AdminPermission.EXTENSIONLIFECYCLE and security is enabled.
     * @throws Exception in case something goes wrong.
     */
    synchronized void addExtensionBundle(Felix felix, BundleImpl bundle)
        throws SecurityException, BundleException, Exception
    {
        Object sm = System.getSecurityManager();
        if (sm != null)
        {
            ((SecurityManager) sm).checkPermission(
                new AdminPermission(bundle, AdminPermission.EXTENSIONLIFECYCLE));

            if (!((BundleProtectionDomain) bundle.getProtectionDomain()).impliesDirect(new AllPermission()))
            {
                throw new SecurityException("Extension Bundles must have AllPermission");
            }
        }

        String directive = ManifestParser.parseExtensionBundleHeader((String)
            ((BundleRevisionImpl) bundle.adapt(BundleRevision.class))
                .getHeaders().get(Constants.FRAGMENT_HOST));
<<<<<<< HEAD
=======

        final ClassPathExtenderFactory.ClassPathExtender extender;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

        if (m_extenderBoot != null && Constants.EXTENSION_BOOTCLASSPATH.equals(directive))
        {
            extender = m_extenderBoot;
        }
        // We only support classpath extensions (not bootclasspath).
<<<<<<< HEAD
        if (!Constants.EXTENSION_FRAMEWORK.equals(directive))
        {
            throw new BundleException("Unsupported Extension Bundle type: " +
                directive, new UnsupportedOperationException(
                "Unsupported Extension Bundle type!"));
=======
        else if (!Constants.EXTENSION_FRAMEWORK.equals(directive))
        {
           throw new BundleException("Unsupported Extension Bundle type: " +
                    directive, new UnsupportedOperationException(
                    "Unsupported Extension Bundle type!"));
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }
        else if (m_extenderFramework == null)
        {
            // We don't support extensions
            m_logger.log(bundle, Logger.LOG_WARNING,
                    "Unable to add extension bundle - Maybe ClassLoader is not supported (on java9, try --add-opens=java.base/jdk.internal.loader=ALL-UNNAMED)?");

<<<<<<< HEAD
        try
        {
            // Merge the exported packages with the exported packages of the systembundle.
            List<BundleCapability> exports = null;
            try
            {
                exports = ManifestParser.parseExportHeader(
                    m_logger, m_systemBundleRevision,
                    (String) ((BundleRevisionImpl) bundle.adapt(BundleRevision.class))
                        .getHeaders().get(Constants.EXPORT_PACKAGE),
                    m_systemBundleRevision.getSymbolicName(), m_systemBundleRevision.getVersion());
                exports = aliasSymbolicName(exports);
            }
            catch (Exception ex)
            {
                m_logger.log(
                    bundle,
                    Logger.LOG_ERROR,
                    "Error parsing extension bundle export statement: "
                    + ((BundleRevisionImpl) bundle.adapt(BundleRevision.class))
                        .getHeaders().get(Constants.EXPORT_PACKAGE), ex);
                return;
            }

            // Add the bundle as extension if we support extensions
            if (m_extensionManager != null)
=======
            throw new UnsupportedOperationException(
                    "Unable to add extension bundle.");
        }
        else
        {
            extender = m_extenderFramework;
        }

        Content content = bundle.adapt(BundleRevisionImpl.class).getContent();
        final File file;
        if (content instanceof JarContent)
        {
            file = ((JarContent) content).getFile();
        }
        else if (content instanceof DirectoryContent)
        {
            file = ((DirectoryContent) content).getFile();
        }
        else
        {
            file = null;
        }
        if (file == null)
        {
            // We don't support revision type for extension
            m_logger.log(bundle, Logger.LOG_WARNING,
                    "Unable to add extension bundle - wrong revision type?");

            throw new UnsupportedOperationException(
                    "Unable to add extension bundle.");
        }
        try
        {
            // Merge the exported packages with the exported packages of the systembundle.
            List<BundleCapability> exports = null;
            if (Constants.EXTENSION_FRAMEWORK.equals(directive))
            {
                try
                {
                    exports = ManifestParser.parseExportHeader(
                            m_logger, m_systemBundleRevision,
                            (String) bundle.adapt(BundleRevisionImpl.class).getHeaders().get(Constants.EXPORT_PACKAGE),
                            m_systemBundleRevision.getSymbolicName(), m_systemBundleRevision.getVersion());
                    exports = aliasSymbolicName(exports);
                }
                catch (Exception ex)
                {
                    m_logger.log(
                            bundle,
                            Logger.LOG_ERROR,
                            "Error parsing extension bundle export statement: "
                                    + bundle.adapt(BundleRevisionImpl.class).getHeaders().get(Constants.EXPORT_PACKAGE), ex);
                    return;
                }
            }
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            {
                @Override
                public Void run() throws Exception
                {
                    extender.add(file);
                    return null;
                }
            });
            if (exports != null)
            {
<<<<<<< HEAD
                // We don't support extensions (i.e., the parent is not an URLClassLoader).
                m_logger.log(bundle, Logger.LOG_WARNING,
                    "Unable to add extension bundle to FrameworkClassLoader - Maybe not an URLClassLoader?");
                throw new UnsupportedOperationException(
                    "Unable to add extension bundle to FrameworkClassLoader - Maybe not an URLClassLoader?");
            }
            appendCapabilities(exports);
=======
                appendCapabilities(exports);
            }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }
        catch (Exception ex)
        {
            throw ex;
        }

<<<<<<< HEAD
        BundleRevisionImpl bri = (BundleRevisionImpl) bundle.adapt(BundleRevision.class);
=======
        BundleRevisionImpl bri = bundle.adapt(BundleRevisionImpl.class);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        List<BundleRequirement> reqs = bri.getDeclaredRequirements(BundleRevision.HOST_NAMESPACE);
        List<BundleCapability> caps = getCapabilities(BundleRevision.HOST_NAMESPACE);
        BundleWire bw = new BundleWireImpl(bri, reqs.get(0), m_systemBundleRevision, caps.get(0));
        bri.resolve(
            new BundleWiringImpl(
                m_logger,
                m_configMap,
                null,
                bri,
                null,
                Collections.singletonList(bw),
                Collections.EMPTY_MAP,
                Collections.EMPTY_MAP));
        felix.getDependencies().addDependent(bw);
        felix.setBundleStateAndNotify(bundle, Bundle.RESOLVED);
    }

    /**
     * This is a Felix specific extension mechanism that allows extension bundles
     * to have activators and be started via this method.
     *
     * @param felix the framework instance the extension bundle is installed in.
     * @param bundle the extension bundle to start if it has a Felix specific activator.
     */
    void startExtensionBundle(Felix felix, BundleImpl bundle)
    {
<<<<<<< HEAD
        String activatorClass = (String)
            ((BundleRevisionImpl) bundle.adapt(BundleRevision.class))
                .getHeaders().get(FelixConstants.FELIX_EXTENSION_ACTIVATOR);
=======
        Map<?,?> headers = bundle.adapt(BundleRevisionImpl.class).getHeaders();
        String activatorClass = (String) headers.get(Constants.EXTENSION_BUNDLE_ACTIVATOR);
        boolean felixExtension = false;
        if (activatorClass == null)
        {
            felixExtension = true;
            activatorClass = (String) headers.get(FelixConstants.FELIX_EXTENSION_ACTIVATOR);
        }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

        if (activatorClass != null)
        {
            ExtensionTuple tuple = null;
            try
            {
// TODO: SECURITY - Should this consider security?
                BundleActivator activator = (BundleActivator)
                    felix.getClass().getClassLoader().loadClass(
                        activatorClass.trim()).newInstance();

<<<<<<< HEAD
// TODO: EXTENSIONMANAGER - This is kind of hacky, can we improve it?
                felix.m_activatorList.add(activator);

=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                BundleContext context = felix._getBundleContext();

                bundle.setBundleContext(context);

<<<<<<< HEAD
=======
// TODO: EXTENSIONMANAGER - This is kind of hacky, can we improve it?
                if (!felixExtension)
                {
                    tuple = new ExtensionTuple(activator, bundle);
                    m_extensionTuples.add(tuple);
                }
                else
                {
                    felix.m_activatorList.add(activator);
                }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                if ((felix.getState() == Bundle.ACTIVE) || (felix.getState() == Bundle.STARTING))
                {
                    if (tuple != null)
                    {
                        tuple.m_started = true;
                    }
                    Felix.m_secureAction.startActivator(activator, context);
                }
            }
            catch (Throwable ex)
            {
<<<<<<< HEAD
                m_logger.log(bundle, Logger.LOG_WARNING,
                    "Unable to start Felix Extension Activator", ex);
            }
        }
    }

    /**
     * Remove all extension registered by the given framework instance. Note, it
     * is not possible to unregister allready loaded classes form those extensions.
     * That is why the spec requires a JVM restart.
     *
     * @param felix the framework instance whose extensions need to be unregistered.
     */
    void removeExtensions(Felix felix)
    {
        if (m_extensionManager != null)
        {
            m_extensionManager._removeExtensions(felix);
        }
    }

    private List<BundleCapability> getCapabilities(String namespace)
    {
=======
                if (tuple != null)
                {
                    tuple.m_failed = true;
                }
                felix.fireFrameworkEvent(FrameworkEvent.ERROR, bundle,
                            new BundleException("Unable to start Bundle", ex));

                m_logger.log(bundle, Logger.LOG_WARNING,
                    "Unable to start Extension Activator", ex);
            }
        }
    }

    void startPendingExtensionBundles(Felix felix)
    {
        for (int i = 0;i < m_extensionTuples.size();i++)
        {
            if (!m_extensionTuples.get(i).m_started)
            {
                m_extensionTuples.get(i).m_started = true;
                try
                {
                    Felix.m_secureAction.startActivator(m_extensionTuples.get(i).m_activator, felix._getBundleContext());
                }
                catch (Throwable ex)
                {
                    m_extensionTuples.get(i).m_failed = true;

                    felix.fireFrameworkEvent(FrameworkEvent.ERROR, m_extensionTuples.get(i).m_bundle,
                                new BundleException("Unable to start Bundle", BundleException.ACTIVATOR_ERROR, ex));

                    m_logger.log(m_extensionTuples.get(i).m_bundle, Logger.LOG_WARNING,
                        "Unable to start Extension Activator", ex);
                }
            }
        }
    }

    void stopExtensionBundles(Felix felix)
    {
        for (int i = m_extensionTuples.size() - 1; i >= 0;i--)
        {
            if (m_extensionTuples.get(i).m_started && !m_extensionTuples.get(i).m_failed)
            {
                try
                {
                    Felix.m_secureAction.stopActivator(m_extensionTuples.get(i).m_activator, felix._getBundleContext());
                }
                catch (Throwable ex)
                {
                    felix.fireFrameworkEvent(FrameworkEvent.ERROR, m_extensionTuples.get(i).m_bundle,
                                new BundleException("Unable to stop Bundle", BundleException.ACTIVATOR_ERROR, ex));

                    m_logger.log(m_extensionTuples.get(i).m_bundle, Logger.LOG_WARNING,
                        "Unable to stop Extension Activator", ex);
                }
            }
        }
        m_extensionTuples.clear();
    }

    private List<BundleCapability> getCapabilities(String namespace)
    {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        List<BundleCapability> caps = m_capabilities;
        List<BundleCapability> result = caps;
        if (namespace != null)
        {
            result = new ArrayList<BundleCapability>();
            for (BundleCapability cap : caps)
            {
                if (cap.getNamespace().equals(namespace))
                {
                    result.add(cap);
                }
            }
        }
        return result;
    }

    private synchronized void appendCapabilities(List<BundleCapability> caps)
    {
        List<BundleCapability> newCaps =
            new ArrayList<BundleCapability>(m_capabilities.size() + caps.size());
        newCaps.addAll(m_capabilities);
        newCaps.addAll(caps);
        m_capabilities = ImmutableList.newInstance(newCaps);
        m_headerMap.put(Constants.EXPORT_PACKAGE, convertCapabilitiesToHeaders(m_headerMap));
    }

    private String convertCapabilitiesToHeaders(Map headers)
    {
        StringBuffer exportSB = new StringBuffer("");
        Set<String> exportNames = new HashSet<String>();

        List<BundleCapability> caps = m_capabilities;
        for (BundleCapability cap : caps)
        {
            if (cap.getNamespace().equals(BundleRevision.PACKAGE_NAMESPACE))
            {
                // Add a comma separate if there is an existing package.
                if (exportSB.length() > 0)
                {
                    exportSB.append(", ");
                }

                // Append exported package information.
                exportSB.append(cap.getAttributes().get(BundleRevision.PACKAGE_NAMESPACE));
                for (Entry<String, String> entry : cap.getDirectives().entrySet())
                {
                    exportSB.append("; ");
                    exportSB.append(entry.getKey());
                    exportSB.append(":=\"");
                    exportSB.append(entry.getValue());
                    exportSB.append("\"");
                }
                for (Entry<String, Object> entry : cap.getAttributes().entrySet())
                {
                    if (!entry.getKey().equals(BundleRevision.PACKAGE_NAMESPACE)
                        && !entry.getKey().equals(Constants.BUNDLE_SYMBOLICNAME_ATTRIBUTE)
                        && !entry.getKey().equals(Constants.BUNDLE_VERSION_ATTRIBUTE))
                    {
                        exportSB.append("; ");
                        exportSB.append(entry.getKey());
                        exportSB.append("=\"");
                        exportSB.append(entry.getValue());
                        exportSB.append("\"");
                    }
                }

                // Remember exported packages.
                exportNames.add(
                    (String) cap.getAttributes().get(BundleRevision.PACKAGE_NAMESPACE));
            }
        }

        m_exportNames = exportNames;

        return exportSB.toString();
    }
<<<<<<< HEAD

=======

    public void close()
    {
        // Do nothing on close, since we have nothing open.
    }

    public Enumeration getEntries()
    {
        return new Enumeration()
        {
            public boolean hasMoreElements()
            {
                return false;
            }

            public Object nextElement() throws NoSuchElementException
            {
                throw new NoSuchElementException();
            }
        };
    }

    public boolean hasEntry(String name) {
        return false;
    }

    public byte[] getEntryAsBytes(String name)
    {
        return null;
    }

    public InputStream getEntryAsStream(String name) throws IOException
    {
        return null;
    }

    public Content getEntryAsContent(String name)
    {
        return null;
    }

    public String getEntryAsNativeLibrary(String name)
    {
        return null;
    }

    public URL getEntryAsURL(String name)
    {
        return null;
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    //
    // Utility methods.
    //

<<<<<<< HEAD
    /*
     * See whether any registered extension provides the class requested. If not
     * throw an IOException.
     */
    public URLConnection openConnection(URL url) throws IOException
=======
    class ExtensionManagerRevision extends BundleRevisionImpl
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    {
        private final Version m_version;
        private volatile BundleWiring m_wiring;

        ExtensionManagerRevision(Felix felix)
        {
            super(felix, "0");
            m_version = new Version((String)
                m_configMap.get(FelixConstants.FELIX_VERSION_PROPERTY));
        }

<<<<<<< HEAD
        Bundle[] extensions = m_extensionsCache;
        URL result = null;
        for (Bundle extBundle : extensions)
        {
            try
            {
                BundleRevisionImpl bri =
                    (BundleRevisionImpl) extBundle.adapt(BundleRevision.class);
                if (bri != null)
                {
                    result = bri.getResourceLocal(path);
                }
            }
            catch (Exception ex)
            {
                // Maybe the bundle went away, so ignore this exception.
            }
            if (result != null)
=======
        @Override
        public Map getHeaders()
        {
            synchronized (ExtensionManager.this)
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            {
                return m_headerMap;
            }
        }

<<<<<<< HEAD
        return new URLConnection(url)
            {
                public void connect() throws IOException
                {
                    throw new IOException("Resource not provided by any extension!");
                }
            };
    }

    @Override
    protected InetAddress getHostAddress(URL u)
    {
        // the extension URLs do not address real hosts
        return null;
    }
=======
        @Override
        public List<BundleCapability> getDeclaredCapabilities(String namespace)
        {
            return ExtensionManager.this.getCapabilities(namespace);
        }

        @Override
        public String getSymbolicName()
        {
            return FelixConstants.SYSTEM_BUNDLE_SYMBOLICNAME;
        }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

        @Override
        public Version getVersion()
        {
            return m_version;
        }

        @Override
        public void close()
        {
            // Nothing needed here.
        }

        @Override
        public Content getContent()
        {
            return ExtensionManager.this;
        }

<<<<<<< HEAD
        _add(extension.getSymbolicName(), extension);
        m_extensionsCache = (Bundle[])
                m_extensions.toArray(new Bundle[m_extensions.size()]);
    }
=======
        @Override
        public URL getEntry(String name)
        {
            // There is no content for the system bundle, so return null.
            return null;
        }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

        @Override
        public boolean hasInputStream(int index, String urlPath)
        {
            return (getClass().getClassLoader().getResource(urlPath) != null);
        }

        @Override
        public InputStream getInputStream(int index, String urlPath)
        {
            return getClass().getClassLoader().getResourceAsStream(urlPath);
        }

        @Override
        public URL getLocalURL(int index, String urlPath)
        {
            return getClass().getClassLoader().getResource(urlPath);
        }

        @Override
        public void resolve(BundleWiringImpl wire)
        {
            try
            {
                m_wiring = new ExtensionManagerWiring(
                    m_logger, m_configMap, this);
            }
            catch (Exception ex)
            {
                // This should never happen.
            }
            m_extensionsCache = (Bundle[])
                m_extensions.toArray(new Bundle[m_extensions.size()]);
        }

        @Override
        public BundleWiring getWiring()
        {
            return m_wiring;
        }
    }

<<<<<<< HEAD
    public void close()
    {
        // Do nothing on close, since we have nothing open.
    }

    public Enumeration getEntries()
=======
    class ExtensionManagerWiring extends BundleWiringImpl
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    {
        ExtensionManagerWiring(
            Logger logger, Map configMap, BundleRevisionImpl revision)
            throws Exception
        {
<<<<<<< HEAD
            public boolean hasMoreElements()
            {
                return false;
            }

            public Object nextElement() throws NoSuchElementException
=======
            super(logger, configMap, null, revision,
                null, Collections.EMPTY_LIST, null, null);
        }

        @Override
        public ClassLoader getClassLoader()
        {
            return getClass().getClassLoader();
        }

        @Override
        public List<BundleCapability> getCapabilities(String namespace)
        {
            return ExtensionManager.this.getCapabilities(namespace);
        }

        @Override
        public List<NativeLibrary> getNativeLibraries()
        {
            return Collections.EMPTY_LIST;
        }

        @Override
        public Class getClassByDelegation(String name) throws ClassNotFoundException
        {
            Class clazz = null;
            String pkgName = Util.getClassPackage(name);
            if (shouldBootDelegate(pkgName))
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            {
                try
                {
                    // Get the appropriate class loader for delegation.
                    ClassLoader bdcl = getBootDelegationClassLoader();
                    clazz = bdcl.loadClass(name);
                    // If this is a java.* package, then always terminate the
                    // search; otherwise, continue to look locally if not found.
                    if (pkgName.startsWith("java.") || (clazz != null))
                    {
                        return clazz;
                    }
                }
                catch (ClassNotFoundException ex)
                {
                    // If this is a java.* package, then always terminate the
                    // search; otherwise, continue to look locally if not found.
                    if (pkgName.startsWith("java."))
                    {
                        throw ex;
                    }
                }
            }
            if (clazz == null)
            {
                if (!m_exportNames.contains(Util.getClassPackage(name)))
                {
                    throw new ClassNotFoundException(name);
                }

<<<<<<< HEAD
    public byte[] getEntryAsBytes(String name)
    {
        return null;
    }

    public InputStream getEntryAsStream(String name) throws IOException
    {
        return null;
    }

    public Content getEntryAsContent(String name)
    {
        return null;
    }
=======
                clazz = getClass().getClassLoader().loadClass(name);
            }
            return clazz;
        }

        @Override
        public URL getResourceByDelegation(String name)
        {
            return getClass().getClassLoader().getResource(name);
        }

        @Override
        public Enumeration getResourcesByDelegation(String name)
        {
           try
           {
               return getClass().getClassLoader().getResources(name);
           }
           catch (IOException ex)
           {
               return null;
           }
        }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

        @Override
        public void dispose()
        {
            // Nothing needed here.
        }
    }
<<<<<<< HEAD

    public URL getEntryAsURL(String name)
    {
        return null;
    }

    //
    // Utility methods.
    //

    class ExtensionManagerRevision extends BundleRevisionImpl
    {
        private final Version m_version;
        private volatile BundleWiring m_wiring;

        ExtensionManagerRevision(Felix felix)
        {
            super(felix, "0");
            m_version = new Version((String)
                m_configMap.get(FelixConstants.FELIX_VERSION_PROPERTY));
        }

        @Override
        public Map getHeaders()
        {
            synchronized (ExtensionManager.this)
            {
                return m_headerMap;
            }
        }

        @Override
        public List<BundleCapability> getDeclaredCapabilities(String namespace)
        {
            return ExtensionManager.this.getCapabilities(namespace);
        }

        @Override
        public String getSymbolicName()
        {
            return FelixConstants.SYSTEM_BUNDLE_SYMBOLICNAME;
        }

        @Override
        public Version getVersion()
        {
            return m_version;
        }

        @Override
        public void close()
        {
            // Nothing needed here.
        }

        @Override
        public Content getContent()
        {
            return ExtensionManager.this;
        }

        @Override
        public URL getEntry(String name)
        {
            // There is no content for the system bundle, so return null.
            return null;
        }

        @Override
        public boolean hasInputStream(int index, String urlPath)
        {
            return (getClass().getClassLoader().getResource(urlPath) != null);
        }

        @Override
        public InputStream getInputStream(int index, String urlPath)
        {
            return getClass().getClassLoader().getResourceAsStream(urlPath);
        }

        @Override
        public URL getLocalURL(int index, String urlPath)
        {
            return getClass().getClassLoader().getResource(urlPath);
        }

        @Override
        public void resolve(BundleWiringImpl wire)
        {
            try
            {
                m_wiring = new ExtensionManagerWiring(
                    m_logger, m_configMap, this);
            }
            catch (Exception ex)
            {
                // This should never happen.
            }
        }

        @Override
        public BundleWiring getWiring()
        {
            return m_wiring;
        }
    }

    class ExtensionManagerWiring extends BundleWiringImpl
    {
        ExtensionManagerWiring(
            Logger logger, Map configMap, BundleRevisionImpl revision)
            throws Exception
        {
            super(logger, configMap, null, revision,
                null, Collections.EMPTY_LIST, null, null);
        }

        @Override
        public ClassLoader getClassLoader()
        {
            return getClass().getClassLoader();
        }

        @Override
        public List<BundleCapability> getCapabilities(String namespace)
        {
            return ExtensionManager.this.getCapabilities(namespace);
        }

        @Override
        public List<R4Library> getNativeLibraries()
        {
            return Collections.EMPTY_LIST;
        }

        @Override
        public Class getClassByDelegation(String name) throws ClassNotFoundException
        {
            Class clazz = null;
            String pkgName = Util.getClassPackage(name);
            if (shouldBootDelegate(pkgName))
            {
                try
                {
                    // Get the appropriate class loader for delegation.
                    ClassLoader bdcl = getBootDelegationClassLoader();
                    clazz = bdcl.loadClass(name);
                    // If this is a java.* package, then always terminate the
                    // search; otherwise, continue to look locally if not found.
                    if (pkgName.startsWith("java.") || (clazz != null))
                    {
                        return clazz;
                    }
                }
                catch (ClassNotFoundException ex)
                {
                    // If this is a java.* package, then always terminate the
                    // search; otherwise, continue to look locally if not found.
                    if (pkgName.startsWith("java."))
                    {
                        throw ex;
                    }
                }
            }
            if (clazz == null)
            {
                if (!m_exportNames.contains(Util.getClassPackage(name)))
                {
                    throw new ClassNotFoundException(name);
                }

                clazz = getClass().getClassLoader().loadClass(name);
            }
            return clazz;
        }

        @Override
        public URL getResourceByDelegation(String name)
        {
            return getClass().getClassLoader().getResource(name);
        }

        @Override
        public Enumeration getResourcesByDelegation(String name)
        {
           try
           {
               return getClass().getClassLoader().getResources(name);
           }
           catch (IOException ex)
           {
               return null;
           }
        }

        @Override
        public void dispose()
        {
            // Nothing needed here.
        }
    }
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
}

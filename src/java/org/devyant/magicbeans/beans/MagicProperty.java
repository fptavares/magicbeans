/*
 * Copyright 2005 Filipe Tavares
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devyant.magicbeans.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.conf.MagicConfiguration;

/**
 * MagicProperty is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 0:00:04
 */
public class MagicProperty {
    /**
     * The getter <code>Method</code>.
     */
    private final Method getter;
    /**
     * The setter <code>Method</code>.
     */
    private final Method setter;
    /**
     * The object that contains the property.
     */
    private final Object object;
    /**
     * The property's name.
     */
    private final String name;
    
    /**
     * The class name of the original <code>MagicBean</code>.
     */
    private final String superBeanClassName;
    /**
     * The parent's beanPath + "." + this.name.
     */
    private final String beanPath;
    
    /**
     * This property's configuration.
     */
    private final MagicConfiguration configuration;
    
    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param parent The parent property
     * @param object The object that contains the property
     * @param property The property
     * @throws NoSuchMethodException
     * {@link MagicUtils#getGetterMethod(Class, String)}
     * {@link MagicUtils#getSetterMethod(Class, String)}
     */
    public MagicProperty(final MagicProperty parent, Object object,
            final String property) throws NoSuchMethodException {
        this(parent.getSuperBeanClassName(), parent.getBeanPath(),
                object, property, false);
    }
    
    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param parent The parent property
     * @param object The object that contains the property
     * @param getter The getter method for the property
     * @param setter The setter method for the property
     */
    public MagicProperty(final MagicProperty parent, final Object object,
            final Method getter, final Method setter) {
        this.object = object;
        this.getter = getter;
        this.setter = setter;
        this.name = MagicUtils.decapitalize(setter.getName().substring(3));
        
        this.superBeanClassName = parent.getSuperBeanClassName();
        if (parent.getBeanPath() == null) {                     // first node
            this.beanPath = "";
        } else if (StringUtils.isBlank(parent.getBeanPath())) { // 2nd (avoid initial '.')
            this.beanPath = this.name;
        } else {                       // third node (simple append situation)
            this.beanPath = parent.getBeanPath() + "." + this.name;
        }
        
        this.configuration =
            new MagicConfiguration(this.superBeanClassName, this.beanPath);
    }
    
    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param parent The parent property
     * @param object The object that contains the property
     * @param property The property
     * @param allowNullMethods Getter and setter methods may be unavailable
     * @throws NoSuchMethodException
     * {@link MagicUtils#getGetterMethod(Class, String)}
     * {@link MagicUtils#getSetterMethod(Class, String)}
     */
    public MagicProperty(final MagicProperty parent, Object object,
            String property, boolean allowNullMethods)
            throws NoSuchMethodException {
        this(parent.getSuperBeanClassName(), parent.getBeanPath(),
                object, property, allowNullMethods);
    }

    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param className The class name of the original <code>MagicBean</code>
     * @param beanPath The parent's beanPath
     * @param object The object that contains the property
     * @param property The property
     * @param allowNullMethods Getter and setter methods may be unavailable
     * @throws NoSuchMethodException
     * {@link MagicUtils#getGetterMethod(Class, String)}
     * {@link MagicUtils#getSetterMethod(Class, String)}
     */
    public MagicProperty(String className, String beanPath, Object object,
            String property, boolean allowNullMethods)
            throws NoSuchMethodException {
        
        if (allowNullMethods) {
            Method getter = null;
            try {
                getter = MagicUtils.getGetterMethod(object.getClass(), property);
            } catch (NoSuchMethodException e) {
            } finally {
                this.getter = getter;
            }
            Method setter = null;
            try {
                setter = MagicUtils.getSetterMethod(object.getClass(), property);
            } catch (NoSuchMethodException e) {
            } finally {
                this.setter = setter;
            }
        } else {
            this.getter = MagicUtils.getGetterMethod(object.getClass(), property);
            this.setter = MagicUtils.getSetterMethod(object.getClass(), property);
        }
        
        this.object = object;
        this.name = MagicUtils.decapitalize(property);
        
        this.superBeanClassName = className;
        if (beanPath == null) {                     // first node
            this.beanPath = "";
        } else if (StringUtils.isBlank(beanPath)) { // 2nd (avoid initial '.')
            this.beanPath = this.name;
        } else {                       // third node (simple append situation)
            this.beanPath = beanPath + "." + this.name;
        }
        
        this.configuration =
            new MagicConfiguration(this.superBeanClassName, this.beanPath);
    }

    /**
     * @return The property's value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public final Object get() throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        return getter.invoke(object, new Object[0]);
    }
    
    /**
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public final void set(final Object value) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        if (setter != null) { // Objects need not to be set, they may be final
            setter.invoke(object, new Object[] {value});
        }
    }
    
    /**
     * @return The property's class
     */
    public final Class getType() {
        return getter.getReturnType();
    }
    
    /**
     * @return Returns the name of the property.
     */
    public final String getName() {
        return name;
    }

    /**
     * @return Returns the beanPath.
     */
    public String getBeanPath() {
        return beanPath;
    }

    /**
     * @return Returns the superBeanClassName.
     */
    public String getSuperBeanClassName() {
        return superBeanClassName;
    }

    /**
     * The getter method for the configuration property.
     * @return The property's <code>MagicProperty</code> value
     */
    public MagicConfiguration getConfiguration() {
        return configuration;
    }
}

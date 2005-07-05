/*
 * Magic Beans: a library for GUI generation and component-bean mapping.
 * Copyright (C) 2005  Filipe Tavares
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * devYant, devyant@devyant.org
 * Rua Simao Bolivar 203 6C, 4470-214 Maia, Portugal.
 *
 */
package org.devyant.magicbeans.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.PropertyException;

/**
 * MagicProperty is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 0:00:04
 */
public class MagicProperty {
    /**
     * The ACCESS_ERROR_MESSAGE <code>String</code>.
     */
    private static final String ACCESS_ERROR_MESSAGE =
        "Could not find one of the accessors for the property: ";
    /**
     * The SETTER_EXCEPTION_MESSAGE <code>String</code>.
     */
    private static final String SETTER_EXCEPTION_MESSAGE =
        "Couldn't call setter method for the property: ";
    /**
     * The GETTER_EXCEPTION_MESSAGE <code>String</code>.
     */
    private static final String GETTER_EXCEPTION_MESSAGE =
        "Couldn't call getter method for the property: ";
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
     * @throws PropertyException
     */
    public MagicProperty(final MagicProperty parent, Object object,
            final String property) throws PropertyException {
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
     * @throws PropertyException
     */
    public MagicProperty(final MagicProperty parent, Object object,
            String property, boolean allowNullMethods)
            throws PropertyException {
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
     * @throws PropertyException
     * {@link MagicUtils#getGetterMethod(Class, String)}
     * {@link MagicUtils#getSetterMethod(Class, String, Class)}
     */
    public MagicProperty(String className, String beanPath, Object object,
            String property, boolean allowNullMethods)
            throws PropertyException {
        
        this.object = object;
        this.name = MagicUtils.decapitalize(property);
        
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
                setter = MagicUtils.getSetterMethod(object.getClass(), property,
                        getter.getReturnType());
            } catch (NoSuchMethodException e) {
            } finally {
                this.setter = setter;
            }
        } else {
            try {
                this.getter = MagicUtils.getGetterMethod(
                        object.getClass(), property);
                this.setter = MagicUtils.getSetterMethod(
                        object.getClass(), property, getter.getReturnType());
            } catch (NoSuchMethodException e) {
                throw new PropertyException(ACCESS_ERROR_MESSAGE + name, e);
            }
        }
        
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
     * Call getter method for the property.
     * @return The property's value
     * @throws PropertyException Could not call getter
     */
    public final Object get() throws PropertyException {
        try {
            return getter.invoke(object, new Object[0]);
        } catch (IllegalArgumentException e) {
            throw new PropertyException(GETTER_EXCEPTION_MESSAGE + name, e);
        } catch (IllegalAccessException e) {
            throw new PropertyException(GETTER_EXCEPTION_MESSAGE + name, e);
        } catch (InvocationTargetException e) {
            throw new PropertyException(GETTER_EXCEPTION_MESSAGE + name, e);
        }
    }
    
    /**
     * Call setter method for the property.
     * @param value The value to be set
     * @throws PropertyException Could not call setter
     */
    public final void set(final Object value) throws PropertyException {
        if (setter != null) { // Objects need not to be set, they may be final
            try {
                setter.invoke(object, new Object[] {value});
            } catch (IllegalArgumentException e) {
                throw new PropertyException(SETTER_EXCEPTION_MESSAGE + name, e);
            } catch (IllegalAccessException e) {
                throw new PropertyException(SETTER_EXCEPTION_MESSAGE + name, e);
            } catch (InvocationTargetException e) {
                throw new PropertyException(SETTER_EXCEPTION_MESSAGE + name, e);
            }
        }
    }
    
    /**
     * @return The property's class
     */
    public final Class getType() {
        try {
            final Object o;
            o = this.get();
            if (o != null) {
                return o.getClass();
            }
        } catch (final PropertyException e) {
        }
        
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

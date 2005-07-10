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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
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
            final String property) {
        this(parent.getSuperBeanClassName(), parent.getBeanPath(),
                object, property);
    }

    /**
     * Creates a new <code>MagicProperty</code> instance.
     * @param className The class name of the original <code>MagicBean</code>
     * @param beanPath The parent's bean path
     * @param object The object that contains the property
     * @param property The property
     * @param ignore Do not include this property in the bean path
     * @param allowNullMethods Getter and setter methods may be unavailable
     * @throws PropertyException
     * {@link MagicUtils#getGetterMethod(Class, String)}
     * {@link MagicUtils#getSetterMethod(Class, String, Class)}
     */
    public MagicProperty(final String className, final String beanPath,
            Object object, final String property) {
        
        this.object = object;
        this.name = MagicUtils.decapitalize(property);
        
        
        
        this.superBeanClassName = className;
        if (beanPath == null) {                      // first node
            this.beanPath = "";
        } else if (object instanceof AuxiliarBean) { // ignore this node
            this.beanPath = beanPath;
        } else if (StringUtils.isBlank(beanPath)) {  // 2nd (avoid initial '.')
            this.beanPath = this.name;
        } else {                       // third node (simple append situation)
            this.beanPath = beanPath + "." + this.name;
        }
        
        this.configuration = new MagicConfiguration(this);
    }

    /**
     * Call getter method for the property.
     * @return The property's value
     * @throws PropertyException Could not call getter
     */
    public final Object get() throws PropertyException {
        try {
            return PropertyUtils.getProperty(object, name);
        } catch (IllegalArgumentException e) {
            throw new PropertyException(GETTER_EXCEPTION_MESSAGE + name, e);
        } catch (IllegalAccessException e) {
            throw new PropertyException(GETTER_EXCEPTION_MESSAGE + name, e);
        } catch (InvocationTargetException e) {
            throw new PropertyException(GETTER_EXCEPTION_MESSAGE + name, e);
        } catch (NoSuchMethodException e) {
            throw new PropertyException(GETTER_EXCEPTION_MESSAGE + name, e);
        }
    }
    
    /**
     * Call setter method for the property.
     * @param value The value to be set
     * @throws PropertyException Could not call setter
     */
    public final void set(final Object value) throws PropertyException {
            try {
                PropertyUtils.setProperty(object, name, value);
            } catch (IllegalArgumentException e) {
                throw new PropertyException(SETTER_EXCEPTION_MESSAGE + name, e);
            } catch (IllegalAccessException e) {
                throw new PropertyException(SETTER_EXCEPTION_MESSAGE + name, e);
            } catch (InvocationTargetException e) {
                throw new PropertyException(SETTER_EXCEPTION_MESSAGE + name, e);
            } catch (NoSuchMethodException e) {
                throw new PropertyException(SETTER_EXCEPTION_MESSAGE + name, e);
            }
    }
    
    /**
     * @return The property's class
     * @throws MagicException
     *  When some problem occures while checking for the type
     */
    public final Class getType() throws MagicException {
        try {
            final Object o;
            o = this.get();
            if (o != null) {
                return o.getClass();
            }
        } catch (final PropertyException e) {
        }
        
        try {
            return PropertyUtils.getPropertyType(object, name);
        } catch (IllegalAccessException e) {
            throw new MagicException(e);
        } catch (InvocationTargetException e) {
            throw new MagicException(e);
        } catch (NoSuchMethodException e) {
            throw new MagicException(e);
        }
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
    
    public boolean isAuxiliarBean() {
        return (object instanceof AuxiliarBean);
    }
}

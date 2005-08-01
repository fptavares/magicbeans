package org.devyant.magicbeans;

import java.awt.Component;
import java.awt.Container;

import javax.swing.AbstractButton;

import junit.extensions.abbot.ComponentTestFixture;
import abbot.finder.ComponentNotFoundException;
import abbot.finder.Matcher;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.ComponentTester;

/**
 * AbstractTestCase is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 3, 2005 4:57:23 AM
 */
public abstract class AbstractTestCase
    extends ComponentTestFixture {
    
    /**
     * The tester <code>ComponentTester</code>.
     */
    protected ComponentTester tester;

    /**
     * Constructor.
     */
    public AbstractTestCase(String testName)
    {
        super(testName);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        tester = new ComponentTester();
    }

    /**
     * @param bean
     * @throws Exception
     */
    protected void testComponent(Object bean,
            final Class componentClass, Object expected) throws Exception {
        
        final Container container = new MagicBean(bean).renderContainer();
        showFrame(container);
        
        final Component component =
            getFinder().find(new ClassMatcher(componentClass));
        messWithComponent(component, expected);
        
        final Component button = getFinder().find(new Matcher() {
                public boolean matches(Component c) {
                    return AbstractButton.class.isAssignableFrom(c.getClass())
                        && c.getParent() == container
                        && "Update".equals(((AbstractButton) c).getText());
                }
            });
        tester.actionClick(button);
        
        assertEquals("The component didn't return the expected value.",
                expected, getValue(bean));
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() {
        // Default JUnit test runner keeps references to Tests for its
        // lifetime, so TestCase fields won't be GC'd for the lifetime of the
        // runner. 
        tester = null;
    }
    
    protected abstract void messWithComponent(
            Component component, Object expected)
            throws ComponentNotFoundException, MultipleComponentsFoundException;
    protected abstract Object getValue(Object bean);
}


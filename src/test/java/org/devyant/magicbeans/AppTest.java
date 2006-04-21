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
package org.devyant.magicbeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * AppTest is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 14:06:27
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Visually test Magic Beans...
     * @throws Exception
     */
    public static void testApp() throws Exception
    {
        final MagicBean bean = new MagicBean(new Dummy());
        
        bean.showFrame(null, new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                MagicUtils.debug(bean.getValue().toString());
                System.exit(0);
            }
        });
        
        /*final Container c = bean.render();
        JFrame frame =
            new JFrame("test");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });
        frame.setPreferredSize(new Dimension(300, 600));
        frame.setContentPane(c);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
    }
    
    /**
     * @param args
     * @throws Exception
     */
    public static final void main(String [] args) throws Exception {
        testApp();
    }

    public static class Dummy {
        private String name = "Dummy";
        private String description = "A dummy class for testing";
        private boolean itIsTrue = true;
        private double number = 6.85;
        /*private File bye = new File("/home/ftavares/someFile");
        private Calendar date = Calendar.getInstance();*/
        private Dummy2 dummy = new Dummy2(new Dummy3());
        
        public boolean isItIsTrue() {
            return this.itIsTrue;
        }
        public void setItIsTrue(boolean itIsTrue) {
            this.itIsTrue = itIsTrue;
        }
        public double getNumber() {
            return this.number;
        }
        public void setNumber(double number) {
            this.number = number;
        }
        public String getDescription() {
            return description;
        }
        public final void setDescription(String description) {
            this.description = description;
        }
        public String getName() {
            return name;
        }
        public final void setName(String name) {
            this.name = name;
        }
        public final boolean validateName() {
            if (name.length() > 5) {
                return true;
            } else {
                return false;
            }
        }
        public final Dummy2 getDummy() {
            return dummy;
        }
        public final void setDummy(Dummy2 dummy) {
            this.dummy = dummy;
        }
        /*public final Calendar getDate() {
            return date;
        }
        public final void setDate(Calendar date) {
            this.date = date;
        }
        public File getBye() {
            return bye;
        }
        public void setBye(File bye) {
            this.bye = bye;
        }
        
        public final String toString() {
            return "\t" + name
                + "\n\t" + description
                + "\n\t" + bye
                + "\n\t" + date
                + "\n\t" + dummy;
        }*/
    }
    public static class Dummy2 {
        private String name = "Dummy2";
        private Object something;
        public Dummy2(Object object) {
            this.something = object;
            Random r = new Random();
            for (int i = 0; i < 6; i++) {
                collection.add(RandomStringUtils.randomAlphanumeric(r.nextInt(48)+4));
            }
        }
        public Object getSomething() {
            return this.something;
        }
        public void setSomething(Object something) {
            this.something = something;
        }

        private boolean foo = true;
        private int bye = 3;
        private Collection collection = new ArrayList();
        
        public int getBye() {
            return bye;
        }
        public final void setBye(int bye) {
            this.bye = bye;
        }
        public boolean isFoo() {
            return this.foo;
        }
        public void setFoo(boolean foo) {
            this.foo = foo;
        }
        public String getName() {
            return name;
        }
        public final void setName(String name) {
            this.name = name;
        }
        public final Collection getCollection() {
            return collection;
        }
        public final void setCollection(Collection collection) {
            this.collection = collection;
        }
        
        public final boolean validateName() {
            if (name.length() > 5) {
                return true;
            } else {
                return false;
            }
        }
        
        public final String toString() {
            return "\t" + name
                + "\n\t" + foo
                + "\n\t" + bye
                + "\n\t" + collection;
        }
    }
    public static class Dummy3 {
        private String name = "Dummy3";
        public String getName() {
            return name;
        }
        public final void setName(String name) {
            this.name = name;
        }
    }
}

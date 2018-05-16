/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The type String operation test.
 *
 * @author Darkus Nightmare
 */
public class StringOperationTest {

    /**
     * Instantiates a new String operation test.
     */
    public StringOperationTest() {
    }

    /**
     * Sets up class.
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Tear down class.
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
    }

    /**
     * Capital letters split.
     */
    @Test
    public void capitalLettersSplit(){
        String[] r = "strategicGoal".split("(?=\\p{Upper})");
        System.out.println(StringUtils.join(r," ").toLowerCase());
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

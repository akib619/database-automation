import java.util.List;

import org.testng.Assert;

import db_operation.CRUD;
import db_operation.ProcessTestData;

import org.testng.annotations.*;
import models.TestTable;

public class DatabaseTest {

    @Test
    public void testDB() {
        CRUD crud = new CRUD();
        TestTable testTable = new TestTable();
        testTable.setId(353);
        testTable.setName("KS-5117 PSS: Testing data 4");
        testTable.setStatusID(2);
        testTable.setMethod_name("com.nexage.tests4.test4/#xTest4");
        testTable.setProject_id(7);
        testTable.setSession_id(5);
        testTable.setEnv("GRIGORYEVS");
        crud.add(testTable);
        

        TestTable checkTestTable = new TestTable();
        Assert.assertNotNull(checkTestTable, "Information added.");


        testTable.setId(352);
        testTable.setName("KS-5113 PSS: Testing data 3");
        testTable.setStatusID(3);
        testTable.setMethod_name("com.nexage.tests3.test3/#xTest3");
        testTable.setProject_id(1);
        testTable.setSession_id(2);
        testTable.setEnv("GRIGORYEVS");
        crud.update(testTable);

        int targetId = 353;
        TestTable result = crud.get(targetId);
        if (result != null) {
            Assert.assertEquals(result.getId() + "| " + result.getName() + "| " + result.getMethod_name(),
                "353| KS-5117 PSS: Testing data 4| com.nexage.tests4.test4/#xTest4", "Printed output doesn't match");
        } else {
            Assert.assertNotNull(result, "Record not found for ID: " + targetId);
        }
    }

    @Test
    public void testProcessData(){
        ProcessTestData processTestData = new ProcessTestData();

        List<TestTable> tests = processTestData.getTestsByPattern("66");
        processTestData.copyTests(tests, "Current Project", "Author");
    
        processTestData.updateTests(tests);
    
        for (TestTable test : tests) {
            Assert.assertEquals(test.getBrowser(), "chrome", "Test is completed");
        }
    
   
        processTestData.deleteTests(tests);
    
        for (TestTable test : tests) {
            TestTable deletedTest = processTestData.get(test.getId());
            Assert.assertNull(deletedTest, "The record has been deleted");
        }

    }
    
}

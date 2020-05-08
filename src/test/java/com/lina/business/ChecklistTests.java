package com.lina.business;

import com.lina.checklistproject.Checklist;
import com.lina.dao.ChecklistDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.RowMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Lina
 */

@RunWith(MockitoJUnitRunner.class)
public class ChecklistTests {
    
    @Mock
    private ChecklistDAO mockDAO;
    
    private ChecklistBusiness checklistBusiness;
    
    private ResultSet mockResultSet;
    
    public ChecklistTests(){
    }
    
    @Before
    public void setMockDao(){
        this.checklistBusiness = new ChecklistBusiness(mockDAO);
    
    }
    @Test
    public void testSuccessAddChecklist(){
        try{
            Checklist checklist = new Checklist("To do");
            when(mockDAO.doesChecklistNameExist(checklist)).thenReturn(false);
            checklistBusiness.insert(checklist);
        }catch(Exception e){
            Assert.fail("Essa inclusao nao deveria dar exceção");
        }
    }
    
    @Test
    public void testFailAddChecklistIsNull(){
        try{
            Checklist checklist = null;
            checklistBusiness.insert(checklist);
            Assert.fail("This insert should not give this exception!");
        }catch(Exception e){
            String expectedMessage = "You should inform the checklist's name!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailAddNameIsEmpty(){
        try{
            Checklist checklist = new Checklist("");
            checklistBusiness.insert(checklist);
            Assert.fail("This insert not give this exception!");
        }catch(Exception e){
            String expectedMessage = "You should inform the checklist's name!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailAddChecklistNameExist(){
        try{
            Checklist checklist = new Checklist("To do");
            when(mockDAO.doesChecklistNameExist(checklist)).thenReturn(true);
            checklistBusiness.insert(checklist);
            Assert.fail("This insert should not give this exception");
        }catch(Exception e){
            String expectedMessage = "The item is already created in the list!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testFailAddChecklistThrowSQLException(){
        try{
            Checklist checklist = new Checklist("To do");
            when(mockDAO.doesChecklistNameExist(checklist)).thenThrow(new SQLException());
            checklistBusiness.insert(checklist);
            Assert.fail("This insert should not give this exception!");
        }catch(Exception e){
            String expectedMessage = "Connection wasn't done!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testSuccessUpdateChecklist(){
        try{
            Checklist checklist = new Checklist("To do");
            checklist.setId(1);
            when(mockDAO.doesChecklistNameExist(checklist)).thenReturn(false);
            when(mockDAO.update(checklist)).thenReturn(1);
            checklistBusiness.update(checklist);
        }catch(Exception e){
            Assert.fail("This insert should not give this exception!");
        }
    }
    
    @Test
    public void testFailUpdateChecklistIsNull(){
        try{
            Checklist checklist = null;
            checklistBusiness.update(checklist);
            Assert.fail("This update should not give this exception");
        }catch(Exception e){
            String expectedMessage = "You should inform the checklist's name!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailUpdateChecklistNameIsEmpty(){
        try{
            Checklist checklist = new Checklist("");
            checklistBusiness.update(checklist);
            Assert.fail("This update should not give this exception");
        }catch(Exception e){
            String expectedMessage = "You should inform the checklist's name!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailUpdateChecklistNameExist(){
        try{
            Checklist checklist = new Checklist("To do");
            checklist.setId(1);
            when(mockDAO.doesChecklistNameExist(checklist)).thenReturn(true);
            checklistBusiness.update(checklist);
            Assert.fail("This update should not give this exception");
        }catch(Exception e){
            String expectedMessage = "The item is already created in the list!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailUpdateChecklist(){
        try{
            Checklist checklist = new Checklist("To do");
            checklist.setId(1);
            when(mockDAO.update(checklist)).thenReturn(0);
            checklistBusiness.update(checklist);
            Assert.fail("This update should not give this exception");
        }catch(Exception e){
            String expectedMessage = "It was not possible to update the checklist!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailUpdateChecklistThrowSQLException(){
        try{
            Checklist checklist = new Checklist("To do");
            when(mockDAO.update(checklist)).thenThrow(new SQLException());
            checklistBusiness.update(checklist);
            Assert.fail("This update should not give this exception!");
        }catch(Exception e){
            String expectedMessage = "Connection wasn't done!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
        
    }
    
    @Test
    public void testSuccessListAll(){
        try{
            List<Checklist> listAll = new ArrayList<>();
            when(mockDAO.listAll()).thenReturn(listAll);
            checklistBusiness.listAllChecklist();
        }catch(Exception e){
            Assert.fail("This query should not give this exception!");
        }
    }
    
    @Test
    public void testFailListAllChecklistThrowSQLException(){
        try{
            when(mockDAO.listAll()).thenThrow(new SQLException());
            checklistBusiness.listAllChecklist();
            Assert.fail("This query should not give this exception");
        }catch(Exception e){
            String expectedMessage = "Connection wasn't done!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testSuccessDelete(){
        try{
            when(mockDAO.delete(1)).thenReturn(1);
            checklistBusiness.delete(1);
        }catch(Exception e){
            Assert.fail("This delete should not give this exception!");
        }
    }
    
    @Test
    public void testFailDeleteIdZero(){
        try{
            checklistBusiness.delete(0);
            Assert.fail("The delete should not give this exception!");
        }catch(Exception e){
            String expectedMessage = "You should inform checklist's id!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailDelete(){
        try{
            when(mockDAO.delete(1)).thenReturn(0);
            checklistBusiness.delete(1);
            Assert.fail("This delete should not give this exception!");
        }catch(Exception e){
            String expectedMessage = "It was not possible to delete the checklist!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testFailDeleteThrowSQLException(){
        try{
            when(mockDAO.delete(1)).thenThrow(new SQLException());
            checklistBusiness.delete(1);
            Assert.fail("This delete should not give this exception!");
        }catch(Exception e){
            String expectedMessage = "Connection wasn't done!";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
}

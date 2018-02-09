package test.statistics;

import org.junit.Before;
import org.junit.Test;
import statistics.Const;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstTest {

    private List<Integer> scenarioId;

    @Before
    public void createScenarioList() {
        scenarioId = new ArrayList<>();
        scenarioId.add(1);
        scenarioId.add(2);
    }

    @Test
    public void testCreateBoxplotName() {
        String boxplotNameTest = Const.instance.createBoxplotName(scenarioId);
        String boxplotName = "boxplot_s_1_2.pdf";
        assertEquals(boxplotName,boxplotNameTest);
    }

    @Test
    public void testCreateStripchartName() {
        String stripchartNameTest = Const.instance.createStripchartName(scenarioId);
        String stripchartName = "stripchart_s_1_2.pdf";
        assertEquals(stripchartName,stripchartNameTest);
    }

    @Test
    public void testCreateScenarioShortname() {
        String test = Const.instance.createScenarioShortname(scenarioId);
        String expected = "s1,s2";
        assertEquals(expected,test);
    }

    @Test
    public void testCreateScenarioName() {
        String test = Const.instance.createScenarioName(scenarioId);
        String expected = "\"Szenario 1\",\"Szenario 2\"";
        assertEquals(expected,test);
    }

    @Test
    public void testCreateScenariodescriptionBarplot() {
        assertNotNull(Const.instance.getScenariodescription_barplot(scenarioId));
    }

    @Test
    public void testWriteCsvInScenarios() {
        assertNotNull(Const.instance.writeCsvInScenarios(scenarioId));
    }

    @Test
    public void testCreateStripchartScenarios() {
        assertNotNull(Const.instance.createStripchartScenarios(scenarioId));
    }

    @Test
    public void testCreateDotplotName() {
        assertNotNull(Const.instance.createDotplotName(scenarioId));
    }

    @Test
    public void testCreateMffs() {
        assertNotNull(Const.instance.createMffs(scenarioId));
    }

    @Test
    public void testBuildFileBeginning() throws IOException {
        assertNotNull(Const.instance.buildFileBeginning(scenarioId,"src/statistics/RTemplates/measures.R.tpl"));
    }

    @Test
    public void testCreateTTestText() {
        assertNotNull(Const.instance.createTTestText(scenarioId));
    }

    @Test
    public void testCreateMedianScenario() {
        assertNotNull(Const.instance.createMedianScenario(scenarioId));
    }

    @Test
    public void testCreateMeanScenario() {
        assertNotNull(Const.instance.createMeanScenario(scenarioId));
    }

    @Test
    public void testCreateHistogramScenario() {
        assertNotNull(Const.instance.createHistogramScenarios(scenarioId));
    }

    @Test
    public void testCreateSdScenario() {
        assertNotNull(Const.instance.createSdScenario(scenarioId));
    }

    @Test
    public void testCreateRangeScenario() {
        assertNotNull(Const.instance.createRangeScenario(scenarioId));
    }

    @Test
    public void testCreateInterquartilerangeScenario() {
        assertNotNull(Const.instance.createInterquartilerangeScenario(scenarioId));
    }

    @Test
    public void testCreateQuantile() {
        assertNotNull(Const.instance.createQuantile(scenarioId,0.25));
    }

    @Test
    public void testCreateQuantileTo() {
        assertNotNull(Const.instance.createQuantileTo(scenarioId,0.25,0.75));
    }

    @Test
    public void testCreateQuantileRange() {
        assertNotNull(Const.instance.createQuantileRange(scenarioId,0.25,0.75));
    }

    @Test
    public void testCreateHistogramMinAndMax() {
        assertNotNull(Const.instance.createHistogramMinAndMax(scenarioId));
    }




}

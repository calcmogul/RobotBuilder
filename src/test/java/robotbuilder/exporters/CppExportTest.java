
package robotbuilder.exporters;

import org.junit.*;
import robotbuilder.TestUtils;
import robotbuilder.data.RobotComponent;
import robotbuilder.data.RobotWalker;
import robotbuilder.extensions.Extensions;
import robotbuilder.robottree.RobotTree;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 *
 * @author alex
 */
public class CppExportTest {

    private static final String PROJECT_DIRECTORY = "RobotBuilderTestProjectCpp";

    public CppExportTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Extensions.init();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        File project = new File("build/test-resources/" + PROJECT_DIRECTORY);
        TestUtils.delete(project);
        assertFalse(project.exists());
        project.mkdir();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCPPExport() throws IOException, InterruptedException {
        RobotTree tree = TestUtils.generateTestTree();
        tree.getRoot().setName(PROJECT_DIRECTORY);
        tree.getRoot().getProperty("Export Directory").setValueAndUpdate(new File("build/test-resources/").getAbsolutePath());
        tree.walk(new RobotWalker() {
            @Override
            public void handleRobotComponent(RobotComponent self) { // Gives us better diagnostics when the robot tree isn't valid.
                assertTrue("Component not valid: " + self + ": " + self.getErrorMessage(), self.isValid());
            }
        });
        assertTrue("Robot tree is not valid.", tree.isRobotValid()); // Fails early instead of opening up a window to report failure.
        GenericExporter exporter = new GenericExporter("/export/cpp/");
        exporter.post_export_action = null;
        exporter.export(tree);

        assertEquals("Exit value should be 0, compilation failed.", 0, TestUtils.runBuild(PROJECT_DIRECTORY));

        //export and build a second time
        exporter.export(tree);

        assertEquals("Exit value should be 0, compilation failed.", 0, TestUtils.runBuild(PROJECT_DIRECTORY));
    }
}

package create.perf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class CreatePerfTest {

    WebDriver driver;
    WebDriverWait wait;

    //Robot
    final String ROBOT_SCM_PROJECT_NAME = "robotTFParamCheck";
    final String ROBOT_SCM_PROJECT_BRANCH = "perf";
    final String ROBOT_PROJECT_NAME = "robot_perf";
    final String ROBOT_BASE_TEST_NAME = "case";
    final String ROBOT_BASE_TEST_REFERENCE_OK = "robotTFParamCheck/robot_community.robot#Check Default Param ";
    final String ROBOT_BASE_TEST_REFERENCE_KO = "robotTFParamCheck/robot_community.robot#Check Default Param Failure ";
    final String ROBOT_TEST_TECHNOLOGY = "Robot Framework";
    final String ROBOT_FOLDER_NAME = "folder";

    //JUnit
    final String JUNIT_SCM_PROJECT_NAME = "junitCalc";
    final String JUNIT_SCM_PROJECT_BRANCH = "perfsameclass";
    final String JUNIT_PROJECT_NAME = "junit_perf";
    final String JUNIT_BASE_TEST_NAME = "case";
    final String JUNIT_BASE_TEST_REFERENCE_OK = "junitCalc/squash.tfauto.CalculatorTest#addSuccess";
    final String JUNIT_BASE_TEST_REFERENCE_KO = "junitCalc/squash.tfauto.CalculatorTest#multFailure";
    final String JUNIT_TEST_TECHNOLOGY = "JUnit";
    final String JUNIT_FOLDER_NAME = "folder";

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.get("http://127.0.0.1:8080/squash/login");
        wait = new WebDriverWait(driver, 60);
    }

    @Test
    public void createPerfTest() {

        login(driver, wait);

        //Robot

        addProject(driver, wait, ROBOT_PROJECT_NAME);

        addSCMServer(driver, wait, ROBOT_SCM_PROJECT_NAME, ROBOT_SCM_PROJECT_BRANCH);

        addFolder(driver, wait, ROBOT_PROJECT_NAME, ROBOT_FOLDER_NAME);

        for(int i = 1; i <251; i++) {
            addTestCase(driver,
                        wait,
                        ROBOT_BASE_TEST_NAME + "OK" + i,
                        ROBOT_BASE_TEST_REFERENCE_OK + i,
                        ROBOT_TEST_TECHNOLOGY,
                        ROBOT_PROJECT_NAME,
                        ROBOT_FOLDER_NAME,
                        ROBOT_SCM_PROJECT_NAME,
                        ROBOT_SCM_PROJECT_BRANCH);
            addTestCase(driver,
                        wait,
                        ROBOT_BASE_TEST_NAME + "KO" + i,
                        ROBOT_BASE_TEST_REFERENCE_KO + i,
                        ROBOT_TEST_TECHNOLOGY,
                        ROBOT_PROJECT_NAME,
                        ROBOT_FOLDER_NAME,
                        ROBOT_SCM_PROJECT_NAME,
                        ROBOT_SCM_PROJECT_BRANCH);
        }

        createIterationTestPlan(driver, wait, ROBOT_PROJECT_NAME, ROBOT_FOLDER_NAME);

        //JUnit

        addProject(driver, wait, JUNIT_PROJECT_NAME);

        addSCMServer(driver, wait, JUNIT_SCM_PROJECT_NAME, JUNIT_SCM_PROJECT_BRANCH);

        addFolder(driver, wait, JUNIT_PROJECT_NAME, JUNIT_FOLDER_NAME);

        for(int i = 1; i <251; i++) {
            addTestCase(driver,
                    wait,
                    JUNIT_BASE_TEST_NAME + "OK" + i,
                    JUNIT_BASE_TEST_REFERENCE_OK + i,
                    JUNIT_TEST_TECHNOLOGY,
                    JUNIT_PROJECT_NAME,
                    JUNIT_FOLDER_NAME,
                    JUNIT_SCM_PROJECT_NAME,
                    JUNIT_SCM_PROJECT_BRANCH);
            addTestCase(driver,
                    wait,
                    JUNIT_BASE_TEST_NAME + "KO" + i,
                    JUNIT_BASE_TEST_REFERENCE_KO + i,
                    JUNIT_TEST_TECHNOLOGY,
                    JUNIT_PROJECT_NAME,
                    JUNIT_FOLDER_NAME,
                    JUNIT_SCM_PROJECT_NAME,
                    JUNIT_SCM_PROJECT_BRANCH);
        }

        createIterationTestPlan(driver, wait, JUNIT_PROJECT_NAME, JUNIT_FOLDER_NAME);
    }

    public void login(WebDriver driver, WebDriverWait wait) {
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@type='submit'][@class='sq-btn']")).click();
    }

    public void addProject(WebDriver driver, WebDriverWait wait, String projectName) {
        driver.findElement(By.xpath("//a[@id='menu-administration-link']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='project-admin']")));
        driver.findElement(By.xpath("//span[@id='project-admin']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='new-project-button']")));
        driver.findElement(By.xpath("//button[@id='new-project-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='add-project-from-template-name']")));
        driver.findElement(By.xpath("//input[@id='add-project-from-template-name']")).sendKeys(projectName);
        driver.findElement(By.xpath("//input[@data-def='evt=confirm'][@type='button']")).click();
    }

    public void addFolder(WebDriver driver, WebDriverWait wait, String projectName, String folderName) {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[@id='test-case-link']"))));
        driver.findElement(By.xpath("//a[@id='test-case-link']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@name='" + projectName + "']")));
        driver.findElement(By.xpath("//li[@name='" + projectName + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/a[text()='" + projectName + "']")));
        driver.findElement(By.xpath("//a[@id='tree-create-button']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[@id='new-folder-tree-button']"))));
        driver.findElement(By.xpath("//li[@id='new-folder-tree-button']")).click();
        driver.findElement(By.xpath("//input[@id='add-folder-name']")).sendKeys(folderName);
        driver.findElement(By.xpath("//div[contains(@style,'block')]//input[@data-def='evt=add-close, state=confirm'][@type='button']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@style,'block')]//input[@data-def='evt=add-close, state=confirm'][@type='button']")));
    }

    public void addSCMServer(WebDriver driver, WebDriverWait wait, String scmProjectName, String scmProjectBranch) {
        driver.findElement(By.xpath("//a[@id='menu-administration-link']")).click();
        driver.findElement(By.xpath("//span[@id='scm-servers-admin']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='workshop']")));
        driver.findElement(By.xpath("//a[text()='workshop']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='add-scm-repository']")));
        driver.findElement(By.xpath("//button[@id='add-scm-repository']")).click();
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys(scmProjectName);
        driver.findElement(By.xpath("//input[@id='branch']")).sendKeys(scmProjectBranch);
        if(driver.findElement(By.xpath("//input[@id='clone']")).isSelected()){
            driver.findElement(By.xpath("//input[@id='clone']")).click();
        }
        driver.findElement(By.xpath("//input[@data-evt='confirm'][@type='button']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@data-evt='confirm'][@type='button']")));
    }

    public void addTestCase(WebDriver driver, WebDriverWait wait, String tcName, String reference, String tech, String projectName, String folderName, String scmProjectName, String scmProjectBranch) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@name='" + projectName + "']/a")));
        driver.findElement(By.xpath("//li[@name='" + projectName + "']/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/a[text()='" + projectName + "']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@name='" + projectName + "']/ins")));
        if(driver.findElement(By.xpath("//li[@name='" + projectName + "']")).getAttribute("class").contains("jstree-closed")) {
            driver.findElement(By.xpath("//li[@name='" + projectName + "']/ins")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@name='" + projectName + "']//li[@name='" + folderName + "']")));
        driver.findElement(By.xpath("//li[@name='" + projectName + "']//li[@name='" + folderName + "']/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/span[@id='folder-name'][text()='" + folderName + "']")));
        driver.findElement(By.xpath("//a[@id='tree-create-button']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[@id='new-test-case-tree-button']"))));
        driver.findElement(By.xpath("//li[@id='new-test-case-tree-button']")).click();
        driver.findElement(By.xpath("//input[@id='add-test-case-name']")).sendKeys(tcName);
        driver.findElement(By.xpath("//div[contains(@style,'block')]//input[@data-def='evt=add-close, state=confirm'][@type='button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='test-case-name'][contains(text(),'" + tcName +"')]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@aria-selected='true']/a[contains(@href, '/steps/panel')]")));
        driver.findElement(By.xpath("//li[@aria-controls='tab-tc-informations']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='test-case-automated-test-reference']")));
        driver.findElement(By.xpath("//div[@id='test-case-automated-test-reference']")).click();
        driver.findElement(By.xpath("//div[@id='test-case-automated-test-reference']//input")).sendKeys(reference);
        driver.findElement(By.xpath("//div[@id='test-case-automated-test-reference']//button[@type='submit']")).click();
        driver.findElement(By.xpath("//span[@id='test-case-automated-test-technology']")).click();
        Select selectTechno = new Select(driver.findElement(By.xpath("//span[@id='test-case-automated-test-technology']//select")));
        selectTechno.selectByVisibleText(tech);
        driver.findElement(By.xpath("//span[@id='test-case-automated-test-technology']//button[@type='submit']")).click();
        driver.findElement(By.xpath("//div[@id='test-case-source-code-repository-url']")).click();
        Select selectSCMRepository = new Select(driver.findElement(By.xpath("//div[@id='test-case-source-code-repository-url']//select")));
        selectSCMRepository.selectByVisibleText("https://github.com/SquashTF-workshop/" + scmProjectName + " (" + scmProjectBranch + ")");
        driver.findElement(By.xpath("//div[@id='test-case-source-code-repository-url']//button[@type='submit']")).click();
    }

    public void createIterationTestPlan(WebDriver driver, WebDriverWait wait, String projectName, String folderName){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='campaign-link']")));
        driver.findElement(By.xpath("//a[@id='campaign-link']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@name='" + projectName + "']")));
        driver.findElement(By.xpath("//li[@name='" + projectName + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/a[text()='" + projectName + "']")));
        driver.findElement(By.xpath("//a[@id='tree-create-button']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[@id='new-campaign-tree-button']"))));
        driver.findElement(By.xpath("//li[@id='new-campaign-tree-button']")).click();
        driver.findElement(By.xpath("//input[@id='add-campaign-name']")).sendKeys("cpg");
        driver.findElement(By.xpath("//div[contains(@style,'block')]//input[@data-def='evt=add-close, state=confirm'][@type='button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='campaign-name'][contains(text(),'cpg')]")));
        driver.findElement(By.xpath("//a[@id='tree-create-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='new-iteration-tree-button']")));
        driver.findElement(By.xpath("//li[@id='new-iteration-tree-button']")).click();
        driver.findElement(By.xpath("//input[@id='add-iteration-name']")).sendKeys("it");
        driver.findElement(By.xpath("//div[contains(@style,'block')]//input[@data-def='evt=add-close, state=confirm'][@type='button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/a[contains(text(),'it')]")));
        driver.findElement(By.xpath("//button[@id='navigate-test-plan-manager']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='linkable-test-cases-tree']//li[@name='" + projectName + "']")));
        if(driver.findElement(By.xpath("//div[@id='linkable-test-cases-tree']//li[@name='" + projectName + "']")).getAttribute("class").contains("jstree-closed")) {
            driver.findElement(By.xpath("//div[@id='linkable-test-cases-tree']//li[@name='" + projectName + "']/ins")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='linkable-test-cases-tree']//li[@name='" + projectName + "']//li[@name='" + folderName + "']")));
        driver.findElement(By.xpath("//div[@id='linkable-test-cases-tree']//li[@name='" + projectName + "']//li[@name='" + folderName + "']/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='linkable-test-cases-tree']//li[@name='" + projectName + "']//li[@name='" + folderName + "']/a[contains(@class, 'jstree-clicked')]")));
        driver.findElement(By.xpath("//div[@id='add-items-button']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//table[@id='iteration-test-plans-table']//td[@class='dataTables_empty']")));
        driver.findElement(By.xpath("//input[@id='back']")).click();
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}

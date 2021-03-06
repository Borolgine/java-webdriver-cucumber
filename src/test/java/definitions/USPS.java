package definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.data.Percentage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getDriver;
import static support.TestContext.*;
////////////////////////////////////////////////////////////////////////
import pages.USPS.*;

import static org.assertj.core.api.Assertions.assertThat;
////////////////////////////////////////////////////////////////////////


public class USPS {
    @Given("I navigate to {string} page")
    public void iNavigateToPage(String page) {
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        switch (page.toLowerCase()) {
            case "quote":
                getDriver().get("https://skryabin.com/market/quote.html");
                break;
            case "google":
                getDriver().get("https://www.google.com");
                break;
            case "usps":
                getDriver().get("https://www.usps.com");
                break;
            case "calculate price":
                getDriver().get("https://postcalc.usps.com/");
                break;
            case "yahoo":
                getDriver().get("https://yahoo.com/");
                break;
            case "converter":
                getDriver().get("https://www.unitconverters.net/");
                break;
            case "calculator":
                getDriver().get("http://www.calculator.net/");
                break;
            case "ups":
                getDriver().get("https://www.ups.com/ca/en/Home.page");
                break;
            default:
                System.out.println("Not recognized page " + page);
                //throw new RuntimeException("Not recognized page " + page);
        }
    }

    @When("I run to Lookup ZIP page by address") //-ok
    public void iRunToLookupZIPPageByAddress() {
        WebElement mailAndShip = getDriver().findElement(By.xpath("//a[@id='mail-ship-width']"));

        new Actions(getDriver())
                .moveToElement(mailAndShip)
                .perform();

        getDriver().findElement(By.xpath("//li[@class='tool-zip']//a")).click();
        getDriver().findElement(By.xpath("//a[contains(@class, 'zip-code-address')]")).click();
    }

    @And("I fill out {string} street, {string} city, {string} state") // -ok
    public void iFillOutStreetCityState(String street, String city, String state) throws InterruptedException {
        getDriver().findElement(By.xpath("//input[@id='tAddress']")).sendKeys(street);
        getDriver().findElement(By.xpath("//input[@id='tCity']")).sendKeys(city);

        Select stateSelect = new Select(getDriver().findElement(By.xpath("//select[@id='tState']")));
        stateSelect.selectByValue(state);

        getDriver().findElement(By.xpath("//a[@id='zip-by-address']")).click();
    }

    @Then("I validate {string} zip code exists in the result") // -ok
    public void iValidateZipCodeExistsInTheResult(String zip) throws InterruptedException {
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String result = getDriver().findElement(By.xpath("//ul[@class='list-group industry-detail']")).getText();
        assertThat(result).contains(zip);

        // --- explicit wait
        //WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        //WebElement result = getDriver().findElement(By.xpath("//div[@id='zipByAddressDiv']"));

        //wait.until(ExpectedConditions.textToBePresentInElement(result, zip));
//        //--- wait.until(driver -> result.getText().length() > 0);
//        //--- assertThat(result.getText()).contains(zip);

    }

    /// USPS DAY 8 ///


    @And("I go to calculate price page")
    public void iGoToCalculatePricePage() throws InterruptedException {
        WebElement mailAndShip = getDriver().findElement(By.xpath("//a[@id='mail-ship-width']"));
        WebElement calculatePrice = getDriver().findElement(By.xpath("//li[@class='tool-calc']//a[contains(text(),'Calculate a Price')]"));

        Actions actions = new Actions(getDriver());     // - actions initializing and they're connected to the browser //
        actions.moveToElement(mailAndShip)              // - "move to" works like "mouse over" // + click on the next required element//
                .click(calculatePrice)
                .perform();

        Thread.sleep(3000);
    }

    @And("I select {string} with {string} shape")
    public void iSelectWithShape(String country, String shape) throws InterruptedException {
        getDriver().findElement(By.xpath("//body//option[72]")).click();
        getDriver().findElement(By.xpath("//input[@id='option_1']")).click();
    }

    @And("I define {string} qty")
    public void iDefineQty(String qty) throws InterruptedException {
        getDriver().findElement(By.xpath("//input[@id='quantity-0']")).sendKeys(qty);
        getDriver().findElement(By.xpath("//input[@class='btn btn-pcalc btn-default']")).click();
        Thread.sleep(1000);
    }

    @Then("I validate the price is {string}")
    public void iValidateThePriceIs(String priceres) {
        String total = getDriver().findElement(By.xpath("//div[@id='total']")).getText();
        assertThat(total).contains(priceres);
    }

    @When("I perform {string} search") // -ok
    public void iPerformSearch(String free_boxes) throws InterruptedException {
        WebElement searchMenu = getDriver().findElement(By.xpath("//li[contains(@class, 'nav-search')]"));
        WebElement searchInput = getDriver().findElement(By.xpath("//input[@id='global-header--search-track-search']"));

        new Actions(getDriver())
                .moveToElement(searchMenu)
                .sendKeys(searchInput, free_boxes)
                .sendKeys(Keys.ENTER)
                .perform();


//        WebElement search = getDriver().findElement(By.xpath("//a[contains(text(),'Search USPS.com')]"));
//        WebElement tracksearch = getDriver().findElement(By.xpath("//input[@id='global-header--search-track-search']"));
//
//        Actions actions = new Actions(getDriver());
//        actions.moveToElement(search)
//                .clickAndHold(search)
//                .perform();;
//
//        getDriver().findElement(By.xpath("//input[@id='global-header--search-track-search']")).sendKeys(free_boxes);
//        getDriver().findElement(By.xpath("//input[@id='global-header--search-track-search']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
    }

    @And("I set {string} in filters") //-ok
    public void iSetInFilters(String filter1) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);

        WebElement spinner = getDriver().findElement(By.xpath("//div[@class='white-spinner-container']"));
        wait.until(ExpectedConditions.invisibilityOf(spinner));

        getDriver().findElement(By.xpath("//a[@class='dn-attr-a'][text()='" + filter1 + "']")).click();

        wait.until(ExpectedConditions.invisibilityOf(spinner));

        /// getWait(5).until(ExpectedConditions.visibilityOf(element));

    }

    @Then("I verify that {string} results found") // -ok
    public void iVerifyThatResultsFound(String searchresultqty) {

        int expectedSize = Integer.parseInt(searchresultqty); //elements parsing

        List<WebElement> results = getDriver().findElements(By.xpath("//ul[@id='records']/li"));
        int actualSize = results.size();

        assertThat(actualSize).isEqualTo(expectedSize);

        //String result = getDriver().findElement(By.xpath("//span[@id='searchResultsHeading']")).getText();
        //assertThat(result).contains(searchresultqty);
    }

    @When("I select {string} in results")  // -ok
    public void iSelectInResults(String result)  throws InterruptedException {
        WebElement spinner = getDriver().findElement(By.xpath("//div[@class='white-spinner-container']"));
        getWait().until(invisibilityOf(spinner));
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);

        getDriver().findElement(By.xpath("//span[contains(text(),'" + result +"')]")).click();
    }

    @And("I click {string} button") // -ok
    public void iClickButton(String button) {
        getDriver().findElement(By.xpath("//a[contains(text(),'" + button + "')]")).click();
    }

    @Then("I validate that Sign In is required") // -ok
    public void iValidateThatSignInIsRequired() throws InterruptedException {
        Thread.sleep(3000);

        String originalWindow = getDriver().getWindowHandle();

        // switch to new window
        for (String handle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(handle);
        }

        new WebDriverWait(getDriver(), 3)
                .until(ExpectedConditions.titleContains("Sign In"));

        WebElement username = getDriver().findElement(By.xpath("//input[@id='username']"));
        assertThat(username.isDisplayed()).isTrue();

        // switch back
        getDriver().switchTo().window(originalWindow);

//        Iterator<String> iterator = getDriver().getWindowHandles().iterator(); //switch to a new tab
//        String newWindow = iterator.next();
//        while (iterator.hasNext()) {
//            newWindow = iterator.next();
//        }
//        getDriver().switchTo().window(newWindow);
//
//        String result = getDriver().findElement(By.xpath("//h1[@id='sign-in-to-your-account-header']")).getText();
//        assertThat(result).contains("Sign In To Your Account");
    }

    @When("I go to Find a Location Page")
    public void iGoToFindALocationPage() throws InterruptedException {
        WebElement quickTools = getDriver().findElement(By.xpath("//a[@id='mail-ship-width']"));
        WebElement findLocation = getDriver().findElement(By.xpath("//a[contains(text(),'Find a USPS Location')]"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(quickTools)
                .click(findLocation)
                .perform();

        Thread.sleep(3000);
    }

    @And("I filter by {string} Location Types, {string} Services,{string} Available Services")
    public void iFilterByLocationTypesServicesAvailableServices(String locationTypes, String services, String availableServices) throws InterruptedException {

        WebElement locationTypesF = getDriver().findElement(By.xpath("//button[@id='post-offices-select']"));
        WebElement locationType = getDriver().findElement(By.xpath("(//ul[@class='dropdown-menu']//a[text()= '" + locationTypes + "'])[2]")); // - forced to hardcode
        WebElement servicesF = getDriver().findElement(By.xpath("//button[@id='service-type-select']"));
        WebElement servicesType = getDriver().findElement(By.xpath("//li[@id='pickupPo']//a[contains(text(),'" + services + "')]"));
        WebElement availableServicesF = getDriver().findElement(By.xpath("//button[@id='available-service-select']"));
        WebElement availableServicesType = getDriver().findElement(By.xpath("//a[contains(text(),'" + availableServices + "')]"));

        new WebDriverWait(getDriver(), 3)
                .until(ExpectedConditions.elementToBeClickable(locationTypesF));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(locationTypesF)
                .click()
                .release()
                .moveToElement(locationType)
                .click()
                .perform();

        new WebDriverWait(getDriver(), 3)
                .until(ExpectedConditions.elementToBeClickable(servicesF));

        actions.moveToElement(servicesF)
                .click()
                .release()
                .moveToElement(servicesType)
                .click()
                .perform();

        new WebDriverWait(getDriver(), 3)
                .until(ExpectedConditions.elementToBeClickable(availableServicesF));

        actions.moveToElement(availableServicesF)
                .click()
                .release()
                .moveToElement(availableServicesType)
                .click()
                .perform();
    }

    @And("I fill in {string} street, {string} city, {string} state")
    public void iFillInStreetCityState(String Street, String City, String State) throws InterruptedException {

        WebElement addressF = getDriver().findElement(By.xpath("//input[@id='search-input']"));
        WebElement addressStreetF = getDriver().findElement(By.xpath("//input[@id='addressLineOne']"));
        WebElement addressZipF = getDriver().findElement(By.xpath("//input[@id='cityOrZipCode']"));
        WebElement addressStateF = getDriver().findElement(By.xpath("//select[@id='servicesStateSelect']"));
        WebElement addressStateType = getDriver().findElement(By.xpath("//select[@id='servicesStateSelect']//option[contains(text(),'CA')]"));
        //WebElement addressStateType = getDriver().findElement(By.xpath("//*[@value='" + State + "']"));
        WebElement resultsBtn = getDriver().findElement(By.xpath("//a[contains(text(),'Go to Results')]"));

        //*[@value= 'CA']


        Actions actions = new Actions(getDriver());
        actions.moveToElement(addressF)
                .click()
                .perform();

        Thread.sleep(1000);
        actions.moveToElement(addressStreetF)
                .click()
                .sendKeys(Street)
                .moveToElement(addressZipF)
                .click()
                .sendKeys(City)
                .click()
                .perform();

        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//option[contains(text(),'" + State + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'Go to Results')]")).click();
        Thread.sleep(1000);
    }

    @Then("I print the phone number and validate it is {string}")
    public void iPrintThePhoneNumberAndValidateItIs(String phone) throws InterruptedException {

        //WebElement firstResult = getDriver().findElement(By.xpath("//div[@id='1440608']//span[@class='listArrow']"));
        //WebElement resultContainer = getDriver().findElement(By.xpath("//p[@class='ask-usps']"));

        Thread.sleep(3000);
        getDriver().findElement(By.xpath("//h2[@class='normal']//strong[contains(text(),'MOUNTAIN VIEW')]")).click();

        Thread.sleep(3000);
        String result = getDriver().findElement(By.xpath("//p[@class='ask-usps']")).getText();
        assertThat(result).contains(phone);
    }

    @When("I go to {string} tab")
    public void iGoToTab(String tab) {
        getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'" + tab + "')]")).click();
    }

    @And("I perform {string} help search")
    public void iPerformHelpSearch(String delivery) throws InterruptedException {
        WebElement search = getDriver().findElement(By.xpath("//input[@id='137:0']"));

        new WebDriverWait(getDriver(), 3)
                .until(ExpectedConditions.elementToBeClickable(search));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(search)
                .click()
                .sendKeys(delivery)
                .sendKeys(Keys.ENTER)
                .perform();
    }

    @Then("I verify that no results of {string} available in help search")
    public void iVerifyThatNoResultsOfAvailableinHelpSearch(String delivery2) {
        WebElement results = getDriver().findElement(By.xpath("//div[@class='slds-has-dividers--bottom uiAbstractList']"));

        new WebDriverWait(getDriver(), 3)
                .until(ExpectedConditions.elementToBeClickable(results));

        String result = getDriver().findElement(By.xpath("//div[@class='slds-has-dividers--bottom uiAbstractList']")).getText();
        assertThat(result).doesNotContain(delivery2);
    }

    @When("I go to {string} under {string}")
    public void iGoToUnder(String option1, String menu1) throws InterruptedException {
        WebElement businessTab = getDriver().findElement(By.xpath("//a[@class='menuitem'][text()='" + menu1 + "']"));
        WebElement subsection = getDriver().findElement(By.xpath("//a[text()='" + option1 + "']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(businessTab)
                .click(subsection)
                .perform();

       // WebDriverWait wait = new WebDriverWait(getDriver(), 10);
       // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='USPS.EDDM.mapPane_layers']//*[local-name()='svg']")));
    }

    @And("I search for {string}")
    public void iSearchFor(String address) {
        getDriver().findElement(By.xpath("//input[@id='address']")).sendKeys(address);
        getDriver().findElement(By.xpath("//input[@id='address']")).sendKeys(Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Show Table')]")));

    }

    @And("I click {string} on the map")
    public void iClickOnTheMap(String showTable) {

        WebElement table = getDriver().findElement(By.xpath("//a[@class='route-table-toggle']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(table)
                .click(table)
                .perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Select All')]")));
    }

    @When("I click {string} on the table")
    public void iClickOnTheTable(String select) {
        getDriver().findElement(By.xpath("//a[contains(text(),'" + select + "')]")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='modal-box']")));
    }

    @And("I close modal window")
    public void iCloseModalWindow() {
        getDriver().findElement(By.xpath("//div[@id='modal-box-closeModal']")).click();
    }

    @Then("I verify that summary of all rows of Cost column is equal Approximate Cost in Order Summary")
    public void iVerifyThatSummaryOfAllRowsOfCostColumnIsEqualApproximateCostInOrderSummary() {
        String result = getDriver().findElement(By.xpath("//div[@class='dojoxGridContent']")).getText();

        String totalCountString = getDriver().findElement(By.xpath("//a[contains(@class, 'totalsArea')]")).getText();
        int totalCount = Integer.parseInt(totalCountString.replaceAll("\\D*", "")); // total count of elements from the top of the frame
        By costListSelector = By.xpath("//td[@idx='7']");
        List<WebElement> costList = getDriver().findElements(By.xpath("//td[@idx='7']"));  //plural

        //var 1 getting to the last element in the list:
        //getActions().moveToElement(costList.get(costList.size() -1));

        //scrolling to the last element and var 2 getting to the last element in the list
        while (costList.size() < totalCount) {
            int lastIndex = costList.size() - 1;
            getActions().moveToElement(costList.get(lastIndex)).perform();
            // and reload the elements again and call the same search
            costList = getDriver().findElements(costListSelector);
        }

        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@idx='7']")));

        //getWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//td[@idx='7']"), totalCount));

        double actualTotal = 0;
        for (WebElement cost : costList) {       //take webelement cost out of costlist
            String costString = cost.getText().replace("$", "");
            double costTotal = Double.parseDouble(costString); // a currency formatter could be used here instead of
            actualTotal += costTotal;   // actualTotal = actualTotal + costTotal
        }

        String expectedTotalString = getDriver().findElement(By.xpath("//span[@class='approx-cost']")).getText();
        double expectedTotal = Double.parseDouble(expectedTotalString);

        assertThat(actualTotal).isCloseTo(expectedTotal, Percentage.withPercentage(1));

        System.out.println("Expected elements total: " + totalCount);
        System.out.println("Actual elements total: " + costList.size());
        System.out.println("The actual total: " + actualTotal);
        System.out.println("The expected total: " + expectedTotal);
    }

    @And("choose mail service Priority Mail")
    public void chooseMailServicePriorityMail() {
        //getExecutor().executeScript("arguments[0].click();", getDriver().findElement(By.xpath("(//label[contains(@for,'Service-Priority')])[1]")));
        WebElement check = getDriver().findElement(By.xpath("//h4[contains(text(),'Mail Service')]/..//label[contains(text(), 'Priority Mail (')]"));
        getWait(5).until(ExpectedConditions.visibilityOf(check));

        getActions()
        .moveToElement(check)
        .perform();

        check.click();

    }

    @Then("I verify {int} items found")
    public void iVerifyItemsFound(int itemQty) {
        String total = getDriver().findElement(By.xpath("//h2[@class='col-md-3 application-header align-self-center results-per-page']")).getText();
        assertThat(total.contains("of " + itemQty + " Results"));
    }

    @When("I unselect Stamps checkbox")
    public void iUnselectStampsCheckbox() {
        getDriver().findElement(By.xpath("//label[contains(@for,'Category-Stamps')]")).click();
        //div[@class='cartridge-viewport']//span[contains(text(),'Stamps')]
    }

    @And("select Vertical stamp Shape")
    public void selectVerticalStampShape() {
        getActions()
        .moveToElement(getDriver().findElement(By.xpath("//h4[contains(text(),'Stamp Shape')]/..//label[contains(text(), 'Vertical (')]")))
        .perform();

        getDriver().findElement(By.xpath("//h4[contains(text(),'Stamp Shape')]/..//label[contains(text(), 'Vertical (')]")).click();
    }

    @And("I click Blue color")
    public void iClickBlueColor() {
        getActions()
        .moveToElement(getDriver().findElement(By.xpath("//div[contains(@class, 'color')]//div[contains(@onclick, 'blue')]")))
        .perform();

        getDriver().findElement(By.xpath("//div[contains(@class, 'color')]//div[contains(@onclick, 'blue')]")).click();
    }

    @Then("I verify {string} and {string} filters")
    public void iVerifyAndFilters(String filter1, String filter2) {
        String option1 = getDriver().findElement(By.xpath("//div[@class='cartridge-viewport']//span[contains(text(),'Blue')]")).getText();
        String option2 = getDriver().findElement(By.xpath("//span[contains(text(),'Vertical')]")).getText();

        assertThat(option1).contains(filter1);
        assertThat(option2).contains(filter2);
    }

    @And("I verify that items below {int} dollars exists")
    public void iVerifyThatItemsBelowDollarsExists(double amount) {

        String result = getDriver().findElement(By.xpath("//div[@class='result-page-wrapper']")).getText();

        assertThat(result).contains("$" + (amount - 1));
    }

    @And("verify {string} service exists")
    public void verifyServiceExists(String service) {
        WebElement spinner = getDriver().findElement(By.xpath("//div[@class='white-spinner-container']"));
        getWait().until(invisibilityOf(spinner));
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);

        String servicesOption = getDriver().findElement(By.xpath("//select[@id='passportappointmentType']/option[text()='" + service + "']")).getText();
        assertThat(servicesOption).isEqualTo(service);
    }

    @And("I reserve new PO box for {string}")
    public void iReserveNewPOBoxFor(String postalCode) {

        WebElement searchInput = getDriver().findElement(By.xpath("//input[@id='searchInput']"));
        getWait().until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click();
        searchInput.sendKeys(postalCode);

        WebElement search = getDriver().findElement(By.xpath("//span[@class='icon-search']"));
        new Actions(getDriver()).moveToElement(search)
                .click()
                .perform();
    }

    @Then("I verify that {string} present")
    public void iVerifyThatPresent(String address) throws InterruptedException {
//        WebElement spinner = getDriver().findElement(By.xpath("//div[@id='modal-box_overlay-progress_2']"));
//        getWait().until(invisibilityOf(spinner));
//        WebDriverWait wait = new WebDriverWait(getDriver(), 10);

        String location = getDriver().findElement(By.xpath("//div[@class='esri-view-surface']//div[2]//*[local-name()='svg']")).getText();
        assertThat(location).contains(address);
    }

    @And("I verify that {string} PO Box is available in {string}")
    public void iVerifyThatPOBoxIsAvailableIn(String size, String location) {

        String item = getDriver().findElement(By.xpath("//label[@for='boxXL']")).getText();
        String position = getDriver().findElement(By.xpath("//div[@id='boxLocation']/h2")).getText();
        assertThat(location).isEqualTo(position);
        assertThat(item).contains(size);
    }

    @And("I enter {string} into store search")
    public void iEnterIntoStoreSearch(String search) {
        getDriver().findElement(By.xpath("//input[@id='store-search']")).click();
        getDriver().findElement(By.xpath("//input[@id='store-search']")).sendKeys(search);
    }

    @Then("I search and validate no products found")
    public void iSearchAndValidateNoProductsFound() {
        getDriver().findElement(By.xpath("//input[@id='store-search-btn']")).click();
        String result = getDriver().findElement(By.xpath("//div[@class='no-results-found']//p")).getText();
        assertThat(result).contains("search did not match any");
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// OOP section

    UspsHome                uspsHome               =   new UspsHome             ();
    UspsLookupByZip         uspsLookupByZip        =   new UspsLookupByZip      ();
    UspsHeader              uspsHeader             =   new UspsHeader           ();
    UspsByAddressForm       uspsByAddressForm      =   new UspsByAddressForm    ();
    UspsByAddressResult     uspsByAddressResult    =   new UspsByAddressResult  ();
    UspsCalculatePrice      uspsCalculatePrice     =   new UspsCalculatePrice   ();
    UspsCalculator          uspsCalculator         =   new UspsCalculator       ();
//  ClassName               method                 =   new Constructor          ();

    @When("I go to lookup ZIP page by address oop")
    public void iGoToLookupZIPPageByAddressOop() {
        uspsHeader.goToLookupByZip();
        uspsLookupByZip.clickFindByAddress();
    }

    @And("I fill out {string} street, {string} city, {string} state oop")
    public void iFillOutStreetCityStateOop(String street, String city, String state) {
        //String resultString =
                uspsByAddressForm
                .fillStreet (street)
                .fillCity   (city)
                .selectState(state)
                .clickFind  ()
                .getSearchResult();
    }

    @Then("I validate {string} zip code in the result oop")
    public void iValidateZipCodeInTheResultOop(String zip) {

        String result = uspsByAddressResult.getSearchResult();
        assertThat(result).contains(zip);

        boolean areAllItemsContainZip = uspsByAddressResult.areAllResultsContain(zip);
        assertThat(uspsByAddressResult.areAllResultsContain(zip)).isTrue();
    }

    @When("I go to Calculate Price Page oop")
    public void iGoToCalculatePricePageOop() {
        uspsHeader.goToCalculatePrice();
    }

    @And("I select {string} with {string} shape oop")
    public void iSelectWithShapeOop(String country, String shape) {
        uspsCalculatePrice.selectCountry(country);
        uspsCalculatePrice.selectShape(shape);
    }

    @And("I define {string} quantity oop")
    public void iDefineQuantityOop(String count) {
        uspsCalculator.fillQuantity(count);
    }

    @Then("I calculate the price and validate cost is {string} oop")
    public void iCalculateThePriceAndValidateCostIsOop(String cost) {
        uspsCalculator.submit();
        //uspsCalculator.getTotal();
        assertThat(uspsCalculator.getTotal()).isEqualTo(cost);

    }
}



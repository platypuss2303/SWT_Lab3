package TestBasic;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestCase {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();

        // Thiết lập User-Agent mới để né captcha
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

        // Độ phân giải tiêu chuẩn 1920x1080
        Dimension dimension = new Dimension(1920, 1080);
        driver.manage().window().setSize(dimension);

        driver.manage().window().maximize();
        driver.navigate().to("https://nhanam.vn/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Đăng nhập 
        WebElement icon_Login = driver.findElement(By.xpath("//body[1]/header[1]/div[1]/div[2]/div[3]/div[2]/*[name()='svg']/*[name()='use']"));
        if (icon_Login.isEnabled()) {
            icon_Login.click();
        } else {
            throw new IllegalStateException("Login icon is not enabled.");
        }
        WebElement login = driver.findElement(By.linkText("Đăng nhập"));
        if (login.isDisplayed()) {
            login.click();
        } else {
            throw new IllegalStateException("Login category is not enabled.");
        }

        // Nhập email và password
        WebElement email = driver.findElement(By.xpath("//input[@id='customer_email']"));
        email.sendKeys("luatngqe180131@fpt.edu.vn");
        WebElement password = driver.findElement(By.xpath("//input[@id='customer_password']"));
        password.sendKeys("123456");

        // Login
        WebElement button_Login = driver.findElement(By.xpath("//button[contains(text(),'Đăng nhập')]"));
        if (button_Login.isEnabled()) {
            button_Login.click();
        } else {
            throw new IllegalStateException("Login button is not enabled.");
        }
        // Tìm kiếm 
        WebElement searchCategoty = driver.findElement(By.xpath("//input[@placeholder='Tìm kiếm...']"));
        searchCategoty.sendKeys("Nấu ăn");
        WebElement icon_Search = driver.findElement(By.xpath("//button[@aria-label='Justify']"));
        icon_Search.click();

        // Tìm phần tử đầu tiên theo kết quả trả về
        WebElement firstItem = driver.findElement(By.xpath("//body/main[@class='wrapperMain_content']/section[@class='signup search-main wrap_background background_white clearfix']/div[@class='container']/div[@class='row bg_page clearfix margin-bottom-50']/div[@class='search-page col-12']/div[@class='products-view products-view-grid ']/div[@class='row']/div[1]"));
        firstItem.click();

        WebElement button_addToCart1 = driver.findElement(By.xpath("//button[contains(text(),'Thêm vào giỏ hàng')]"));
        button_addToCart1.click();

        driver.navigate().back();
        // Tìm phần tử thứ hai theo kết quả trả về
        WebElement secondItem = driver.findElement(By.xpath("//body/main[@class='wrapperMain_content']/section[@class='signup search-main wrap_background background_white clearfix']/div[@class='container']/div[@class='row bg_page clearfix margin-bottom-50']/div[@class='search-page col-12']/div[@class='products-view products-view-grid ']/div[@class='row']/div[2]"));
        secondItem.click();

        WebElement button_addToCart2 = driver.findElement(By.xpath("//button[contains(text(),'Thêm vào giỏ hàng')]"));
        button_addToCart2.click();

        driver.navigate().back();
        // Tìm phần tử thứ ba theo kết quả trả về
        WebElement fifthItem = driver.findElement(By.xpath("//body/main[@class='wrapperMain_content']/section[@class='signup search-main wrap_background background_white clearfix']/div[@class='container']/div[@class='row bg_page clearfix margin-bottom-50']/div[@class='search-page col-12']/div[@class='products-view products-view-grid ']/div[@class='row']/div[5]"));
        fifthItem.click();

        WebElement button_addToCart3 = driver.findElement(By.xpath("//button[contains(text(),'Thêm vào giỏ hàng')]"));
        button_addToCart3.click();

        WebElement button_Cart = driver.findElement(By.xpath("//span[@class='count_item count_item_pr']"));
        button_Cart.click();

        // Kiểm tra tổng tiền     
        List<WebElement> priceElements = driver.findElements(By.xpath("//span[@class='cart-price']"));

        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText();

            priceText = priceText.replaceAll("[^\\d]", "");

            double price = Double.parseDouble(priceText);
            prices.add(price);
        }

        // Tính tổng giá trị của các sản phẩm
        double total = 0.0;
        for (double price : prices) {
            total += price;
        }
        total = total / 2;

        // Lấy total price
        WebElement totalElement = driver.findElement(By.xpath("//div[@class='text-right cart__totle']"));
        String totalText = totalElement.getText().replace("₫", "").replace(".", "").trim();
        double displayedTotal = Double.parseDouble(totalText);

        // So sánh 
        if (total != displayedTotal) {
            throw new AssertionError("Tổng giá trị sản phẩm không khớp với giá trị hiển thị trên trang!");
        }

        // Thanh toán
        WebElement button_Purchase = driver.findElement(By.id("btn-proceed-checkout"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button_Purchase);
        button_Purchase.click();

        // Nhập thông tin để tiến hành thanh toán
        WebElement address = driver.findElement(By.id("billingAddress"));
        address.clear();
        address.sendKeys("Khu đô thị mới An Phú Thịnh, P. Nhơn Bình, TP. Quy Nhơn, Bình Định");

        WebElement icon_Province = driver.findElement(By.xpath("//span[@id='select2-billingProvince-container']"));
        icon_Province.click();
        WebElement province = driver.findElement(By.xpath("//input[@role='searchbox']"));
        province.sendKeys("Bình Định");
        province.sendKeys(Keys.ENTER);

        WebElement icon_District = driver.findElement(By.xpath("//span[@id='select2-billingDistrict-container']"));
        icon_District.click();
        WebElement district = driver.findElement(By.xpath("//input[@role='searchbox']"));
        district.sendKeys("Thành phố Quy Nhơn");
        district.sendKeys(Keys.ENTER);

        WebElement icon_Ward = driver.findElement(By.xpath("//span[@id='select2-billingWard-container']"));
        icon_Ward.click();
        WebElement ward = driver.findElement(By.xpath("//input[@role='searchbox']"));
        ward.sendKeys("Phường Nhơn Bình");
        ward.sendKeys(Keys.ENTER);

        WebElement note = driver.findElement(By.id("note"));
        note.click();
        note.sendKeys("Sách em dùng tặng người thân, shop gói kĩ giúp em nhé !! Em cảm ơn ạ !!");

        WebElement button_PaymentMethod = driver.findElement(By.xpath("//input[@id='paymentMethod-432316']"));
        button_PaymentMethod.click();
                
//        WebElement button_Buy = driver.findElement(By.xpath("//div[@class='order-summary__nav field__input-btn-wrapper hide-on-mobile layout-flex--row-reverse']//span[@class='spinner-label'][contains(text(),'ĐẶT HÀNG')]"));
//        button_Buy.click();
        
        Thread.sleep(3000);
        driver.quit();
    }
}

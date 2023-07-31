import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        //Подключаю Google Chrome:
        WebDriver driver = new ChromeDriver();
        //Неявное ожидание:
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //Явное ожидание
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //Подключаю собственный метод сравнения:
        EqualsPage equals = new EqualsPage();
        //Подключаю собственный метод поиска окна:
        WindowsPage windowBrowser = new WindowsPage();
        //Подключаю собственный метод тестирования карты:
        TestCardPage checkCard = new TestCardPage();
        //Подключаю js:
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            //Открываю сайт:
            driver.get("https://d1mk0-0.github.io/qa-engineer/");
            //Чуточку подожду:
            Thread.sleep(5000);

            //Начну проверку с навбара:
            //Ссылку на элемент буду тестировать методом поиска значения атрибута и просто сверю его:
            WebElement caseLink = driver.findElement(By.xpath("//a[text()='Кейсы']"));
            System.out.println("Ссылка на блок 'Кейсы':");
            equals.equalsTest(caseLink.getAttribute("href"), "https://d1mk0-0.github.io/qa-engineer/#cases");

            WebElement aboutMeLink = driver.findElement(By.xpath("//a[text()='Обо мне']"));
            System.out.println("Ссылка на блок 'Обо мне':");
            equals.equalsTest(aboutMeLink.getAttribute("href"), "https://d1mk0-0.github.io/qa-engineer/#about");

            //Получаю дискриптор окна для проверки ссылок:
            String windowOriginal = driver.getWindowHandle();
            //Нахожу ссылку GitHub и нажимаю на нее:
            driver.findElement(By.xpath("//a[text()='GitHub']")).click();
            //Теперь снова запишу дискрипторы:
            Set<String> currentWindows = driver.getWindowHandles();
            //Инициализирую переменную:
            String windowNew = null;
            //С помощью цикла нахожу дикриптор нового окна:
            for (String window : currentWindows) {
                if (!window.equals(windowOriginal)) {
                    windowNew = window;
                }
            }
            //Переключаюсь на него:
            driver.switchTo().window(windowNew);
            //Снова получаю настоящий URL:
            String newUrl = driver.getCurrentUrl();
            //Использую свой метод для сравнения, он же сообщит хорошо ли все:
            equals.equalsTest(newUrl, "https://github.com/D1mk0-0");
            //После проверки закрываю вкладку браузера:
            driver.close();
            //Переключаюсь на окно сайта:
            driver.switchTo().window(windowOriginal);

            //Нахожу следующую ссылку и кликаю по ней:
            driver.findElement(By.xpath("//a[text()='HeadHunter']")).click();
            //Сново получаю дискрипторы:
            currentWindows = driver.getWindowHandles();
            //Использую метод цикла:
            windowBrowser.windowCheckUrl(windowOriginal, currentWindows);
            //Переключаюсь на новое окно:
            driver.switchTo().window(windowBrowser.windowNew);
            //Получаю ссылку нового окна:
            newUrl = driver.getCurrentUrl();
            equals.equalsTest(newUrl, "https://volgograd.hh.ru/applicant/resumes/view?resume=a8b9c8e8ff0c38c99a0039ed1f4c476b4f4879");
            driver.close();
            driver.switchTo().window(windowOriginal);

            //Делаю то же самое с остальными ссылками:
            driver.findElement(By.xpath("(//img[@alt='Телеграмм'])[1]")).click();
            currentWindows = driver.getWindowHandles();
            windowBrowser.windowCheckUrl(windowOriginal, currentWindows);
            driver.switchTo().window(windowBrowser.windowNew);
            newUrl = driver.getCurrentUrl();
            equals.equalsTest(newUrl, "https://t.me/DmitryVasyutin");
            driver.close();
            driver.switchTo().window(windowOriginal);

            driver.findElement(By.xpath("(//img[@alt='Вотсап'])[1]")).click();
            currentWindows = driver.getWindowHandles();
            windowBrowser.windowCheckUrl(windowOriginal, currentWindows);
            driver.switchTo().window(windowBrowser.windowNew);
            newUrl = driver.getCurrentUrl();
            equals.equalsTest(newUrl, "https://api.whatsapp.com/send/?phone=79616568977&text&type=phone_number&app_absent=0");
            driver.close();
            driver.switchTo().window(windowOriginal);

            driver.findElement(By.xpath("(//img[@alt='Вайбер'])[1]")).click();
            currentWindows = driver.getWindowHandles();
            windowBrowser.windowCheckUrl(windowOriginal, currentWindows);
            driver.switchTo().window(windowBrowser.windowNew);
            newUrl = driver.getCurrentUrl();
            equals.equalsTest(newUrl, "https://viber.click/79616568977");
            driver.close();
            driver.switchTo().window(windowOriginal);

            //Ссылку на почту буду тестировать методом поиска значения атрибута и просто сверю его:
            WebElement postLink = driver.findElement(By.xpath("//a[text()='Почта']"));
            System.out.println("Ссылка на почту:");
            equals.equalsTest(postLink.getAttribute("href"), "mailto:D1mk0Leb@yandex.ru");

            //Использую js что бы проскролить окно до ссылки, иначе selenium ее не открывает и ожидания не помогают:
            WebElement footerLinkTelegram = driver.findElement(By.xpath("(//img[@alt='Телеграмм'])[2]"));
            js.executeScript("arguments[0].scrollIntoView(true);", footerLinkTelegram);

            Thread.sleep(1000);
            driver.findElement(By.xpath("(//img[@alt='Телеграмм'])[2]")).click();
            currentWindows = driver.getWindowHandles();
            windowBrowser.windowCheckUrl(windowOriginal, currentWindows);
            driver.switchTo().window(windowBrowser.windowNew);
            newUrl = driver.getCurrentUrl();
            //Добавлю пояснение, так как в консольке ссылка выглядит одинаково:
            System.out.println("Ссылка внизу сайта:");
            equals.equalsTest(newUrl, "https://t.me/DmitryVasyutin");
            driver.close();
            driver.switchTo().window(windowOriginal);

            driver.findElement(By.xpath("(//img[@alt='Вотсап'])[2]")).click();
            currentWindows = driver.getWindowHandles();
            windowBrowser.windowCheckUrl(windowOriginal, currentWindows);
            driver.switchTo().window(windowBrowser.windowNew);
            newUrl = driver.getCurrentUrl();
            System.out.println("Ссылка внизу сайта:");
            equals.equalsTest(newUrl, "https://api.whatsapp.com/send/?phone=79616568977&text&type=phone_number&app_absent=0");
            driver.close();
            driver.switchTo().window(windowOriginal);

            driver.findElement(By.xpath("(//img[@alt='Вайбер'])[2]")).click();
            currentWindows = driver.getWindowHandles();
            windowBrowser.windowCheckUrl(windowOriginal, currentWindows);
            driver.switchTo().window(windowBrowser.windowNew);
            newUrl = driver.getCurrentUrl();
            System.out.println("Ссылка внизу сайта:");
            equals.equalsTest(newUrl, "https://viber.click/79616568977");
            driver.close();
            driver.switchTo().window(windowOriginal);

            //Для тестирования карт использую отдельный метод:
            checkCard.testSqlCard(driver);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //Еще чуть чуть подожду и закрою браузер:
            Thread.sleep(10000);
            driver.quit();
        }
    }
}

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestCardPage {
    //Перекидываю параметр driver:
    public WebDriver driver;

    //Использую отдельные методы для тестирования каждой карты:
    //Метод тестирования карты SQL-демонстрации:
    public void testSqlCard(WebDriver driver) throws InterruptedException {
        //Объявляю driver:
        this.driver = driver;
        //Сново подключаю модуль JS:
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Подключаю метод ожидания:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //Сообщаю о начале тестирования карты:
        System.out.println("Тестирование карты SQL-демонстрации:");

        //Инициализирую переменные:
        String createTextOriginal = "CREATE TABLE employee (\n" +
                "id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                "first_name VARCHAR(50) NOT NULL,\n" +
                "last_name VARCHAR(50) NOT NULL,\n" +
                "gender VARCHAR(6) NOT NULL,\n" +
                "email VARCHAR(150),\n" +
                "date_of_birth DATE NOT NULL,\n" +
                "country_of_birth VARCHAR (50) NOT NULL\n" +
                ");";
        String insertTextOriginal = "INSERT INTO employee (\n" +
                "first_name,\n" +
                "last_name,\n" +
                "gender,\n" +
                "email,\n" +
                "date_of_birth,\n" +
                "country_of_birth\n" +
                ")\n" +
                "VALUES ('John', 'Doe', 'MALE', 'jd@mail.com', '2000-01-01', 'United Kingdom');";
        String selectTextOriginal = "SELECT country_of_birth, COUNT(*) > 10\n" +
                "FROM employee GROUP BY country_of_birth\n" +
                "HAVING COUNT(*) > 10\n" +
                "ORDER BY country_of_birth DESC;";

        //Скролю до кнопки. Иначе она не кликается:
        WebElement buttonCard = driver.findElement(By.xpath("//button[@data-bs-target='#sqlModal']"));
        js.executeScript("arguments[0].scrollIntoView(true);", buttonCard);
        Thread.sleep(1000);

        //Нахожу кнопку карты :
        WebElement cardButton = driver.findElement(By.xpath("//button[@data-bs-target='#sqlModal']"));
        //Вместо click использую sendKeys:
        cardButton.click();

        //Установлю ожидание, пока модальое окно полностью не откроется и будет отображена кнопка:
        WebElement firstButtonCard = driver.findElement(By.xpath("//button[@id='test-create']"));
        wait.until(ExpectedConditions.visibilityOf(firstButtonCard));
        firstButtonCard.click();
        //Нахожу блок с отображаемым текстом и сохраняю в элемент:
        WebElement sqlDisplay = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div/p/span[2]"));
        //Ожидаю завершения демонстрации, о чем будет свидетельствовать последний символ в команде:
        wait.until(ExpectedConditions.textToBePresentInElement(sqlDisplay, ";"));
        //Теперь сохраняю полученное значение в переменную:
        String textVisib = sqlDisplay.getText();
        //И проверяю сходится ли команда отображенная на экране с предустановленной:
        if (textVisib.equals(createTextOriginal)) {
            System.out.println("С CREATE все как надо!");
        } else {
            System.out.println("С CREATE что-то не так!");
        }

        //Ровно то же самое делаю с остальными кнопками:
        driver.findElement(By.xpath("//button[@id='test-insert']")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(sqlDisplay, ";"));
        textVisib = sqlDisplay.getText();
        if (textVisib.equals(insertTextOriginal)) {
            System.out.println("С INSERT все как надо!");
        } else {
            System.out.println("С INSERT что-то не так!");
        }

        driver.findElement(By.xpath("//button[@id='test-select']")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(sqlDisplay, ";"));
        textVisib = sqlDisplay.getText();
        if (textVisib.equals(selectTextOriginal)) {
            System.out.println("С SELECT все как надо!");
        } else {
            System.out.println("С SELECT что-то не так!");
        }

        //Закрываю модальное окно после теста:
        driver.findElement(By.xpath("//div[@id='sqlModal']//button[text()='Закрыть']")).click();
    }
}
